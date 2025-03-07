package ms.notification.service.impl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import app.core.jwt.JwtUtils;
import app.core.jwt.JwtVerifyUtils;
import app.core.jwt.JwtVertxUtils;
import app.core.util.CoreUtils;
import io.jsonwebtoken.Claims;
import io.vertx.core.json.JsonObject;
import ms.notification.model.JwtUserDetails;

@Service("jwtUserDetailsService")
public class JwtUserDetailsService implements UserDetailsService {

	private static final Logger LOGGER = LogManager.getLogger(JwtUserDetailsService.class);
	

	@Autowired
	JwtVerifyUtils jwtVerifyUtils;

	@Autowired
	JwtUtils jwtUtils;

	@Override
	public UserDetails loadUserByUsername(String jwt) throws UsernameNotFoundException {

		LOGGER.info("loadUserByUsername jwt : {}", jwt);

		try {

			Claims claims = jwtVerifyUtils.getClaims(jwt);
			JwtUserDetails user = new JwtUserDetails();

			LOGGER.info("loadUserByUsername pincipal : {}", claims.toString());

			user.setJwt(jwt);
			user.setUsername(claims.getSubject());

			if (claims.get("roles") != null && claims.get("roles") instanceof List) {
				user.setRoles((List<String>)claims.get("roles"));
			}
			
			if (claims.get("groups") != null && claims.get("groups") instanceof List) {
				user.setGroups((List<String>)claims.get("groups"));
			}
  
			return user;
		} catch (Exception e) {
			LOGGER.warn(e.getMessage(),e);
		}

		throw new UsernameNotFoundException(jwt);
	}

}

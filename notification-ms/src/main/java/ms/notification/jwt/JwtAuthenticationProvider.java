package ms.notification.jwt;

 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import app.core.jwt.JwtUtils;
import app.core.jwt.JwtVerifyUtils;
import app.core.jwt.JwtVertxUtils;

import ms.notification.service.impl.JwtUserDetailsService;

public class JwtAuthenticationProvider implements AuthenticationProvider {
	
	private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationProvider.class);

 

	@Autowired
	JwtVerifyUtils jwtVerifyUtils;
	
	
	@Autowired
	JwtUtils jwtUtils;

	@Autowired
	JwtUserDetailsService userDetailsService;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {

		final String jwt = authentication.getCredentials().toString();

		try {
			if (jwtVerifyUtils.isVaildToken(jwt)) {

				UserDetails userDetails = userDetailsService.loadUserByUsername(jwt);

				UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
						null, userDetails.getAuthorities());
				
				return authToken;

			}
		} catch (UsernameNotFoundException e) {
			log.info(e.getMessage(),e);
		}  

		throw new AuthenticationServiceException("Login Failed.");

	}

	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

}

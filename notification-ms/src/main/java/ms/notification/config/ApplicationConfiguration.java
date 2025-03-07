package ms.notification.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;

import app.core.jwt.JwtUtils;
import app.core.jwt.JwtVerifyUtils;
import app.core.jwt.JwtVertxUtils;
import ms.notification.jwt.JwtAuthenticationFilter;
import ms.notification.jwt.JwtAuthenticationProvider;

@Configuration
public class ApplicationConfiguration {

	private static final Logger LOGGER = LogManager.getLogger(ApplicationConfiguration.class);

	@Value("${ms.core.key.store:key.p12}")
	private String keystore;

	@Value("${ms.core.key.pass}")
	private String keypass;

	@Value("${ms.core.key.alias:default}")
	private String keyAlias;

	
	
	@Bean
	public JwtVerifyUtils jwtVerifyUtils() {

		LOGGER.info("jwtUtils: {} {} {}", keystore, keypass, keyAlias);
		JwtVerifyUtils jb = new JwtVerifyUtils();
//		jb.init(keystore, keyAlias, keypass);
		return jb;
	}
	
	
	@Bean
	public JwtUtils jwtUtils() {

		LOGGER.info("jwtUtils: {} {} {}", keystore, keypass, keyAlias);
		JwtUtils jb = new JwtUtils();
//		jb.init(keystore, keyAlias, keypass);
		return jb;
	}

	@Bean
	AuthenticationProvider authenticationProvider() {

		JwtAuthenticationProvider authProvider = new JwtAuthenticationProvider();

		return authProvider;
	}

}

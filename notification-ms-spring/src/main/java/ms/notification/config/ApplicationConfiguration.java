package ms.notification.config;

 
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;

import app.core.jwt.JwtUtils;
import app.core.jwt.JwtVerifyUtils;
import ms.notification.jwt.JwtAuthenticationProvider;
 
@Configuration
@ComponentScan({"ms.notification"})
public class ApplicationConfiguration {

	private static final Logger log = LoggerFactory.getLogger(ApplicationConfiguration.class);


	@Value("${ms.core.key.store:key.p12}")
	private String keystore;

	@Value("${ms.core.key.pass}")
	private String keypass;

	@Value("${ms.core.key.alias:default}")
	private String keyAlias;

	
	
	@Bean
	public JwtVerifyUtils jwtVerifyUtils() {
 
		JwtVerifyUtils jb = new JwtVerifyUtils();
 
		return jb;
	}
	
	
	@Bean
	public JwtUtils jwtUtils() {

 
		JwtUtils jb = new JwtUtils();
 
		return jb;
	}

	@Bean
	AuthenticationProvider authenticationProvider() {

		JwtAuthenticationProvider authProvider = new JwtAuthenticationProvider();

		return authProvider;
	}
	

 
}

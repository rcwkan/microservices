package ms.user.security;

import java.security.Principal;

import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;
import io.quarkus.amazon.lambda.http.LambdaIdentityProvider;
import io.quarkus.amazon.lambda.http.model.AwsProxyRequest;
import io.quarkus.security.identity.SecurityIdentity;
import io.quarkus.security.runtime.QuarkusPrincipal;
import io.quarkus.security.runtime.QuarkusSecurityIdentity;

@ApplicationScoped
public class AwsSecurityProvider implements LambdaIdentityProvider {

	private static final Logger log = Logger.getLogger(AwsSecurityProvider.class);

	
	@Override
	public SecurityIdentity authenticate(AwsProxyRequest event) {
		
		log.info("AwsSecurityProvider: " + event);
		 
		if (event.getMultiValueHeaders() == null || !event.getMultiValueHeaders().containsKey("x-user"))
			return null;
		Principal principal = new QuarkusPrincipal(event.getMultiValueHeaders().getFirst("x-user"));
		QuarkusSecurityIdentity.Builder builder = QuarkusSecurityIdentity.builder();
		builder.setPrincipal(principal);
		return builder.build();
	}
}
package ms.user.lambda;

import org.jboss.logging.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import ms.user.dynamo.model.User;
import ms.user.lambda.model.AuthInput;
import ms.user.lambda.model.AuthOutput;
import ms.user.service.AuthService;
import ms.user.service.UserService;
import ms.user.service.impl.AuthServiceImpl;

@Named("auth")
public class AuthLambda implements RequestHandler<AuthInput, AuthOutput> {

	private static final Logger log = Logger.getLogger(AuthLambda.class);

	@Inject
	@Named("authService")
	AuthService authService;

	@Inject
	@Named("userService")
	UserService userService;

	@Override
	public AuthOutput handleRequest(AuthInput input, Context context) {

		
		try {

			if (AuthInput.ACTION_AUTH.equals(input.getAction())) {
				String jwt = authService.authenticate(input.getUsername(), input.getPassword());

				log.info("handleRequest jwt:" + jwt);

				return new AuthOutput(context.getAwsRequestId(), jwt);
				
			} else if (AuthInput.ACTION_REGISTER.equals(input.getAction())) {

				User user = userService.register(input.getUsername(), input.getEmail(), input.getDisplayName(), input.getPassword());
				
				return new AuthOutput(context.getAwsRequestId(), user.getUserId());
			}
		} catch (Exception e) {

			e.printStackTrace();

			return new AuthOutput(context.getAwsRequestId(), null, e.getMessage());
		}

		return new AuthOutput(context.getAwsRequestId(), null, "Action not found.");
	}

}

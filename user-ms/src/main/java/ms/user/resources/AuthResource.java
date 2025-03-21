package ms.user.resources;

    
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import app.core.util.CoreUtils;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ms.user.dynamo.model.User;
import ms.user.models.UserAccount;
import ms.user.service.AuthService;
import ms.user.service.UserService;
 

@RequestScoped
@Path("auth")
public class AuthResource {
	
	private static final Logger log = LoggerFactory.getLogger(AuthResource.class);
	 
 
	@Inject
	private AuthService authService;
	 
	@Inject
	private UserService userService;

	/**
	 * This method creates a new User from the submitted data by the user.
	 */
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/login")
	@Transactional
	public Response login(@FormParam("username") String username, @FormParam("password") String password) {

		log.info("login: $1 ", username);

		try {
			String jwt = authService.authenticate(username, password);
			return Response.status(Response.Status.OK).entity(jwt).build();
		} catch (Exception e) {
			e.printStackTrace();

			return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
		}

	}
	
	
 
	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/register")
	@Transactional
	public Response register(
			@FormParam("username") String username, 
			@FormParam("email") String email,
			@FormParam("displayName") String displayName,
			@FormParam("password") String password
			
			) {
		User newUser = new User(username, email, displayName);
		newUser.setPwdHash(CoreUtils.hashPwd(newUser.getUserId() , password));
		if (!userService.findUser(username).isEmpty()) {
			return Response.status(Response.Status.BAD_REQUEST).entity("User already exists").build();
		}
		
		 
		userService.createUser(newUser);
		 
		
		return Response.status(Response.Status.NO_CONTENT).build();
	}

}

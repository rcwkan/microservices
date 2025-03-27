package ms.user.rest;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import app.core.util.CoreUtils;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;
import ms.user.dynamo.model.User;
import ms.user.service.AuthService;
import ms.user.service.UserService;

@Path("/auth")
@RequestScoped
public class AuthRestApi {

	private static final Logger log = Logger.getLogger(AuthRestApi.class);

	@Inject
	JsonWebToken jwt;

	@Inject
	AuthService authService;

	@Inject
	UserService userService;

	@POST
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@Path("/login")
	@Transactional
	public Response login(@FormParam("username") String username, @FormParam("password") String password) {

		log.info("login: " + username);

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
	public Response register(@FormParam("username") String username, @FormParam("email") String email,
			@FormParam("displayName") String displayName, @FormParam("password") String password) {

		try {
			userService.register(username, email, displayName, password);
			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

}

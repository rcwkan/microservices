package ms.user.api;

import org.jboss.logging.Logger;

import jakarta.annotation.security.PermitAll;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.FormParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ms.user.api.model.AuthResponse;
import ms.user.api.model.AuthResquest;
import ms.user.service.AuthService;
import ms.user.service.UserService;

@Path("/auth")
@RequestScoped
public class AuthResource {

	private static final Logger log = Logger.getLogger(AuthResource.class);
 
	@Inject
	@Named("authService")
	AuthService authService;

	@Inject
	@Named("userService")
	UserService userService;

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@PermitAll
	@Path("/login")
	public Response login(AuthResquest req) {

		if (req == null)
			return Response.status(Response.Status.UNAUTHORIZED).build();

		log.info("login: " + req.getUsername());

		try {
			String jwt = authService.authenticate(req.getUsername(), req.getPassword());
 
			return Response.ok(new AuthResponse(jwt)).build();
		} catch (Exception e) {
			e.printStackTrace();

			return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
		}

	}

	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@PermitAll
	@Path("/register")
	public Response register(AuthResquest req) {

		try {
			userService.register(req.getUsername(), req.getEmail(), req.getEmail(), req.getPassword());
			return Response.status(Response.Status.NO_CONTENT).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}
	}

}

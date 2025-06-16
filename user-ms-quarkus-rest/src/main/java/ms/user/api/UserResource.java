package ms.user.api;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;

import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.SecurityContext;

import ms.user.dynamo.model.User;
import ms.user.service.UserService;

@Path("/users")
@RequestScoped
public class UserResource {

	private static final Logger log = Logger.getLogger(UserResource.class);

	//@Inject
	//JsonWebToken jwt;

	@Inject
	@Named("userService")
	UserService userService;

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/test")
	@RolesAllowed({ "admin" })
	public Response test() {

		try {
			//log.info("test: " + securityContext.getUserPrincipal().getName());

			return Response.status(Response.Status.BAD_REQUEST).entity("test").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}

	}

	@GET
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/me")
	@PermitAll
	public Response getMyProfile(@Context SecurityContext securityContext) {

		try {
			log.info("getProfile: " + securityContext.getUserPrincipal().getName());
			User user = userService.readUser(securityContext.getUserPrincipal().getName());
			if (user != null)
				return Response.ok(user).build();
			else
				return Response.status(Response.Status.BAD_REQUEST).entity("User not found.").build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
		}

	}

}

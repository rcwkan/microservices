package ms.sync.resources;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import ms.sync.service.SyncService;

@Path("/sync")
@RequestScoped
public class SyncResource {
	
    private static final Logger LOG = Logger.getLogger(SyncResource.class);

	@Inject
	JsonWebToken jwt;

	@Inject
	SyncService syncService;

	@GET
	@Path("permit-all")
	@PermitAll
	@Produces(MediaType.TEXT_PLAIN)
	public String permitAll(@Context SecurityContext ctx) {
		
		LOG.info("hello :");
		return getResponseString(ctx);
	}

	@POST
	@Produces(MediaType.TEXT_PLAIN) 
	@RolesAllowed({ "user" })
	public String sync(@Context SecurityContext ctx) {
		return getResponseString(ctx);
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@RolesAllowed({ "Admin" })
	public String hello(@Context SecurityContext ctx) {
		return getResponseString(ctx);
	}

	private String getResponseString(SecurityContext ctx) {
		String name;
		if (ctx.getUserPrincipal() == null) {
			name = "anonymous";
		} else if (!ctx.getUserPrincipal().getName().equals(jwt.getName())) {
			throw new InternalServerErrorException("Principal and JsonWebToken names do not match");
		} else {
			name = ctx.getUserPrincipal().getName();
		}
		return String.format("hello %s," + " isHttps: %s," + " authScheme: %s," + " hasJWT: %s", name, ctx.isSecure(),
				ctx.getAuthenticationScheme(), hasJwt());
	}

	private boolean hasJwt() {
		return jwt.getClaimNames() != null;
	}

}

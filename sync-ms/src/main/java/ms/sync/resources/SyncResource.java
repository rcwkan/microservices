package ms.sync.resources;

import org.eclipse.microprofile.jwt.JsonWebToken;
import org.jboss.logging.Logger;

import io.quarkus.security.Authenticated;
import jakarta.annotation.security.PermitAll;
import jakarta.annotation.security.RolesAllowed;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.InternalServerErrorException;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.SecurityContext;
import ms.sync.dynamo.entity.SyncLog;
import ms.sync.model.FileResource;
import ms.sync.service.SyncService;

@Path("/sync")
@Authenticated 
public class SyncResource {

	private static final Logger log = Logger.getLogger(SyncResource.class);

	@Inject
	JsonWebToken jwt;

	@Inject
	SyncService syncService;
 
	@POST
	@Produces(MediaType.TEXT_PLAIN)
	 
	//@RolesAllowed({ "user" })
	public long sync(@Context SecurityContext ctx) {
		log.info("sync:" +jwt.getSubject() );
		
		SyncLog syncLog = new SyncLog(ctx.getUserPrincipal().getName());
		syncService.createSyncLog(syncLog);
		log.infov("sync: " + ctx.getUserPrincipal().getName() ); 
		return 0l;
	}
 
	@POST
	@Path("/file")
//	@RolesAllowed({ "user" })
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public boolean fileUpload(  FileResource upload) {
	    log.info("fileUpload   File path: "+ upload.file.getAbsolutePath() + " name:" + upload.fileName + "," + upload.id);
	    
		return true;
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN) 
	//@RolesAllowed({ "admin" })
//	@PermitAll
	public String check(@Context SecurityContext ctx) {
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

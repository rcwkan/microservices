package ms.sync.api;

import java.util.UUID;

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

	// @Inject
	// JsonWebToken jwt;

	@Inject
	SyncService syncService;

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	@RolesAllowed({ "user" })
	public String sync(@Context SecurityContext securityContext ) {

	 
		log.info("sync: " + securityContext.getUserPrincipal().getName());

		SyncLog syncLog = new SyncLog(UUID.randomUUID() ,  securityContext.getUserPrincipal().getName());
		syncService.createSyncLog(syncLog);

		return syncLog.getId().toString();
	}

	@POST
	@Path("/file")
	@RolesAllowed({ "user" })
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public boolean fileUpload(FileResource upload) {
		log.info("fileUpload   File path: " + upload.file.getAbsolutePath() + " name:" + upload.fileName + ","
				+ upload.id);

		return true;
	}

	@GET
	@Path("/check")
	@Produces(MediaType.TEXT_PLAIN)
	@RolesAllowed({ "user" })
//	@PermitAll
	public String check(@Context SecurityContext ctx) {
		return getResponseString(ctx);
	}

	private String getResponseString(SecurityContext ctx) {
		String name;
		if (ctx.getUserPrincipal() == null) {
			name = "anonymous";
		} else {
			name = ctx.getUserPrincipal().getName();
		}
		return String.format("hello %s," + " isHttps: %s," + " authScheme: %s,", name, ctx.isSecure(),
				ctx.getAuthenticationScheme());
	}

}

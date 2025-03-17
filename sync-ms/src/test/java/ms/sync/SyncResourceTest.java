package ms.sync;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;
import ms.sync.entity.SyncLog;
import ms.sync.model.FileResource;
import ms.sync.resources.SyncResource;
import ms.sync.service.SyncService;
import ms.sync.service.impl.SyncServiceImpl;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@QuarkusTest
@TestHTTPEndpoint(SyncResource.class)
public class SyncResourceTest {

	@TestHTTPResource("/sync")
	URL syncEndpoint;

	@TestHTTPResource("/sync/file")
	URL uploadEndpoint;

	@BeforeEach
	public void setup() {
		SyncService syncService = Mockito.mock(SyncServiceImpl.class);
		Mockito.when(syncService.createSyncLog(new SyncLog())).thenReturn(new SyncLog());
		QuarkusMock.installMockForType(syncService, SyncService.class);
	}

	//@Test
	@TestSecurity(user = "tester01", roles = { "user" })
	public void testSync() {

//		setup();
		given().when().post(syncEndpoint).then().statusCode(200);
	}

	@Test
	@TestSecurity(user = "tester01", roles = { "user" })
	public void testSyncAndUpload() throws IOException, URISyntaxException {

		FileResource fr = new FileResource();
		fr.id = 1l;
		fr.fileName = "test.txt"; 
		
		Path path  =Paths.get( this.getClass().getClassLoader().getResource(fr.fileName).toURI());
		fr.file = path.toFile();
//		System.out.println("file.getAbsolutePath():" + path.toString());
//		fr.data =  Files.readAllBytes(path);
		
		Map<String, Object> fromMap = new HashMap<>();
		fromMap.put("fileName", fr.fileName);
		fromMap.put("id", fr.id);
		 
		given().contentType("multipart/form-data").multiPart("file" ,path.toFile()).formParams(fromMap).post(uploadEndpoint).then().statusCode(200);
		 
	 
	}

}

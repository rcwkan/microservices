package ms.sync;

import static io.restassured.RestAssured.given;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import io.quarkus.test.InjectMock;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.common.http.TestHTTPResource;
import io.quarkus.test.junit.QuarkusMock;
import io.quarkus.test.junit.QuarkusTest;
import io.quarkus.test.security.TestSecurity;
import jakarta.inject.Inject;
import ms.sync.api.SyncResource;
import ms.sync.dynamo.entity.SyncLog;
import ms.sync.model.FileResource;
import ms.sync.service.SyncService;
import ms.sync.service.impl.SyncServiceImpl;

@QuarkusTest
@TestHTTPEndpoint(SyncResource.class) //root path configure
public class SyncResourceTest {

	//@TestHTTPResource("/sync")
	//URL syncEndpoint;

	@TestHTTPResource("/file")
	URL uploadEndpoint;
	
	@InjectMock
	SyncService syncService;

	@BeforeEach 
	public void setup() { 
		Mockito.when(syncService.createSyncLog(new SyncLog())).thenReturn(new SyncLog()); 
	}

	@Test
	@TestSecurity(user = "tester01", roles = { "user" })
	public void testSync() {
 
		given().when().get().then().statusCode(200);
	}

	@Test
	@TestSecurity(user = "tester01", roles = { "user" })
	public void testSyncAndUpload() throws IOException, URISyntaxException {

		FileResource fr = new FileResource();
		fr.id = 1l;
		fr.fileName = "test.txt"; 
		
		Path path  =Paths.get( this.getClass().getClassLoader().getResource(fr.fileName).toURI());
		fr.file = path.toFile();
		System.out.println("file.getAbsolutePath():" + path.toString());
//		fr.data =  Files.readAllBytes(path);
		
		Map<String, Object> fromMap = new HashMap<>();
		fromMap.put("fileName", fr.fileName);
		fromMap.put("id", fr.id);
		 
		given().contentType("multipart/form-data").multiPart("file" ,path.toFile()).formParams(fromMap).post(uploadEndpoint).then().statusCode(200);
		 
	 
	}

}

package ms.sync.dynamo;

import java.net.URI;
import java.util.Properties;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import software.amazon.awssdk.auth.credentials.SystemPropertyCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

public abstract class DynamoBaseRepository<T, K> {

	private static final Logger log = Logger.getLogger(DynamoBaseRepository.class);

	@ConfigProperty(name = "dynamodb.database", defaultValue="syncLog")
	protected String database;

	@ConfigProperty(name = "dynamodb.endpoint")
	protected String endpoint;

	@ConfigProperty(name = "dynamodb.region")
	protected String region;

	@ConfigProperty(name = "dynamodb.islocal", defaultValue="true")
	protected boolean islocal;
 
	@ConfigProperty(name = "dynamodb.awsaccesskey", defaultValue="123")
	protected String awsaccesskey;

	@ConfigProperty(name = "dynamodb.secretaccess", defaultValue="123")
	protected String secretaccess;

	protected DynamoDbEnhancedClient enhancedClient;

	private final Class<T> clazz;

	protected DynamoBaseRepository(Class<T> clazz) {

		this.clazz = clazz;
	}

	protected DynamoDbEnhancedClient getClient() {

		log.info("getClient database :" + database);

		if (enhancedClient != null)
			return enhancedClient;

		Properties props = System.getProperties();
		props.setProperty("aws.accessKeyId", awsaccesskey);
		props.setProperty("aws.secretAccessKey", secretaccess);

		Region awsRegion = Region.of(region);

		if (islocal) {
			final DynamoDbClient client = DynamoDbClient.builder()
					.credentialsProvider(SystemPropertyCredentialsProvider.create())
					.endpointOverride(URI.create(endpoint)).region(awsRegion).build();

			enhancedClient = DynamoDbEnhancedClient.builder().dynamoDbClient(client).build();

			return enhancedClient;

		} else {
			final DynamoDbClient client = DynamoDbClient.builder()
					.credentialsProvider(SystemPropertyCredentialsProvider.create()).region(awsRegion).build();

			enhancedClient = DynamoDbEnhancedClient.builder().dynamoDbClient(client).build();

			return enhancedClient;
		}

	}

	@SuppressWarnings("unchecked")
	protected DynamoDbTable<T> getTable() {

		return getClient().table(database, TableSchema.fromBean(clazz));
	}


	public T load(T t) {
		return getTable().getItem(t);
	}

	public void save(T t) {
		getTable().putItem(t);
	}
	
}

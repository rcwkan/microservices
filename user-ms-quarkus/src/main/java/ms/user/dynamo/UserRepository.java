package ms.user.dynamo;

import ms.user.dynamo.model.User;
import software.amazon.awssdk.auth.credentials.SystemPropertyCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;
import java.util.Properties;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.jboss.logging.Logger;

import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class UserRepository {

	private static final Logger log = Logger.getLogger(DynamoBaseRepository.class);

	@ConfigProperty(name = "dynamodb.database")
	protected String database;

	@ConfigProperty(name = "dynamodb.endpoint")
	protected String endpoint;

	@ConfigProperty(name = "dynamodb.region")
	protected String region;

	@ConfigProperty(name = "dynamodb.profile")
	protected String profile;

	@ConfigProperty(name = "dynamodb.awsaccesskey")
	protected String awsaccesskey;

	@ConfigProperty(name = "dynamodb.secretaccess")
	protected String secretaccess;

	protected DynamoDbEnhancedClient enhancedClient;
	protected DynamoDbTable<User> table;

	public UserRepository() {

	}

	protected DynamoDbEnhancedClient getClient() {

		log.info("getClient database :" + database);

		if (enhancedClient != null)
			return enhancedClient;

		Properties props = System.getProperties();
		props.setProperty("aws.accessKeyId", awsaccesskey);
		props.setProperty("aws.secretAccessKey", secretaccess);

		Region awsRegion = Region.of(region);

		final DynamoDbClient client = DynamoDbClient.builder()
				.credentialsProvider(SystemPropertyCredentialsProvider.create()).endpointOverride(URI.create(endpoint))
				.region(awsRegion).build();

		enhancedClient = DynamoDbEnhancedClient.builder().dynamoDbClient(client).build();

		return enhancedClient;
	}

	protected DynamoDbTable<User> getTable() {

		if (table != null)
			return table; 
		
		table = getClient().table("user", TableSchema.fromBean(User.class));
		return table;
	}

	public User load(User user) {
		return getTable().getItem(user);
	}

	public void save(User user) {
		getTable().putItem(user);
	}

	public User findById(String username) {
		User user = new User();
		user.setUsername(username);

		return load(user);
	}

}

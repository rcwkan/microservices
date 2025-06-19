package ms.notification.config;

import java.net.URI;

//import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.awspring.cloud.dynamodb.DynamoDbTemplate;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

@Configuration
//@EnableDynamoDBRepositories(basePackages = "ms.notification.dynamo.repository")
public class DynamoDBConfig {

	@Value("${dynamodb.endpoint:http://localhost:8000/}")
	private String amazonDynamoDBEndpoint;

	@Value("${dynamodb.region:ap-southeast-2}")
	private String amazonDynamoDBRegion;

	@Value("${dynamodb.awsaccesskey:key}")
	private String amazonAWSAccessKey;

	@Value("${dynamodb.secretaccess:key2}")
	private String amazonAWSSecretKey;

	@Value("${dynamodb.islocal:true}")
	private boolean isLocal;

	@Bean
	public DynamoDbClient dynamoDbClient() {
		if (isLocal) {
			return DynamoDbClient.builder().endpointOverride(URI.create(amazonDynamoDBEndpoint))
					.region(Region.of(amazonDynamoDBRegion)).credentialsProvider(StaticCredentialsProvider
							.create(AwsBasicCredentials.create(amazonAWSAccessKey, amazonAWSSecretKey)))
					.build();
		}

		return DynamoDbClient.builder().region(Region.of(amazonDynamoDBRegion)).credentialsProvider(
				StaticCredentialsProvider.create(AwsBasicCredentials.create(amazonAWSAccessKey, amazonAWSSecretKey)))
				.build();
	}

	@Bean
	public DynamoDbEnhancedClient dynamoDbEnhancedClient() {
		return DynamoDbEnhancedClient.builder().dynamoDbClient(dynamoDbClient()).build();
	}

	@Bean
	public DynamoDbTemplate dynamoDbTemplate() {
		return new DynamoDbTemplate(dynamoDbEnhancedClient());
	}

}
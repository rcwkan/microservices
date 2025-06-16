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

	@Value("${amazon.dynamodb.endpoint}")
	private String amazonDynamoDBEndpoint;

	@Value("${amazon.dynamodb.region}")
	private String amazonDynamoDBRegion;

	@Value("${amazon.aws.accesskey}")
	private String amazonAWSAccessKey;

	@Value("${amazon.aws.secretkey}")
	private String amazonAWSSecretKey;

	@Bean
	public DynamoDbClient dynamoDbClient() {
		return DynamoDbClient.builder().endpointOverride(URI.create(amazonDynamoDBEndpoint)).region(Region.of(amazonDynamoDBRegion))
				.credentialsProvider(StaticCredentialsProvider
						.create(AwsBasicCredentials.create(amazonAWSAccessKey, amazonAWSSecretKey)))
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
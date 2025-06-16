package ms.notification.config;


//import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

 
 
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
/*
	@Bean
	public AmazonDynamoDB amazonDynamoDB() {
		
		AmazonDynamoDBClientBuilder builder = AmazonDynamoDBClientBuilder.standard()
				.withEndpointConfiguration(new EndpointConfiguration(amazonDynamoDBEndpoint,amazonDynamoDBRegion))
				  .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey)));
		 
		 
		return  builder.build();
	}

	@Bean
	public AWSCredentials amazonAWSCredentials() {
		return new BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey);
	}
*/
}
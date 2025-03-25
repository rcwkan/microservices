package ms.notification.adaptor.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Regions;
//import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
//import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
//import com.amazonaws.services.simpleemail.model.Body;
//import com.amazonaws.services.simpleemail.model.Content;
//import com.amazonaws.services.simpleemail.model.Destination;
//import com.amazonaws.services.simpleemail.model.Message;
//import com.amazonaws.services.simpleemail.model.SendEmailRequest;

//import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import jakarta.annotation.PostConstruct;
import ms.notification.adaptor.EmailAdaptor;

@Service
public class AmazonSES implements EmailAdaptor {

	private static final Logger log = LoggerFactory.getLogger(AmazonSES.class);

	AWSCredentialsProvider credentialsProvider;

	@Value("${ms.core.aws.secret.key}")
	String awsSecretKey;

	@Value("${ms.core.aws.access.key.id}")
	String awsAccessKeyId;

	@PostConstruct
	public void init() {

		credentialsProvider = new AWSCredentialsProvider() {
			@Override
			public void refresh() {
			}

			@Override
			public AWSCredentials getCredentials() {
				return new AWSCredentials() {
					@Override
					public String getAWSSecretKey() {
						return awsSecretKey;
					}

					@Override
					public String getAWSAccessKeyId() {
						return awsAccessKeyId;
					}
				};
			}
		};
	}

//	@CircuitBreaker(name = "externalEmailService", fallbackMethod = "sendFallback")
	public boolean send(String from, String to, String subject, String htmlContent, String textContent)
			throws Exception {

		try {
			/*
			AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
					.withRegion(Regions.AP_SOUTHEAST_1).withCredentials(credentialsProvider).build();

			Message msg = new Message().withBody(new Body())
					.withSubject(new Content().withCharset("UTF-8").withData(subject));

			if (textContent != null && !textContent.isEmpty())
				msg.getBody().withHtml(new Content().withCharset("UTF-8").withData(htmlContent));
			if (textContent != null && !textContent.isEmpty())
				msg.getBody().withText(new Content().withCharset("UTF-8").withData(textContent));

			SendEmailRequest request = new SendEmailRequest().withDestination(new Destination().withToAddresses(to))
					.withMessage(msg).withSource(from);

			client.sendEmail(request);
			*/
			return true;
		} catch (Exception ex) {
			log.warn("The email was not sent. Error message: " + ex.getMessage(), ex);
			throw new Exception(ex.getMessage());
		}
 
	}

	public boolean sendFallback(String from, String to, String subject, String htmlContent, String textContent,Exception e) {
		 
		log.warn("sendFallback @CircuitBreaker" + e.getMessage());
		return false;

	}

}

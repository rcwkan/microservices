package ms.notification.adaptor.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.Body;
import com.amazonaws.services.simpleemail.model.Content;
import com.amazonaws.services.simpleemail.model.Destination;
import com.amazonaws.services.simpleemail.model.Message;
import com.amazonaws.services.simpleemail.model.SendEmailRequest;

import app.core.jwt.JwtUtils;
import ms.notification.adaptor.EmailAdaptor;
import ms.notification.service.impl.JwtUserDetailsService;
 
@Service
public class AmazonSES implements EmailAdaptor {
	
	
	private static final Logger log = LoggerFactory.getLogger(AmazonSES.class);
 

	static final String CONFIGSET = "ConfigSet";

	public boolean send(String from, String to, String subject, String htmlContent, String textContent)
			throws IOException {

		try {
			AmazonSimpleEmailService client = AmazonSimpleEmailServiceClientBuilder.standard()
					.withRegion(Regions.AP_SOUTHEAST_1).build();

			Message msg = new Message().withBody(new Body())
					.withSubject(new Content().withCharset("UTF-8").withData(subject));

			if (textContent != null && !textContent.isEmpty())
				msg.getBody().withHtml(new Content().withCharset("UTF-8").withData(htmlContent));
			if (textContent != null && !textContent.isEmpty())
				msg.getBody().withText(new Content().withCharset("UTF-8").withData(textContent));

			SendEmailRequest request = new SendEmailRequest().withDestination(new Destination().withToAddresses(to))
					.withMessage(msg).withSource(from);
//					// Comment or remove the next line if you are not using a
//					// configuration set
//					.withConfigurationSetName(CONFIGSET);

			client.sendEmail(request);
			return true;
		} catch (Exception ex) {
			log.warn("The email was not sent. Error message: " + ex.getMessage(), ex);
		}

		return false;
	}

}

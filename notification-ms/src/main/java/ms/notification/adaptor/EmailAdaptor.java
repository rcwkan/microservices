package ms.notification.adaptor;

import java.io.IOException;

public interface EmailAdaptor {
	
	
	public boolean send(String from, String to, String subject, String htmlContent, String textContent) throws IOException;

}

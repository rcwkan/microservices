package ms.notification.adaptor;

public interface EmailAdaptor {
	
	
	public boolean send(String from, String to, String subject, String htmlContent, String textContent) throws Exception;

}

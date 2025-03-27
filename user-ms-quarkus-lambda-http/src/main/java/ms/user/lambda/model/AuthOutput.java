package ms.user.lambda.model;

public class AuthOutput {

	private String requestId;
	private String jwt;
	
	private String err;

	public AuthOutput() {
	}
	
	public AuthOutput(String requestId ) {
		this.requestId = requestId;
	 
	}

	public AuthOutput(String requestId, String jwt) {
		this.requestId = requestId;
		this.jwt = jwt;
	}
	
	public AuthOutput(String requestId, String jwt,String err) {
		this.requestId = requestId;
		this.jwt = jwt;
		this.err = err;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public String getErr() {
		return err;
	}

	public void setErr(String err) {
		this.err = err;
	}

	
}

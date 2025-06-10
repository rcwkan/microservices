package ms.user.api;

public class AuthResponse {

	String jwt;

	public AuthResponse() {
		
	}
	public AuthResponse(String jwt) {
		this.jwt = jwt;
	}

	public String getJwt() {
		return jwt;
	}

}

package ms.user.api.model;

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

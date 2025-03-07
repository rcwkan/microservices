package ms.notification.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class JwtUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	
	private String jwt;
	private String username;
	private List<String> groups;
	private List<String> roles;

	List<SimpleGrantedAuthority> authoritie;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		if (authoritie == null)
			authoritie = new ArrayList<>();

		for (String r : roles) {
			authoritie.add(new SimpleGrantedAuthority(r));
		}

		return authoritie;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getUsername() {

		return username;
	}

	public String getJwt() {
		return jwt;
	}

	public void setJwt(String jwt) {
		this.jwt = jwt;
	}

	public List<String> getGroups() {
		return groups;
	}

	public void setGroups(List<String> groups) {
		this.groups = groups;
	}

	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public void setUsername(String username) {
		this.username = username;
	}

}

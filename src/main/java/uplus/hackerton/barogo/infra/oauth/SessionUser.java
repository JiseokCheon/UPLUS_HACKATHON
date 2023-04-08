package uplus.hackerton.barogo.infra.oauth;

import java.io.Serializable;

import lombok.Getter;
import uplus.hackerton.barogo.domain.User;

@Getter
public class SessionUser implements Serializable {
	private final String name;
	private final String email;
	private final String picture;

	public SessionUser(User user) {
		this.name = user.getName();
		this.email = user.getEmail();
		this.picture = user.getPhoto();
	}
}

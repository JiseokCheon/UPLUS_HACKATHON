package uplus.hackerton.barogo.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "d_users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 30)
	private String name;

	@Column(length = 100, unique = true)
	private String email;

	private String photo;

	private Role role;

	@Column(length = 100)
	private String activeArea;

	@Builder
	public User(String name, String email, String photo, Role role, String activeArea) {
		this.name = name;
		this.email = email;
		this.photo = photo;
		this.role = role;
		this.activeArea = activeArea;
	}

	public User update(String name, String picture) {
		this.name = name;
		this.photo = picture;

		return this;
	}

	public String getAuthority() {
		return this.role.getKey();
	}
}

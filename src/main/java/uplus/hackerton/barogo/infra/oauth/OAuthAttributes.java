package uplus.hackerton.barogo.infra.oauth;

import java.util.Map;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import uplus.hackerton.barogo.domain.Role;
import uplus.hackerton.barogo.domain.User;

@Slf4j
@Getter
public class OAuthAttributes {
	public static final String EMAIL_LITERAL = "email";
	private static final String NAVER = "naver";
	private static final String GITHUB = "github";
	private final Map<String, Object> attributes;
	private final String nameAttributeKey;
	private final String name;
	private final String email;
	private final String picture;

	@Builder
	public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey, String name, String email,
		String picture) {
		this.attributes = attributes;
		this.nameAttributeKey = nameAttributeKey;
		this.name = name;
		this.email = email;
		this.picture = picture;
	}

	public static OAuthAttributes of(String registrationId, String userNameAttributeName,
		Map<String, Object> attributes) {
		log.info("OAuthAttributes == {} {} {}", registrationId, userNameAttributeName, attributes.toString());

		if (registrationId.equals(NAVER)) {
			return ofNaver("id", attributes);
		} else if (registrationId.equals(GITHUB)) {
			return ofGithub(userNameAttributeName, attributes);
		}
		return ofGoogle(userNameAttributeName, attributes);
	}

	private static OAuthAttributes ofGithub(String userNameAttributeName, Map<String, Object> attributes) {
		return OAuthAttributes.builder()
			.name((String)attributes.get("login"))
			.email((String)attributes.get(EMAIL_LITERAL))
			.picture((String)attributes.get("avatar_url"))
			.attributes(attributes)
			.nameAttributeKey(userNameAttributeName)
			.build();
	}

	private static OAuthAttributes ofGoogle(String userNameAttributeName, Map<String, Object> attributes) {
		return OAuthAttributes.builder()
			.name((String)attributes.get("name"))
			.email((String)attributes.get(EMAIL_LITERAL))
			.picture((String)attributes.get("picture"))
			.attributes(attributes)
			.nameAttributeKey(userNameAttributeName)
			.build();
	}

	private static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
		Map<String, Object> response = (Map<String, Object>)attributes.get("response");

		return OAuthAttributes.builder()
			.name((String)response.get("name"))
			.email((String)response.get(EMAIL_LITERAL))
			.picture((String)response.get("profile_image"))
			.attributes(response)
			.nameAttributeKey(userNameAttributeName)
			.build();
	}

	public User toUser() {
		return User.builder()
			.name(name)
			.email(email)
			.photo(picture)
			.role(Role.MEMBER)
			.build();
	}
}

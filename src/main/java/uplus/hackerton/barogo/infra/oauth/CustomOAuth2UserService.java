package uplus.hackerton.barogo.infra.oauth;

import java.util.Collections;

import javax.servlet.http.HttpSession;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import uplus.hackerton.barogo.domain.User;
import uplus.hackerton.barogo.repository.UserRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
	private final UserRepository userRepository;
	private final HttpSession httpSession;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2User = getOAuthUser(userRequest);
		log.info("loadUser== {}", oAuth2User.toString());

		OAuthAttributes attributes = getOAuthAttributes(userRequest, oAuth2User);

		User user = saveOrUpdateUser(attributes);

		saveUserInfoInSession(user);

		return new DefaultOAuth2User(
			Collections.singleton(new SimpleGrantedAuthority(user.getAuthority())),
			attributes.getAttributes(),
			attributes.getNameAttributeKey());
	}

	private void saveUserInfoInSession(User user) {
		httpSession.setAttribute("login_user", new SessionUser(user));
	}

	private OAuthAttributes getOAuthAttributes(OAuth2UserRequest userRequest, OAuth2User oAuth2User) {
		String registrationId = userRequest.getClientRegistration().getRegistrationId();
		String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
			.getUserInfoEndpoint().getUserNameAttributeName();
		return OAuthAttributes.of(registrationId, userNameAttributeName,
			oAuth2User.getAttributes());
	}

	private OAuth2User getOAuthUser(OAuth2UserRequest userRequest) {
		OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
		return delegate.loadUser(userRequest);
	}

	private User saveOrUpdateUser(OAuthAttributes attributes) {
		log.info("saveOrUpdte --{}", attributes.toString());
		User user = userRepository.findByEmail(attributes.getEmail())
			.map(entity -> entity.update(attributes.getName(), attributes.getPicture()))
			.orElse(attributes.toUser());

		return userRepository.save(user);
	}
}

package uplus.hackerton.barogo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;
import uplus.hackerton.barogo.domain.Role;
import uplus.hackerton.barogo.infra.oauth.CustomOAuth2UserService;

@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

	private final CustomOAuth2UserService customOAuth2UserService;

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http
			.csrf().disable()
			.headers().frameOptions().disable()
			.and()
			.authorizeRequests()
			.antMatchers("/", "/delivery/**", "/assets/**", "/css/**", "/h2-console/**",
				"/oauth2/**", "/login/**", "/profile").permitAll()
			.antMatchers("/api/v1/**", "/order/**").hasRole(Role.MEMBER.name())
			.anyRequest().authenticated()
			.and()
			.logout()
			.logoutSuccessUrl("/")
			.and()
			.oauth2Login().loginPage("/login/signIn")
			.userInfoEndpoint()         //로그인 성공이후 사용자 정보 가져올때
			.userService(customOAuth2UserService);      //oauth로그인 성공시 후속조치
		return http.build();
	}

}

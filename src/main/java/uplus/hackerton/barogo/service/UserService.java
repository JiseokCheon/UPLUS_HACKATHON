package uplus.hackerton.barogo.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import uplus.hackerton.barogo.domain.User;
import uplus.hackerton.barogo.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public User getUserByEmail(String email) {
		Optional<User> optionalUser = userRepository.findByEmail(email);
		if (optionalUser.isEmpty()) {
			throw new IllegalArgumentException(String.format("이메일 : %s 의 유저는 존재하지 않습니다", email));
		}
		return optionalUser.get();
	}
}

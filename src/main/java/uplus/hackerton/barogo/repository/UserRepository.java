package uplus.hackerton.barogo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import uplus.hackerton.barogo.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByEmail(String email);
}

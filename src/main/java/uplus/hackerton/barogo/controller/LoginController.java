package uplus.hackerton.barogo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

	@GetMapping("/signIn")      //로그인 화면
	public String signIn() {
		return "login/signIn";
	}

	@GetMapping("/info")       //서비스 소개
	public String info() {
		return "login/info";
	}

	@GetMapping("/comingSoon")
	public String commingSoon() {
		return "login/comingSoon";
	}

}

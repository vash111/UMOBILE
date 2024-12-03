package org.zerock.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequiredArgsConstructor
public class TestController {

	private final KakaoApi kakaoApi;

	@GetMapping("/user/loginTest")
	public String loginForm(Model model) {
		model.addAttribute("kakaoApiKey", kakaoApi.getKakaoApiKey());
		model.addAttribute("redirectUri", kakaoApi.getKakaoRedirectUri());
		
		log.info(kakaoApi.getKakaoApiKey());
		log.info(kakaoApi.getKakaoRedirectUri());
		
		log.info("---testLogin 진입---");
		log.info("model => " + model);
		
		return "/user/loginTest";
	}

	@RequestMapping("/login/oauth2/code/kakao")
	public String kakaoLogin(@RequestParam String code) {
		
		log.info("---kakao-login 진입---");

		// 1. 인가 코드 받기 (@RequestParam String code)
		
		System.out.println("code => " + code);

		// 2. 토큰 받기
		String accessToken = kakaoApi.getAccessToken(code);
		System.out.println("accessToken => " + accessToken);

		// 3. 사용자 정보 받기
		Map<String, Object> userInfo = kakaoApi.getUserInfo(accessToken);
		System.out.println(userInfo);

		String email = (String) userInfo.get("email");
		String nickname = (String) userInfo.get("nickname");

		System.out.println("email = " + email);
		System.out.println("nickname = " + nickname);
		System.out.println("accessToken = " + accessToken);

		return "redirect:/";
	}

}

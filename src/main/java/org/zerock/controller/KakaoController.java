package org.zerock.controller;


import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import lombok.extern.log4j.Log4j;

@Controller
@Log4j
@RequestMapping("/kakao-login")
public class KakaoController {

	@GetMapping
	public String getAccessToken(@RequestParam("code") String code) {
		// 액세스 토큰 요청
		log.info(code);
		
		//1.header 생성
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/x-www-form-urlencoded;charset=utf-8");
		
		//2. body 생성
		MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		params.add("grant_type", "authorization_code");
		params.add("client_id", "70c4d976b0b45b6e04372cf2a3ebf3aa");
		params.add("redirect_uri", "http://localhost:8080/kakao-login");
		params.add("code", code);
		
		//3. header + body
		HttpEntity<MultiValueMap<String, String>> httpEntity =  new HttpEntity<MultiValueMap<String,String>>(params, httpHeaders);
		
		//4. http 요청하기
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response = restTemplate.exchange
				("https://kauth.kakao.com/oauth/token", 
						HttpMethod.POST, 
						httpEntity, 
						Object.class);
		
		System.out.println("response = " + response);
		
		return "/";
	}
}

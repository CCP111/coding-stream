package com.example.spring.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.spring.service.auth.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	private final AuthService authService;
	
	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	@PostMapping("/login")
	public ResponseEntity<AuthResponeDto> login(@RequestBody AuthRequestDto authResponeDto) {
		var jwtToken = authService.login(authResponeDto.username(), authResponeDto.password());
		var authResponseDto = new AuthResponeDto(jwtToken, AuthenStatus.LOGIN_SUCCESS);
		
		return ResponseEntity
				.status(HttpStatus.OK)
				.body(authResponseDto);
	}
	
	@PostMapping("/sign-up")
	public ResponseEntity<AuthResponeDto> signUp(@RequestBody AuthRequestDto authResponeDto) {
		try {
			var jwtToken = authService.signUp(authResponeDto.name() ,authResponeDto.username(), authResponeDto.password());
			var authResponseDto = new AuthResponeDto(jwtToken, AuthenStatus.USER_CREATED_SUCCESSFULLY);
			
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(authResponseDto);
		} catch (Exception e) {
			var authResponseDto = new AuthResponeDto(null, AuthenStatus.USER_NOT_CREATED_SUCCESSFULLY);
			
			return ResponseEntity
					.status(HttpStatus.CONFLICT)
					.body(authResponseDto);
		}
	}
}

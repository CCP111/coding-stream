package com.example.spring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityFilterChainConfig {
	private final AuthenticationEntryPoint authenticationEntryPoint;
	private final JWTAuthenticationFilter jwtAuthenticationFilter;
	
	public SecurityFilterChainConfig(AuthenticationEntryPoint authenticationEntryPoint, JWTAuthenticationFilter jwtAuthenticationFilter) {
		this.authenticationEntryPoint = authenticationEntryPoint;
		this.jwtAuthenticationFilter = jwtAuthenticationFilter;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		//disable Cors
		httpSecurity.cors(corsConfig->corsConfig.disable());
		//disable CSRF
		httpSecurity.csrf(AbstractHttpConfigurer::disable);
		
		//Http request filter
		httpSecurity.authorizeHttpRequests(
				requestMatcher->requestMatcher.requestMatchers("/api/auth/login/**").permitAll()
								.requestMatchers("/api/auth/sign-up/**").permitAll()
								.anyRequest().authenticated()
				);
		
		//authentication entry point -> exception handler
		httpSecurity.exceptionHandling(
				exceptionConfig->exceptionConfig.authenticationEntryPoint(authenticationEntryPoint)
				);
		
		//set sesion policy = STATELESS
		httpSecurity.sessionManagement(
				sessionConfig->sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				);
		
		//add JWT Authenticaiton Filter
		httpSecurity.addFilterBefore(jwtAuthenticationFilter,
				UsernamePasswordAuthenticationFilter.class
				);
		
		return httpSecurity.build();
	}
}

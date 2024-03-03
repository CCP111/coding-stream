package com.example.spring.model;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "tb_sttm_user")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AppUser {
	@Id
	private String id;
	private String name;
	private String username;
	private String password;
	private List<GrantedAuthority> authorities;
}

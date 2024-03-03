package com.example.spring.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.spring.model.AppUser;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser, String>{

	boolean existsByUsername(String username);

	Optional<AppUser> findByUsername(String username);
}

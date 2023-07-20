package com.assginment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.assginment.Model.User;
import com.assginment.Repo.UserRepository;

@Configuration
@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	public User save(User user) {
		User saveUser=new User();
		System.out.println("user" + user.getPassword());
		saveUser.setFirstName(user.getFirstName());
		saveUser.setLastName(user.getLastName());
		saveUser.setEmail(user.getEmail());
		saveUser.setPassword(passwordEncoder().encode(user.getPassword()));
		
		User savedUser = userRepository.save(saveUser);
		
		return savedUser;
	}
}

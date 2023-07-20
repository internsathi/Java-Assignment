package com.assginment.service;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.assginment.Model.User;

@Service
public class LoginService {
	private final UserService userService;
	private final BCryptPasswordEncoder passwordEncoder;

	public LoginService(UserService userService, BCryptPasswordEncoder passwordEncoder) {
		this.userService = userService;
		this.passwordEncoder = passwordEncoder;
	}

	public boolean authenticateUser(String email, String password) {
		User user = userService.getUserByEmail(email);
		if (user == null) {
			// User not found in the database
			return false;
		}

		// Compare hashed inputted password with the hashed password from the database
		return passwordEncoder.matches(password, user.getPassword());
	}
}

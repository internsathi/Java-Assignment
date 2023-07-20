package com.assginment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.assginment.Model.User;
import com.assginment.Repo.UserRepository;
import com.assginment.exception.EmailAlreadyExistsException;

@Configuration
@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	public User save(User user) throws EmailAlreadyExistsException {
//		// Check if the email already exists

		if (userRepository.existsByEmail(user.getEmail())) {
			throw new EmailAlreadyExistsException("Email already exists.");
		}
		User saveUser = new User();
		System.out.println("user" + user.getPassword());
		saveUser.setFirstName(user.getFirstName());
		saveUser.setLastName(user.getLastName());
		saveUser.setEmail(user.getEmail());
		saveUser.setPassword(passwordEncoder.encode(user.getPassword()));
		saveUser.setRole("USER");

		User savedUser = userRepository.save(saveUser);

		return savedUser;
	}

	public User getUserByEmail(String email) {
		return userRepository.getUserByEmail(email);
	}

}

package com.assginment.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.assginment.exception.EmailAlreadyExistsException;
import com.assginment.model.User;
import com.assginment.repo.UserRepository;

@Configuration
@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	//method to save user in database
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
//method to get user by email from database
	public User getUserByEmail(String email) {
		return userRepository.getUserByEmail(email);
	}
	
	//method to encode new password and call repo function to save in database
	@Transactional
	public void updatePassword(User user, String password) {
		user.setPassword(passwordEncoder.encode(password));
        userRepository.updatePassword(user.getEmail(), user.getPassword());
	}

}

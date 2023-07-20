package com.assginment.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int userId;
	@NotBlank(message = "Firstname cannot be blank")
	@Size(message = "Firstname must be atleast 3 characters")
	private String firstName;
	@NotBlank(message = "Lastname cannot be blank")
	@Size(message = "Lastname must be atleast 3 characters")
	private String lastName;
	@NotBlank(message = "Email cannot be blank")
	@Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$" , message = "Invalid Email !!")
	private String email;
	@NotBlank(message = "Passwordcannot be blank")
	@Size(message = "Password must be atleast 6 characters")
	private String password;
	private String role;
	private String enabled;
	
	
}

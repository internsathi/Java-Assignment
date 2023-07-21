package com.assginment.controller;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.assginment.Helper.Message;
import com.assginment.exception.EmailAlreadyExistsException;
import com.assginment.model.User;
import com.assginment.service.UserService;

@Controller

public class SignupController {
	

	@Autowired
	private UserService userService;

//	handler to open signup page
	@GetMapping("/signup")
	public String signUp(Model model,HttpSession session) {
		String email2 = (String) session.getAttribute("email");
		System.out.println(email2);
		if(email2!=null) {
			return "redirect:/home";
		}
		model.addAttribute("title", "Sign Up Page");
		model.addAttribute("user", new User());
		return "signup";
	}

//	handler for processing signup page
	@PostMapping("/process-signup")
	public String processSignUp(@Valid @ModelAttribute("user") User user, BindingResult result, Model model,
			HttpSession session) {
		if (result.hasErrors()) {
			System.out.println(result);
			return "signup";
		}

		try {
			User savedUser = userService.save(user);
			System.out.println("savedUser: " + savedUser);
			if(savedUser==null) {
				 Message message = new Message("Failed to Register", "danger");
				session.setAttribute("message",message);
				
				System.out.println(message.getType());
				return "signup";
			}
		} catch (EmailAlreadyExistsException e) {
			result.rejectValue("email", "error.user", "Email already exists");
			return "signup";
		}
		 Message message = new Message("Successfully Registered!! You Can Login Now", "success");
		 System.out.println(message.getType());
		session.setAttribute("message",message);
		return "redirect:/login";
	}



	
}

package com.assginment.Controller;

import java.security.Principal;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.assginment.Helper.Message;
import com.assginment.Model.User;
import com.assginment.exception.EmailAlreadyExistsException;
import com.assginment.service.LoginService;
import com.assginment.service.UserService;

@Controller

public class HomeController {
	@Autowired
	 private LoginService loginService;


	@Autowired
	private UserService userService;

//	handler to open signup page
	@GetMapping("/signup")
	public String signUp(Model model) {
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
//	handler to open login page
	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("title", "Login Page");
		return "login";
	}
	

//	handler to open home page
	@GetMapping("/home")
	public String home(@RequestParam(name = "email", required = false) String email, Model model,HttpSession session) {
		System.out.println("Hello");
		String email2 = (String) session.getAttribute("email");
	    model.addAttribute("email", email2);
	    session.removeAttribute("email");
		return "home";
	}
	
	@PostMapping("/process-login")
	public String processLogin(@ModelAttribute("User")User user,HttpSession session,Model model) {
		System.out.println("username: "+user.toString());
		 if (loginService.authenticateUser(user.getEmail(), user.getPassword())) {
			 session.setAttribute("email", user.getEmail());
	           	return "redirect:/home";
	        } else {
	            session.setAttribute("message", new Message("Wrong Email or Password", "danger"));
	            return "redirect:/login?error";
	        }
		
	}

}

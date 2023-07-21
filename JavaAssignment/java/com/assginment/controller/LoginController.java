package com.assginment.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.assginment.Helper.Message;
import com.assginment.model.User;
import com.assginment.service.LoginService;

@Controller
public class LoginController {
	
	@Autowired
	 private LoginService loginService;

	
//	handler to open login page
	@GetMapping("/login")
	public String login(Model model,HttpSession session) {
		String email2 = (String) session.getAttribute("email");
		if(email2!=null) {
			return "redirect:/home";
		}
		model.addAttribute("title", "Login Page");
		return "login";
	}
	
//	handler to process login details
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
	
	@GetMapping("/logout")
	public String logout(HttpSession session,Model model){
		session.removeAttribute("email");
		return "redirect:/login";
	}
	

	
}

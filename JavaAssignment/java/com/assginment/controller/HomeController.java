package com.assginment.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.assginment.model.User;
import com.assginment.repo.UserRepository;


@Controller
public class HomeController {
	
	@Autowired
	private UserRepository userRepository;
	
//	handler to open home page
	@GetMapping("/home")
	public String home(@RequestParam(name = "email", required = false) String email, Model model,HttpSession session) {
	
		String email2 = (String) session.getAttribute("email");
		if(email2==null) {
			return "redirect:/login";
		}
		User user = this.userRepository.getUserByEmail(email2);
		String firstName = user.getFirstName();
		
	    model.addAttribute("firstName", firstName);
	    
	    model.addAttribute("title","Home Page");
		return "home";
	}
	
	
	
	
}

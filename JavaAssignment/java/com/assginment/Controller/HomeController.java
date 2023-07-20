package com.assginment.Controller;

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
import com.assginment.Model.User;
import com.assginment.service.UserService;

@Controller
public class UserController {

	@Autowired
	private UserService userService;
	
//	handler to open signup page
	@GetMapping("/signup")
	public String signUp(Model model) {
		model.addAttribute("title","Sign Up Page");
		model.addAttribute("user",new User());
		return "signup";
	}
	
//	handler for processing signup page
	@PostMapping("/process-signup")
	public String processSignUp(@Valid @ModelAttribute("user") User user,BindingResult result,Model model,HttpSession session) {
		if(result.hasErrors()) {
			System.out.println(result);
			return "signup";
		}
		
		User savedUser = userService.save(user);
		System.out.println("savedUser: "+savedUser);
		if(savedUser==null) {
			session.setAttribute("message", new Message("Failed to Register", "danger"));
			
			return "signup";
		}
		session.setAttribute("message",new Message("Successfully Registered!! You Can Login Now", "success"));
		return "redirect:/login";
		
	}
	
	
//	handler to open login page
	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("title","Login Page");
		return "login";
	}
	

}

package com.assginment.Controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.assginment.Model.User;
import com.assginment.Repo.UserRepository;

@Controller
public class UserController {

	@Autowired
	private UserRepository userRepository;
	
//	handler to open signup page
	@GetMapping("/signup")
	public String signUp(Model model) {
		model.addAttribute("title","Sign Up Page");
		model.addAttribute("user",new User());
		return "signup";
	}
	
//	handler for processing signup page
	@PostMapping("/process-signup")
	public String processSignUp(@Valid @ModelAttribute("user") User user,BindingResult result,Model model) {
		if(result.hasErrors()) {
			System.out.println(result);
			return "signup";
		}
		System.out.println("user "+user.toString());
		userRepository.save(user);
		return "home";
		
	}
	
	
//	handler to open login page
	@GetMapping("/login")
	public String login(Model model) {
		model.addAttribute("title","Login Page");
		return "login";
	}
	

}

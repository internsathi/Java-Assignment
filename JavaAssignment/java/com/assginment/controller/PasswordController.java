package com.assginment.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.assginment.Helper.Message;
import com.assginment.model.User;
import com.assginment.repo.UserRepository;
import com.assginment.service.LoginService;
import com.assginment.service.UserService;

@Controller
public class PasswordController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LoginService loginService;
	@Autowired
	private UserService userService;

	@GetMapping("/change-password")
	public String changePassword(HttpSession session, Model model) {
		String email2 = (String) session.getAttribute("email");
		if (email2 == null) {
			return "redirect:/login";
		}
		User user = this.userRepository.getUserByEmail(email2);
		String firstName = user.getFirstName();

		model.addAttribute("firstName", firstName);
		model.addAttribute("title", "Change Password");
		return "change_password.html";

	}

	
//	handler for processing update passsword
	@PostMapping("/process-password")
	public String processPassword(@RequestParam("password") String oldPassword,
			@RequestParam("newPassword") String newPassword, @RequestParam("rePassword") String rePassword,
			HttpSession session) {

		System.out.println("oldPassword: " + oldPassword + " newPassword: " + newPassword + " rePassword: " + rePassword);
		if(oldPassword.isEmpty() || newPassword.isEmpty() || rePassword.isEmpty()) {
			session.setAttribute("message", new Message("Field cannot be empty", "danger"));
			return "redirect:/change-password";
		}else if(newPassword.length()<6){
			session.setAttribute("message", new Message("Password must be atleast 6 characters", "danger"));
			return "redirect:/change-password";
		}else {
			String email = (String) session.getAttribute("email");
			User user = this.userRepository.getUserByEmail(email);
			//checks if inputed old password matches with password save in database 
			if (loginService.authenticateUser(user.getEmail(), oldPassword)) {
				
				Boolean result=newPassword.matches(rePassword);
				System.out.println(result);
				//checks if inputed new password matches with input confirm password
				if(!newPassword.matches(rePassword)) {
					session.setAttribute("message", new Message("New and Confirm Password didn't match", "danger"));
				}else {
					userService.updatePassword(user, rePassword);
					session.setAttribute("message", new Message("Password Updated Successfully", "success"));
					
				}
			}else {
				session.setAttribute("message", new Message("Old Password didn't match", "danger"));
			}
		}

		
		return "redirect:/change-password";
	}

}

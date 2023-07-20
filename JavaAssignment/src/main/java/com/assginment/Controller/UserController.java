package com.assginment.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@Controller
public class UserController {

	public String signUp(Model model) {
		return "signup";
	}
}

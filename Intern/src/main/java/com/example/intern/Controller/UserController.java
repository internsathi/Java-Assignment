package com.example.intern.Controller;

import com.example.intern.entity.User;
import com.example.intern.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping({"/", "/index"})
    public String index(Model model) {
        model.addAttribute("title","Register Service");
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    // handler method to handle user registration request
    @GetMapping("register")
    public String showRegistrationForm(Model model, User user) {
        model.addAttribute("user", user);
        return "register";
    }

    @PostMapping("/register/save")
    public String saveUser(@Valid User user, BindingResult result, Model model) {

        User existing = userService.findByEmail(user.getEmail());
        if (existing != null) {
            result.rejectValue("email", null, "There is already an account registered with that email");
        }
        if (result.hasErrors()) {
            model.addAttribute("user", user);
            return "register";
        }
        userService.saveUser(user);
        return "redirect:/register?success";
    }


    // List Of Users
    @GetMapping("/userList")
    public String userList(Model model) {
        model.addAttribute("userList", userService.findAllUsers());
        return "userList";
    }

    // Delete User By Id
    @GetMapping("/deleteUser/{id}")
    public String deleteUser(@PathVariable("id") Integer id) {
        userService.deleteUser(id);
        return "redirect:/userList";
    }

    //  Edit User
    @GetMapping("/editUser/{id}")
    public String editUser(@PathVariable("id") Integer id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "updateForm";
    }
    // update User
    @PostMapping("/updateUser")
    public String updateUser(@Valid User user, BindingResult result, Model model) {
        model.addAttribute("user", user);
        userService.updateUser(user);
        return "redirect:/userList";
    }
}
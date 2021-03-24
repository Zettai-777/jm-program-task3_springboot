package ru.zettai.jmprogramtask3_springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.zettai.jmprogramtask3_springboot.entities.User;
import ru.zettai.jmprogramtask3_springboot.services.RoleService;
import ru.zettai.jmprogramtask3_springboot.services.UserService;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    private RoleService roleService;

    private PasswordEncoder encoder;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }

    @Autowired
    public void setEncoder(PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @GetMapping("/")
    public String showAllUsers(Model model) {
        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("allUsers", allUsers);
        return "all-users";
    }

    @GetMapping("/addUser")
    public String addNewUser(
            @ModelAttribute("user") User user,
            Model model) {
        model.addAttribute("user", user);
        model.addAttribute("roles", roleService.getAllRoles());
        return "edit-user";
    }


    @PostMapping("/saveUser")
    public String saveUser(
            @Valid @ModelAttribute("user") User user,
            BindingResult bindingResult,
            Model model,
            @RequestParam Map<String, String> allParams) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("user", user);
            return "edit-user";
        }else {
            userService.saveUser(user);
            return "redirect:/admin/";
        }
    }

    @GetMapping("/updateUserInfo/{userId}")
    public String getUserInfo(@PathVariable(name = "userId") long id, Model model) {
        User currentUser = userService.findUserById(id);
        model.addAttribute("user", currentUser);
        model.addAttribute("roles", roleService.getAllRoles());
        return "edit-user";
    }

    @PostMapping("/updateUserInfo/{userId}")
    public String updateUserInfo(
            @PathVariable(name = "userId") long id,
            @Valid @ModelAttribute("user") User user,
            BindingResult bindingResult,
            Model model){
        System.out.println("User roles before validation: " + user.getRoles());
        if(bindingResult.hasErrors()){
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            System.out.println("User roles after validation: " + user.getRoles());
            model.addAttribute("user", user);
            return "edit-user";
        }else {
            user.setPassword(encoder.encode(user.getPassword()));
            userService.updateUser(user);
            return "redirect:/admin/";
        }
    }

    @RequestMapping("/deleteUserById/{userId}")
    public String deleteUser(@PathVariable(name = "userId") long id) {
        userService.deleteUserById(id);
        return "redirect:/admin/";
    }

}

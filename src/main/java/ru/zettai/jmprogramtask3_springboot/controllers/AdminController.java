package ru.zettai.jmprogramtask3_springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.zettai.jmprogramtask3_springboot.entities.Role;
import ru.zettai.jmprogramtask3_springboot.entities.User;
import ru.zettai.jmprogramtask3_springboot.services.RoleService;
import ru.zettai.jmprogramtask3_springboot.services.UserService;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
    public String showAllUsers(Model model, Principal principal) {
        //добавляем всех пользователей
        List<User> allUsers = userService.getAllUsers();
        model.addAttribute("allUsers", allUsers);
        //добавляем все роли
        List<Role> allRoles = roleService.getAllRoles();
        model.addAttribute("roles", allRoles);
        //добавляем текущего пользователя
        User currentUser = userService.getUserByUsername(principal.getName());
        model.addAttribute("current_user", currentUser);
        //добавляем роли текущего пользователя
        model.addAttribute("userRoles", roleService.getAllRoles().stream().map(Role::getRole).collect(Collectors.toList()).toString());
        //добавляем нового пользователя
        model.addAttribute("new_user", new User());
        return "admin-panel";
    }

//    @GetMapping("/new")
//    public String addNewUser(
//            @ModelAttribute("user") User user,
//            Model model) {
//        model.addAttribute("user", user);
//        model.addAttribute("roles", roleService.getAllRoles());
//        return "admin-panel/new";
//    }


//    @PostMapping()
    @PostMapping("/saveUser")
    public String saveUser(
            @Valid @ModelAttribute("user") User user,
            BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("user", user);
//            return "edit-user";
            return "admin-panel/new";
        }else {
            userService.saveUser(user);
//            return "redirect:/admin/";
            return "admin-panel";
        }
    }

    @GetMapping("/updateUserInfo/{userId}")
    public String getUserInfo(@PathVariable(name = "userId") long id, Model model) {
        User currentUser = userService.findUserById(id);
        model.addAttribute("current_user", currentUser);
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin-panel";
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
//            return "edit-user";
            return "admin-panel";
        }else {
            user.setPassword(encoder.encode(user.getPassword()));
            userService.updateUser(user);
//            return "redirect:/admin/";
            return "admin-panel";
        }
    }

    @RequestMapping("/deleteUserById/{userId}")
    public String deleteUser(@PathVariable(name = "userId") long id) {
        userService.deleteUserById(id);
        return "admin-panel";
//        return "redirect:/admin/";
    }

}

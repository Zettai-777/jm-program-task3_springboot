package ru.zettai.jmprogramtask3_springboot.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.zettai.jmprogramtask3_springboot.entities.User;
import ru.zettai.jmprogramtask3_springboot.services.RoleService;
import ru.zettai.jmprogramtask3_springboot.services.UserService;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class RegistrationController {

    private UserService userService;

    private RoleService roleService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setRoleService(RoleService roleService) {
        this.roleService = roleService;
    }


    @GetMapping("/registration")
    public String registration(Model model){
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String addNewUser(
            @Valid @ModelAttribute("user") User  user,
            BindingResult bindingResult,
            Model model)
    {
        if(bindingResult.hasErrors()){
            Map<String, String> errorsMap = ControllerUtils.getErrors(bindingResult);
            model.mergeAttributes(errorsMap);
            model.addAttribute("user", user);
            return "registration";
        }
        if(!user.getPassword().equals(user.getPasswordConfirmation())){
            model.addAttribute("passwordError", "Пароли не совпадают");
            return "registration";
        }
        user.getRoles().add(roleService.getRoleByName("USER"));
        if(!userService.saveUser(user)){
            model.addAttribute("usernameError", "Пользователь с таким именем уже существует!");
            return "registration";
        }
        model.addAttribute("user", user);
        return "redirect:/login";
    }
}

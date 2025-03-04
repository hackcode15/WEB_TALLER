package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.Dto.UserRegisterDTO;
import com.app.model.RoleEnum;
import com.app.service.UserService;

@Controller
@RequestMapping("/auth")
public class AuthController {
    
    @Autowired
    private UserService service;

    @GetMapping("/view-login")
    public String verFormularioDeLogin(){
        return "form-login";
    }

    @GetMapping("/view-register")
    public String verFormularioDeRegistro(Model model){
        model.addAttribute("user", new UserRegisterDTO());
        model.addAttribute("roles", RoleEnum.values());
        return "form-register";
    }

    @PostMapping("/register")
    public String guardarNuevoUsuario(@ModelAttribute UserRegisterDTO userDTO){
        
        service.register(userDTO); 
            
        return "redirect:/auth/view-login"; 

    }

}

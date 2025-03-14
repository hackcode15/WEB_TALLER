package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.app.Dto.UserRegisterDTO;
import com.app.model.RoleEnum;
import com.app.service.UserService;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    
    @Autowired
    private UserService service;

    AuthController(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

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

    /* @PostMapping("/register")
    public String guardarNuevoUsuario(@ModelAttribute UserRegisterDTO userDTO){
        
        service.register(userDTO); 
            
        return "redirect:/auth/view-login"; 

    } */

    @PostMapping("/register")
    public String guardarNuevoUsuario(@ModelAttribute UserRegisterDTO userDTO, RedirectAttributes redirectAttributes){
        
        try {
            
            service.register(userDTO);

            redirectAttributes.addFlashAttribute("successMessage", "!Te has registrado correctamente¡");

            return "redirect:/auth/view-login";

        } catch (Exception e) {
            
            redirectAttributes.addFlashAttribute("errorMessage", "Error al registrar: " + e.getMessage());

            return "redirect:/auth/view-register";

        }

    }

}

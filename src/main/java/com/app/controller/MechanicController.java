package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.Dto.MechanicDTO;
import com.app.service.MechanicService;

@Controller
@RequestMapping("/mechanic")
public class MechanicController {
    
    @Autowired
    private MechanicService service;

    @GetMapping
    public String getAllMechanicDTOs(Model model){
        model.addAttribute("mechanics", service.findAllMechanicDTOs());
        return "/mechanic/mechanic-management";
    }

    @GetMapping("/view-form-mechanic")
    public String viewFormMechanic(Model model){
        model.addAttribute("mechanic", new MechanicDTO());
        return "/mechanic/form-mechanic";
    }

    @PostMapping("/save-mechanic")
    public String saveMechanic(@ModelAttribute MechanicDTO mechanicDTO){
        service.saveMechanic(mechanicDTO);
        return "redirect:/mechanic";
    }

    @GetMapping("/search")
    public String searchMechanic(@RequestParam(name = "id", required = false) Integer id, Model model){
    
        if (id != null) {
            
            MechanicDTO mechanicDTO = service.findMechanicDtoById(id);

            if(mechanicDTO != null){
            
                model.addAttribute("mechanics", mechanicDTO);

            } else {
                model.addAttribute("mechanics", service.findAllMechanicDTOs());
            }

        } else {
            model.addAttribute("mechanics", service.findAllMechanicDTOs());
        }

        return "/mechanic/mechanic-management";
    
    }

    @GetMapping("/edit/{id}")
    public String updateMechanic_view_form(@PathVariable("id") Integer id, Model model){
        
        MechanicDTO mechanicDTO = service.findMechanicDtoById(id);

        model.addAttribute("mechanic", mechanicDTO);

        return "/mechanic/form-mechanic";

    }

    @GetMapping("/delete/{id}")
    public String deleteMechanic(@PathVariable("id") Integer id){
        service.deleteMechanicById(id);
        return "redirect:/mechanic";
    }


}

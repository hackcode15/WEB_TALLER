package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.app.model.ServiceWorkshop;
import com.app.service.ServiceWorkshopService;

@Controller
@RequestMapping("/service-workshop")
public class ServiceWorkshopController {
    
    @Autowired
    private ServiceWorkshopService service;

    @GetMapping
    public String getAllServiceWorkshop(Model model){
        model.addAttribute("services", service.findAllServiceWorkshops());
        return "service_workshop/service-work-management";
    }

    @GetMapping("/view-form-service_workshop")
    public String viewFormServiceWorkshop(Model model){
        model.addAttribute("service", new ServiceWorkshop());
        return "service_workshop/form-service-work";
    }

    @PostMapping("/save-service_workshop")
    public String saveNewServiceWorkshop(@ModelAttribute ServiceWorkshop serviceWorkshop){
        service.saveServiceWorkshop(serviceWorkshop);
        return "redirect:/service-workshop";
    }

}

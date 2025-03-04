package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.Dto.CustomerDTO;
//import com.app.model.UserPrincipal;
import com.app.service.CustomerService;

@Controller
@RequestMapping("/customer")
public class CustomerController {
    
    @Autowired
    private CustomerService service;

    @GetMapping
    public String getAllCustomersDTOs(Model model){
        model.addAttribute("customers", service.findAllCustomerDtos());
        return "customer/customer-management";
    }

    @GetMapping("/search")
    public String searchCustomer(@RequestParam(name = "id", required = false) Integer id, Model model){
    
        if(id != null){
            
            CustomerDTO customerDTO = service.findCustomerDtoById(id);

            model.addAttribute("customers", customerDTO);

        } else{
            model.addAttribute("customers", service.findAllCustomerDtos());
        }

        return "customer/customer-management";

    }

    @GetMapping("/view-form-customer")
    public String viewFormCustomer(Model model){
        model.addAttribute("customer", new CustomerDTO());
        return "/customer/form-customer";
    }

    @PostMapping("/save-customer")
    public String saveCustomer(@ModelAttribute CustomerDTO customerDto){
        service.saveCustomerDto(customerDto);
        return "redirect:/customer";
    }

    @GetMapping("/edit/{id}")
    public String editCustomerViewForm(@PathVariable("id") Integer id, Model model){
        
        CustomerDTO customerDTO = service.findCustomerDtoById(id);

        model.addAttribute("customer", customerDTO);

        return "/customer/form-customer";

    }

    @GetMapping("/delete/{id}")
    public String deleteCustomer(@PathVariable("id") Integer id){
        service.deleteCustomerById(id);
        return "redirect:/customer";
    }

    /* @GetMapping("/mycars")
    public String viewMyCars(Model model, Authentication authentication){
        
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Integer userId = ((UserPrincipal) userDetails).getId();

        model.addAttribute("mycars", service.findAllCarDTOs(userId));

        return "/home";
    } */


}

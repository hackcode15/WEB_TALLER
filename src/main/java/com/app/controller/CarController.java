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

import com.app.Dto.CarRequestDTO;
import com.app.model.Car;
import com.app.service.CarService;
import com.app.service.CustomerService;

@Controller
@RequestMapping("/customer/car")
public class CarController {
    
    @Autowired 
    private CarService service;

    @Autowired
    private CustomerService customerService;

    @GetMapping
    public String getAllCars(Model model){
        model.addAttribute("cars", service.getAllCarRequestDTOs());
        return "customer/car/car-management";
    }

    @GetMapping("/view-form-car")
    public String viewFormCar(Model model) {
       
        model.addAttribute("customers", customerService.findAllCustomerDtos());
        model.addAttribute("car", new Car());

        return "customer/car/form-car";
    }

    @PostMapping("/save-car")
    public String saveNewCar(@ModelAttribute Car car){
        service.saveCar(car);
        return "redirect:/customer/car";
    }

    @GetMapping("/search")
    public String searchAutoByPatent(@RequestParam(name = "patent", required = false) String patent, Model model){
        
        if(patent != null){
            
            CarRequestDTO carDto = service.findCarRequestDTOByPatent(patent);

            model.addAttribute("cars", carDto);

        } else {
            model.addAttribute("cars", service.getAllCarRequestDTOs());
        }

        return "customer/car/car-management";

    }

    @GetMapping("/edit/{patent}")
    public String editCar(@PathVariable("patent") String patent, Model model){
    
        Car car = service.findCarByPatent(patent);

        model.addAttribute("car", car);
        model.addAttribute("customers", customerService.findAllCustomerDtos());

        return "customer/car/form-car";

    }

    @GetMapping("/delete/{patent}")
    public String deleteCar(@PathVariable("patent") String patent){
        
        CarRequestDTO carDto = service.findCarRequestDTOByPatent(patent);

        service.deleteCarByPatent(carDto.getPatent());

        return "redirect:/customer/car";

    }

}

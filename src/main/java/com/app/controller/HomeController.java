package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.app.Dto.CarDTO;
import com.app.Dto.EditProfileDTO;
import com.app.model.Car;
import com.app.model.Customer;
import com.app.model.Mechanic;
import com.app.model.User;
import com.app.model.UserPrincipal;
import com.app.service.CarService;
import com.app.service.CustomerService;
import com.app.service.MechanicService;
import com.app.service.UserService;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private MechanicService mechanicService;

    @Autowired
    private UserService userService;

    @Autowired
    private CarService carService;

    /*
     * private Integer getIdUserAuthenticado(Authentication authentication) {
     * 
     * if (authentication != null) {
     * 
     * UserDetails userDetails = (UserDetails) authentication.getPrincipal();
     * 
     * if (userDetails instanceof UserPrincipal) {
     * 
     * UserPrincipal userPrincipal = (UserPrincipal) userDetails;
     * 
     * if (userPrincipal.isCustomer()) {
     * return userPrincipal.getId();
     * }
     * 
     * }
     * 
     * }
     * 
     * return null;
     * 
     * }
     */

    private Integer getIdUserAuthenticado(Authentication authentication) {
        if (authentication != null && authentication.isAuthenticated()) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserPrincipal) {
                UserPrincipal userPrincipal = (UserPrincipal) principal;
                return userPrincipal.getId();
            }
        }
        return null;
    }

    @GetMapping
    public String home(Model model, Authentication authentication) {
        Integer idUserAuthenticado = getIdUserAuthenticado(authentication);
        if (idUserAuthenticado != null) {
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
            if(userPrincipal.isCustomer()){
                model.addAttribute("mycars", customerService.findAllMyCarDTOs(idUserAuthenticado));
            }
        }
        return "home";
    }

    @GetMapping("/search-mycar")
    public String findMyCar(@RequestParam(name = "patent", required = false) String patent, Model model, Authentication authentication) {

        Integer idUserAuthenticado = getIdUserAuthenticado(authentication);

        if (idUserAuthenticado != null && patent != null) {

            CarDTO carDTO = customerService.findMyCarDTObyPatent(idUserAuthenticado, patent);

            model.addAttribute("mycars", carDTO);

        } else {
            model.addAttribute("mycars", customerService.findAllMyCarDTOs(idUserAuthenticado));
        }

        return "home";

    }

    @GetMapping("/view-form-mycar")
    public String viewFormAddMyCar(Model model) {
        model.addAttribute("car", new CarDTO());
        return "form-customer-car";
    }

    @PostMapping("/save-mycar")
    public String saveMyCar(@ModelAttribute CarDTO carDTO, Authentication authentication) {

        Integer idUserAuthenticado = getIdUserAuthenticado(authentication);

        if (idUserAuthenticado != null) {
            customerService.saveMyNewCar(carDTO, idUserAuthenticado);
        }

        return "redirect:/home";

    }

    @GetMapping("/edit-mycar/{patent}")
    public String viewFormEditMyCar(@PathVariable("patent") String patent, Model model, Authentication authentication) {

        Integer idUserAuthenticado = getIdUserAuthenticado(authentication);

        boolean isMyCar = customerService.isYouCar(idUserAuthenticado, patent);

        if (isMyCar) {

            Car car = carService.findCarByPatent(patent);

            CarDTO carDTO = CarDTO.builder()
                    .patent(car.getPatent())
                    .brand(car.getBrand())
                    .model(car.getModel())
                    .year(car.getYear())
                    .color(car.getColor())
                    .build();

            model.addAttribute("car", carDTO);

        }

        return "form-customer-car";

    }

    @GetMapping("/delete-mycar/{patent}")
    public String deleteMyCar(@PathVariable("patent") String patent, Authentication authentication) {

        Integer idUserAuthenticado = getIdUserAuthenticado(authentication);

        if (idUserAuthenticado != null) {

            Car car = carService.findCarByPatent(patent);

            customerService.deleteMyCarByPatent(idUserAuthenticado, car.getPatent());

        }

        return "redirect:/home";

    }

    // ---
    @GetMapping("/profile")
    public String viewProfile() {
        return "profile";
    }

    @GetMapping("/view-general-form")
    public String viewGeneralForm(Model model, Authentication authentication){
        
        Integer idUserAuthenticado = getIdUserAuthenticado(authentication);

        if(idUserAuthenticado != null){
            
            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();

            String userType = userPrincipal.getTypeUser();

            if(userType.equals("CUSTOMER")){
                Customer customer = customerService.findCustomerById(idUserAuthenticado);
                model.addAttribute("user", customer);
            } else if(userType.equals("MECHANIC")){
                Mechanic mechanic = mechanicService.findMechanicById(idUserAuthenticado);
                model.addAttribute("user", mechanic);
            } else {
                User user = userService.findUserById(idUserAuthenticado);
                model.addAttribute("user", user);
            }

        }

        return "form-general-profile";

    }

    @PostMapping("/update-profile")
    public String updateProfile(@ModelAttribute EditProfileDTO editDTO, Authentication authentication){
        
        Integer idUserAuthenticado = getIdUserAuthenticado(authentication);

        userService.editProfileUser(editDTO, idUserAuthenticado);

        return "redirect:/home/profile";

    }

    /* @PostMapping("/update-profile")
    public String updateProfile(@ModelAttribute("typeUser") User user, Authentication authentication) {

        Integer idUserAuthenticado = getIdUserAuthenticado(authentication);

        if (idUserAuthenticado != null) {

            user = userService.findUserById(idUserAuthenticado);

            if (user instanceof Customer) {
                //customerService.updateCustomer((Customer) user);
                Customer customer = (Customer) user;
                CustomerDTO customerDTO = CustomerDTO.builder()
                    .id(customer.getId())
                    .username(customer.getUsername())
                    .password(customer.getPassword())
                    .firstName(customer.getFirstName())
                    .lastName(customer.getLastName())
                    .birthdate(customer.getBirthdate())
                    .phone(customer.getPhone())
                    .address(customer.getAddress())
                    .build();
                customerService.saveCustomerDto(customerDTO);
            } else if (user instanceof Mechanic) {
                mechanicService.updateMechanic((Mechanic) user);
            } else {
                userService.updateUser(user);
            }

        }

        return "redirect:/home/profile";

    } */

}

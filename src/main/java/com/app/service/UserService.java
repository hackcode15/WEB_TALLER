package com.app.service;

//import java.util.HashSet;
import java.util.List;
import java.util.Set;
//import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.Dto.EditProfileDTO;
import com.app.Dto.UserRegisterDTO;
import com.app.model.Customer;
import com.app.model.Mechanic;
import com.app.model.Role;
import com.app.model.RoleEnum;
import com.app.model.User;
import com.app.repo.CustomerRepo;
import com.app.repo.MechanicRepo;
import com.app.repo.RoleRepo;
import com.app.repo.UserRepo;
import com.app.service.Imp.IUserService;

@Service
public class UserService implements IUserService {
    
    @Autowired
    private UserRepo repo;

    @Autowired
    private CustomerRepo customerRepo;

    @Autowired
    private MechanicRepo mechanicRepo;

    @Autowired
    private RoleRepo roleRepo;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);
    
    @Override
    public User register(UserRegisterDTO userDTO){

        User user = new User();

        if(userDTO.getTipo().equalsIgnoreCase("Customer")){
            
            Customer customer = Customer.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .birthdate(userDTO.getBirthdate())
                .phone(userDTO.getPhone())
                .address(userDTO.getAddress())
                .build();

            user = customer;

        } else if(userDTO.getTipo().equalsIgnoreCase("Mechanic")){
            
            Mechanic mechanic = Mechanic.builder()
                .username(userDTO.getUsername())
                .password(passwordEncoder.encode(userDTO.getPassword()))
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .birthdate(userDTO.getBirthdate())
                .salary(userDTO.getSalary())
                .specialization(userDTO.getSpecialization())
                .dateEntry(userDTO.getDateEntry())
                .build();

            user = mechanic;

        }
        
        Role rolUser = roleRepo.findByRoleEnum(RoleEnum.USER);
        if(rolUser == null){
            throw new RuntimeException("Rol USER no encontrado");
        }

        user.setRoles(Set.of(rolUser));

        return repo.save(user);

    }

    @Override
    public void editProfileUser(EditProfileDTO editDTO, Integer idUserAuthenticado) {
        
        User user = this.findUserById(idUserAuthenticado);

        if (user.getId() != null) {
            
            if (user instanceof  Customer) {
                
                Customer customer = customerRepo.findById(user.getId())
                    .orElseThrow(() -> new RuntimeException("Error cliente no encontrado con ID: " + user.getId()));

                customer.setUsername(editDTO.getUsername());
                customer.setPassword(passwordEncoder.encode(editDTO.getPassword()));
                customer.setFirstName(editDTO.getFirstName());
                customer.setLastName(editDTO.getLastName());
                customer.setBirthdate(editDTO.getBirthdate());
                customer.setPhone(editDTO.getPhone());
                customer.setAddress(editDTO.getAddress());

                customerRepo.save(customer);

            } else if (user instanceof Mechanic) {
                
                Mechanic mechanic = mechanicRepo.findById(user.getId())
                    .orElseThrow(() -> new RuntimeException("Error mecanico no encontrado con ID: " + user.getId()));

                mechanic.setUsername(editDTO.getUsername());
                mechanic.setPassword(passwordEncoder.encode(editDTO.getPassword()));
                mechanic.setFirstName(editDTO.getFirstName());
                mechanic.setLastName(editDTO.getLastName());
                mechanic.setBirthdate(editDTO.getBirthdate());
                mechanic.setSalary(editDTO.getSalary());
                mechanic.setSpecialization(editDTO.getSpecialization());
                mechanic.setDateEntry(editDTO.getDateEntry());

                mechanicRepo.save(mechanic);
                
            } else {
                
                user.setUsername(editDTO.getUsername());
                user.setPassword(passwordEncoder.encode(editDTO.getPassword()));
                user.setFirstName(editDTO.getFirstName());
                user.setLastName(editDTO.getLastName());
                user.setBirthdate(editDTO.getBirthdate());

                repo.save(user);

            }

        }

    }

    @Override
    public List<User> findAllUsers() {
        return repo.findAll();    
    }

    @Override
    public User findUserById(Integer id) {
        return repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Usuario no existe con ID: " + id));    
    }

    @Override
    public void deleteUserById(Integer id) {
        User user = this.findUserById(id);
        repo.deleteById(user.getId());    
    }

    @Override
    public void updateUser(User user) {
       repo.save(user);
    }

    

}

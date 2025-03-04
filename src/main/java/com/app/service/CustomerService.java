package com.app.service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.Dto.CarDTO;
import com.app.Dto.CustomerDTO;
import com.app.model.Car;
import com.app.model.Customer;
import com.app.model.Role;
import com.app.model.RoleEnum;
import com.app.repo.CarRepo;
import com.app.repo.CustomerRepo;
import com.app.repo.RoleRepo;
import com.app.service.Imp.ICustomerService;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    private CustomerRepo repo;

    @Autowired
    private CarRepo carRepo;

    @Autowired
    private RoleRepo roleRepo;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @Override
    public Customer findCustomerById(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Error cliente no encontrado con ID: " + id));    
    }

    @Override
    public List<CustomerDTO> findAllCustomerDtos() {

        List<CustomerDTO> customerDTOs = repo.findAll().stream()
                .map(customer -> {
                    CustomerDTO dto = CustomerDTO.builder()
                            .id(customer.getId())
                            .firstName(customer.getFirstName())
                            .lastName(customer.getLastName())
                            .birthdate(customer.getBirthdate())
                            .age(customer.calcularEdad())
                            .phone(customer.getPhone())
                            .address(customer.getAddress())
                            .build();
                    return dto;
                })
                .collect(Collectors.toList());

        return customerDTOs;

    }
    
    @Override
    public Customer saveCustomerDto(CustomerDTO customerDto) {
        Customer customer;
        if (customerDto.getId() != null) {
            customer = repo.findById(customerDto.getId())
                    .orElseThrow(() -> new RuntimeException("Cliente con ID: " + customerDto.getId() + " no existe"));
            customer.setUsername(customerDto.getUsername());
            customer.setPassword(passwordEncoder.encode(customerDto.getPassword())); // Codifica el password
            customer.setFirstName(customerDto.getFirstName());
            customer.setLastName(customerDto.getLastName());
            customer.setBirthdate(customerDto.getBirthdate());
            customer.setPhone(customerDto.getPhone());
            customer.setAddress(customerDto.getAddress());
        } else {
            Role roleUser = roleRepo.findByRoleEnum(RoleEnum.USER);
            if (roleUser == null) {
                throw new RuntimeException("Rol USER no encontrado");
            }
            customer = Customer.builder()
                    .username(customerDto.getUsername())
                    .password(passwordEncoder.encode(customerDto.getPassword()))
                    .firstName(customerDto.getFirstName())
                    .lastName(customerDto.getLastName())
                    .birthdate(customerDto.getBirthdate())
                    .phone(customerDto.getPhone())
                    .address(customerDto.getAddress())
                    .roles(Set.of(roleUser))
                    .build();
        }
        return repo.save(customer);
    }

    @Override
    public CustomerDTO findCustomerDtoById(Integer id) {

        /* Customer customer = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente con ID: " + id + " no existe")); */
        
        Customer customer = this.findCustomerById(id);

        CustomerDTO customerDTO = CustomerDTO.builder()
                .id(customer.getId())
                .username(customer.getUsername())
                .password(customer.getPassword())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .birthdate(customer.getBirthdate())
                .age(customer.calcularEdad())
                .phone(customer.getPhone())
                .address(customer.getAddress())
                .build();

        return customerDTO;

    }

    @Override
    public void deleteCustomerById(Integer id) {

        /* Customer customer = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente con ID: " + id + " no existe")); */
        
        Customer customer = this.findCustomerById(id);

        repo.deleteById(customer.getId());

    }

    // interaccion de los autos propios de cada cliente
    // para usuarios de tipo cliente

    @Override
    public boolean isYouCar(Integer idCustomer, String patent) {
        
        Integer value = repo.isThisYouCar(idCustomer, patent);

        return value == 1;

    }

    @Override
    public List<CarDTO> findAllMyCarDTOs(Integer idCustomer) {

        /* Customer customer = repo.findById(idCustomer)
                .orElseThrow(() -> new RuntimeException("Error cliente no encontrado con ID: " + idCustomer)); */

        Customer customer = this.findCustomerById(idCustomer);

        return repo.findAllCarsByCustomerId(customer.getId());   
    }

    @Override
    public CarDTO findMyCarDTObyPatent(Integer idCustomer, String patent) {
        
        Customer customer = this.findCustomerById(idCustomer);

        Car car = carRepo.findCarByPatent(patent)
                .orElseThrow(() -> new RuntimeException("Error vehiculo no encontrado con patente: " + patent));

        return repo.findCarOfCustomerByPatent(customer.getId(), car.getPatent())
                .orElseThrow(() -> new RuntimeException("Error no podimos mostrar el auto del cliente solicitado"));

    }

    @Override
    public void saveMyNewCar(CarDTO carDTO, Integer idCustomer) {
        
        Customer customer = this.findCustomerById(idCustomer);

        Car car = Car.builder()
                .patent(carDTO.getPatent())
                .brand(carDTO.getBrand())
                .model(carDTO.getModel())
                .year(carDTO.getYear())
                .color(carDTO.getColor())
                .customer(customer)
                .build();

        carRepo.save(car);

    }

    @Override
    public void deleteMyCarByPatent(Integer idCustomer, String patent) {
        
        boolean isMyCar = this.isYouCar(idCustomer, patent);

        Car car = carRepo.findCarByPatent(patent)
                .orElseThrow(() -> new RuntimeException("Error auto no encontrado con patente: " + patent));

        if(isMyCar){
            carRepo.delete(car);
        }

    }

    @Override
    public void updateCustomer(Customer customer) {
        repo.save(customer);
    }

}

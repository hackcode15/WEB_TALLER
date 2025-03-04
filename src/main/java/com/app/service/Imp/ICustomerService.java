package com.app.service.Imp;

import java.util.List;

import com.app.Dto.CarDTO;
import com.app.Dto.CustomerDTO;
import com.app.model.Customer;

public interface ICustomerService {

    // ---
    public void updateCustomer(Customer customer);
    
    // interaccion sobre todos los vehiculos de todos los clientes
    // para desarrolladores y administradores
    public List<CustomerDTO> findAllCustomerDtos();
    public Customer saveCustomerDto(CustomerDTO customerDTO);
    public CustomerDTO findCustomerDtoById(Integer id);
    public Customer findCustomerById(Integer id);
    public void deleteCustomerById(Integer id);

    // interaccion de los vehiculos propios de cada cliente
    // para usuarios de tipo cliente
    public List<CarDTO> findAllMyCarDTOs(Integer idCustomer);
    public CarDTO findMyCarDTObyPatent(Integer idCustomer, String patent);
    public void saveMyNewCar(CarDTO carDTO, Integer idCustomer);
    public void deleteMyCarByPatent(Integer idCustomer, String patent);
    public boolean isYouCar(Integer idCustomer, String patent);

}

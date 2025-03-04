package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.Dto.CarRequestDTO;
import com.app.model.Car;
import com.app.repo.CarRepo;
import com.app.repo.CustomerRepo;
import com.app.service.Imp.ICarService;

@Service
public class CarService implements ICarService {
    
    @Autowired
    private CarRepo repo;

    @Autowired CustomerRepo customerRepo;

    @Override
    public void saveCar(Car car) {

        repo.save(car);

    }

    @Override
    public void deleteCarByPatent(String patent) {
           
        Car car = repo.findCarByPatent(patent)
                .orElseThrow(() -> new RuntimeException("Error auto no encontrado con patente: " + patent));

        repo.delete(car);

    }

    @Override
    public List<CarRequestDTO> getAllCarRequestDTOs() {
        return repo.findAllCarRequestDTOs();    
    }

    @Override
    public CarRequestDTO findCarRequestDTOByPatent(String patent) {
        
        Car car = repo.findCarByPatent(patent)
                .orElseThrow(() -> new RuntimeException("Error auto no encontrado con patente: " + patent));

        CarRequestDTO carDto = CarRequestDTO.builder()
                .firstName(car.getCustomer().getFirstName())
                .patent(car.getPatent())
                .brand(car.getBrand())
                .model(car.getModel())
                .year(car.getYear())
                .color(car.getColor())
                .build();

        return carDto;

    }

    @Override
    public Car findCarByPatent(String patent) {
        return repo.findCarByPatent(patent)
                .orElseThrow(() -> new RuntimeException("Auto no encontrado con patente: " + patent));    
    }

}

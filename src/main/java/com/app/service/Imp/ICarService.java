package com.app.service.Imp;

import java.util.List;

import com.app.Dto.CarRequestDTO;
import com.app.model.Car;

public interface ICarService {
    
    public List<CarRequestDTO> getAllCarRequestDTOs();
    public CarRequestDTO findCarRequestDTOByPatent(String patent);
    public Car findCarByPatent(String patent);
    public void saveCar(Car car);
    public void deleteCarByPatent(String patent);

}

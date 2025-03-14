package com.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.model.ServiceWorkshop;
import com.app.repo.ServiceWorkshopRepo;
import com.app.service.Imp.IServiceWorkshop;

@Service
public class ServiceWorkshopService implements IServiceWorkshop {
    
    @Autowired
    private ServiceWorkshopRepo repo;

    @Override
    public List<ServiceWorkshop> findAllServiceWorkshops() {
        return repo.findAll();    
    }

    @Override
    public void saveServiceWorkshop(ServiceWorkshop serviceWorkshop) {
        repo.save(serviceWorkshop);
    }

    @Override
    public ServiceWorkshop findServiceWorkshopById(Integer id) {
        return repo.findById(id)
            .orElseThrow(() -> new RuntimeException("Error servicio de taller no encontrado con id: " + id));
    }

    @Override
    public void deleteServiceWorkshopById(Integer id) {
        ServiceWorkshop serviceWorkshop = this.findServiceWorkshopById(id);
        repo.delete(serviceWorkshop);    
    }

}

package com.app.service.Imp;

import java.util.List;

import com.app.model.ServiceWorkshop;

public interface IServiceWorkshop {
    
    public List<ServiceWorkshop> findAllServiceWorkshops();
    public void saveServiceWorkshop(ServiceWorkshop serviceWorkshop);
    public ServiceWorkshop findServiceWorkshopById(Integer id);
    public void deleteServiceWorkshopById(Integer id);

    

}

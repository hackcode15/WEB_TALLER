package com.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.ServiceWorkshop;

@Repository
public interface ServiceWorkshopRepo extends JpaRepository<ServiceWorkshop, Integer> {
    
    

}

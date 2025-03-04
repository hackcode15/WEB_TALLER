package com.app.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.model.Mechanic;

@Repository
public interface MechanicRepo extends JpaRepository<Mechanic, Integer> {
    
}

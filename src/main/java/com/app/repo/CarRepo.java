package com.app.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.Dto.CarRequestDTO;
import com.app.model.Car;


@Repository
public interface CarRepo extends JpaRepository<Car, String> {
    
    Optional<Car> findCarByPatent(String patent);

    @Query(
        value = """
                SELECT 
                    u.first_name,
                    c.patent,
                    c.brand,
                    c.model,
                    c.year,
                    c.color 
                FROM tbl_car c 
                JOIN tbl_customer cu ON c.customer_id = cu.id
                JOIN tbl_users u ON cu.id = u.id
                """,
        nativeQuery =  true
    )
    List<CarRequestDTO> findAllCarRequestDTOs();

}

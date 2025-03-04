package com.app.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.Dto.CarDTO;
import com.app.model.Customer;

@Repository
public interface CustomerRepo extends JpaRepository<Customer, Integer> {

    // consultas personalizadas para la manipulacion de sus propios vehiculos
    
    // con codigo JPQL
    /* @Query("SELECT new com.app.Dto.CarDTO(c.patent, c.brand, c.model, c.year, c.color) FROM Car c WHERE c.customer.id = :idCustomer")
    List<CarDTO> findAllCarsByCustomerId(@Param("idCustomer") Integer idCustomer); */

    // con sql codigo SQL
    @Query(
        value = """
                SELECT
                    patent,
                    brand,
                    model,
                    year,
                    color
                FROM tbl_car ca 
                WHERE ca.customer_id = :idCustomer
                """,
        nativeQuery = true
    )
    List<CarDTO> findAllCarsByCustomerId(@Param("idCustomer") Integer idCustomer);

    @Query(
        value = """
                SELECT
                    patent,
                    brand,
                    model,
                    year,
                    color
                FROM tbl_car ca
                WHERE ca.customer_id = :idCustomer AND ca.patent = :patent
                """,
        nativeQuery = true
    )
    Optional<CarDTO> findCarOfCustomerByPatent(@Param("idCustomer") Integer idCustomer, @Param("patent") String patent);

    @Query(
        value = """
                SELECT EXISTS (
                    SELECT 1 
                    FROM tbl_car ca 
                    WHERE ca.patent = :patent AND ca.customer_id = :idCustomer
                )
                """,
        nativeQuery = true
    )
    Integer isThisYouCar(@Param("idCustomer") Integer idCustomer, @Param("patent") String patent); // retorna -> 1 si es true, 0 si es false

}

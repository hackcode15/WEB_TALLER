package com.app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_car")
public class Car {

    @Id
    @Column(unique = true, nullable = false, length = 20)
    private String patent;

    @Column(nullable = false, length = 45)
    private String brand;

    @Column(nullable = false, length = 45)
    private String model;

    @Column(nullable = false)
    private Integer year;
    
    private String color;

    @ToString.Exclude
    @ManyToOne(targetEntity = Customer.class)
    private Customer customer;
    

}

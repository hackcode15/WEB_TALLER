package com.app.model;


import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter @Setter @ToString(callSuper = true)
@SuperBuilder
@Entity
@Table(name = "tbl_customer")
public class Customer extends User {
    
    @Column(nullable = false, length = 45)
    private String phone;

    @Column(nullable = false, length = 45)
    private String address;

    @ToString.Exclude
    @OneToMany(targetEntity = Car.class, mappedBy = "customer", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Car> cars;

}

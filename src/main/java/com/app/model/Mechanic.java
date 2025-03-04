package com.app.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "tbl_mechanic")
public class Mechanic extends User {
    
    @Column(nullable = false)
    private Double salary;

    @Column(nullable = false, length = 45)
    private String specialization;

    @Column(name = "date_entry", columnDefinition = "DATE")
    private LocalDate dateEntry;

}

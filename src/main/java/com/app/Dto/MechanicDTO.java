package com.app.Dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MechanicDTO {
    
    private Integer id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private Integer age;

    private Double salary;
    private String specialization;
    private LocalDate dateEntry;

}

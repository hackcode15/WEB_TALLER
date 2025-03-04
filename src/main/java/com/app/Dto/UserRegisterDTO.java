package com.app.Dto;

import java.time.LocalDate;
import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRegisterDTO {
    
    private Integer id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private Set<String> roles;
    private String tipo;

    private String phone;
    private String address;

    private Double salary;
    private String specialization;
    private LocalDate dateEntry;

}

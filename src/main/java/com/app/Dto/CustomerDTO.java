package com.app.Dto;

import java.time.LocalDate;
import java.util.Set;

import com.app.model.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerDTO {

    private Integer id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;
    private Set<Role> roles;
    
    private Integer age;
    
    private String phone;
    private String address;

}


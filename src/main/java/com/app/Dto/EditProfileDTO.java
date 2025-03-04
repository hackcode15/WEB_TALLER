package com.app.Dto;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EditProfileDTO {

    private Integer id;
    private String username;
    private String password;
    private String firstName;
    private String lastName;
    private LocalDate birthdate;

    private String phone;
    private String address;

    private Double salary;
    private String specialization;
    private LocalDate dateEntry;

}

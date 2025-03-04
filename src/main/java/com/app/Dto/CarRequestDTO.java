package com.app.Dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CarRequestDTO {
    
    private String firstName;
    private String patent;
    private String brand;
    private String model;
    private Integer year;
    private String color;

}

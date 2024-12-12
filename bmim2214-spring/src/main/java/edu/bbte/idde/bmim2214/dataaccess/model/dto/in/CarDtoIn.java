package edu.bbte.idde.bmim2214.dataaccess.model.dto.in;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.sql.Date;

@Data
public class CarDtoIn {
    private long id;
    private Date uploadDate;
    @NotBlank
    private String name;
    @NotBlank
    private String brand;
    @NotNull
    @Min(value = 1900, message = "The year should be between 1900 and the current year!")
    private int year;
    @NotNull
    @Min(value = 0, message = "The price should be >= 0")
    private double price;
}

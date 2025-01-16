package edu.bbte.idde.bmim2214.controller.dto.indto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CarExtraDtoIn {
    @NotBlank
    private String description;
}

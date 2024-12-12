package edu.bbte.idde.bmim2214.dataaccess.model.dto.outDto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CarShortDtoOut implements Serializable {
    private String name;
    private String brand;
}

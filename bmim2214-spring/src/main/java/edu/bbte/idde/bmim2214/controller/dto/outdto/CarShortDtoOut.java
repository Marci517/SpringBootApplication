package edu.bbte.idde.bmim2214.controller.dto.outdto;

import lombok.Data;

import java.io.Serializable;

@Data
public class CarShortDtoOut implements Serializable {
    private long id;
    private String name;
    private String brand;
}

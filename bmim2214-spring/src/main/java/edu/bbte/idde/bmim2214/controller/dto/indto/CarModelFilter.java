package edu.bbte.idde.bmim2214.controller.dto.indto;

import lombok.Data;

@Data
public class CarModelFilter {
    private String brand;
    private Integer minYear;
    private Integer maxYear;
    private Double minPrice;
    private Double maxPrice;
}

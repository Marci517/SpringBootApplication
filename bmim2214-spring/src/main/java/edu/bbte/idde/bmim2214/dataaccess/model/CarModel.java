package edu.bbte.idde.bmim2214.dataaccess.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "CarModelJpa")
public class CarModel extends BaseEntity {
    @Column(length = 255)
    @NotNull
    private String name;
    @Column(length = 255)
    @NotNull
    private String brand;
    @NotNull
    private int year;
    @NotNull
    private double price;
    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date uploadDate;

    @OneToMany(mappedBy = "car", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<CarExtra> extras = new ArrayList<>();

    @Override
    public String toString() {
        return "CarModel{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", brand='" + brand + '\''
                + ", year=" + year
                + ", price=" + price
                + ", uploadDate=" + uploadDate
                + '}';
    }
}

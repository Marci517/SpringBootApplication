package edu.bbte.idde.bmim2214.dataaccess.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "CarExtra")
public class CarExtra extends BaseEntity {
    @NotNull
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "car_model_jpa_id", nullable = false)
    @JsonBackReference
    private CarModel car;

    @Override
    public String toString() {
        return "Extras{"
                + "id=" + id
                + ", description='" + description + '\''
                + '}';
    }
}

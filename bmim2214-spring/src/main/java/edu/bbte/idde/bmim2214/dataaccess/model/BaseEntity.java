package edu.bbte.idde.bmim2214.dataaccess.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@MappedSuperclass
public abstract class BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L; //verzio kovetes, serializable mechanism

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long id;

    public BaseEntity() {
    }

    public BaseEntity(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        BaseEntity newBaseEntity = (BaseEntity) object;
        return Objects.equals(id, newBaseEntity.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


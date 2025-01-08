package edu.bbte.idde.bmim2214.dataaccess.model;

public class CarExtra extends BaseEntity {
    private String description;

    public CarExtra() {
        super();
    }

    public CarExtra(long id, String description) {
        super(id);
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "CarExtra{"
                + "id=" + id
                + ", description='" + description + '\''
                + '}';
    }
}

package edu.bbte.idde.bmim2214.dataaccess.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;
import java.util.List;

public class CarModel extends BaseEntity {
    private String name;
    private String brand;
    private int year;
    private double price;
    private List<CarExtra> carExtras;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date uploadDate;

    public CarModel() {
        super();
    }

    public CarModel(long id, String name, String brand, int year, Date uploadDate, List<CarExtra> carExtras) {
        super(id);
        this.name = name;
        this.brand = brand;
        this.year = year;
        this.uploadDate = uploadDate;
        this.carExtras = carExtras;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public List<CarExtra> getCarExtras() {
        return carExtras;
    }

    public void setCarExtras(List<CarExtra> carExtras) {
        this.carExtras = carExtras;
    }

    @Override
    public String toString() {
        return "CarModel{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", brand='" + brand + '\''
                + ", year=" + year
                + ", price=" + price
                + ", uploadDate=" + uploadDate
                + ", extras=" + carExtras
                + '}';
    }
}

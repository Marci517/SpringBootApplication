package edu.bbte.idde.bmim2214.dataaccess.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Date;

public class CarModel extends BaseEntity {
    private String name;
    private String brand;
    private int year;
    private double price;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date uploadDate;

    public CarModel() {
        super();
    }

    public CarModel(long id, String name, String brand, int year, Date uploadDate) {
        super(id);
        this.name = name;
        this.brand = brand;
        this.year = year;
        this.uploadDate = uploadDate;
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

package edu.bbte.idde.bmim2214.presentation;

import edu.bbte.idde.bmim2214.business.AllService;
import edu.bbte.idde.bmim2214.business.exceptions.CarExceptionDates;
import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionDatabase;
import edu.bbte.idde.bmim2214.dataaccess.model.CarExtra;
import edu.bbte.idde.bmim2214.dataaccess.model.CarModel;

import javax.swing.*;
import java.sql.Date;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;

public class CarActions {

    private final AllService carService;

    public CarActions(AllService carService) {
        this.carService = carService;
    }

    void addCar() throws ParseException, CarExceptionDates, CarExceptionDatabase {
        JTextField brandField = new JTextField(12);
        JTextField modelField = new JTextField(12);
        JTextField yearField = new JTextField(12);
        JTextField priceField = new JTextField(12);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Brand:"));
        panel.add(brandField);
        panel.add(new JLabel("Model:"));
        panel.add(modelField);
        panel.add(new JLabel("Year:"));
        panel.add(yearField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);

        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();
        Date today = new Date(year - 1900, localDate.getMonthValue() - 1, localDate.getDayOfMonth());
        int result = JOptionPane.showConfirmDialog(null, panel, "Add Car", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            CarModel car = new CarModel();
            car.setBrand(brandField.getText());
            car.setName(modelField.getText());
            car.setYear(Integer.parseInt(yearField.getText()));
            car.setPrice(Double.parseDouble(priceField.getText()));
            car.setUploadDate(today);
            car.setCarExtras(new ArrayList<>());

            carService.addCar(car);
            //System.out.println(car.getId());
            JOptionPane.showMessageDialog(null, "Car added successfully!");
        }
    }

    void addExtra() throws CarExceptionDatabase, NumberFormatException {
        JTextField carId = new JTextField(12);
        JTextField description = new JTextField(12);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Car ID:"));
        panel.add(carId);
        panel.add(new JLabel("Extra:"));
        panel.add(description);

        int result = JOptionPane.showConfirmDialog(null, panel, "Add an extra for a car", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            CarExtra carExtra = new CarExtra();
            carExtra.setDescription(description.getText());
            carService.addCarExtra(Long.parseLong(carId.getText()), carExtra);
            JOptionPane.showMessageDialog(null, "Extra added successfully!");
        }
    }

    void updateCar() throws CarExceptionDatabase, ParseException, CarExceptionDates {
        JTextField idField = new JTextField(12);
        JTextField brandField = new JTextField(12);
        JTextField modelField = new JTextField(12);
        JTextField yearField = new JTextField(12);
        JTextField priceField = new JTextField(12);

        JPanel panel = new JPanel();
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        panel.add(new JLabel("Brand:"));
        panel.add(brandField);
        panel.add(new JLabel("Model:"));
        panel.add(modelField);
        panel.add(new JLabel("Year:"));
        panel.add(yearField);
        panel.add(new JLabel("Price:"));
        panel.add(priceField);

        LocalDate localDate = LocalDate.now();
        Date today = new Date(localDate.getYear() - 1900, localDate.getMonthValue() - 1, localDate.getDayOfMonth());
        int result = JOptionPane.showConfirmDialog(null, panel, "Update Car", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            CarModel car = new CarModel();
            car.setId(Integer.parseInt(idField.getText()));
            car.setBrand(brandField.getText());
            car.setName(modelField.getText());
            car.setYear(Integer.parseInt(yearField.getText()));
            car.setPrice(Double.parseDouble(priceField.getText()));
            car.setUploadDate(today);

            carService.updateCar(car);
            JOptionPane.showMessageDialog(null, "Car updated successfully!");
        }

    }

    void deleteCar() throws CarExceptionDatabase {
        JTextField idField = new JTextField(12);

        JPanel panel = new JPanel();
        panel.add(new JLabel("ID:"));
        panel.add(idField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Delete Car", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {

            carService.deleteCar(Integer.parseInt(idField.getText()));
            JOptionPane.showMessageDialog(null, "Car deleted successfully!");
        }
    }

    void deleteExtra() throws CarExceptionDatabase, NumberFormatException {
        JTextField carIdField = new JTextField(12);
        JTextField extraIdField = new JTextField(12);

        JPanel panel = new JPanel();
        panel.add(new JLabel("CarID: "));
        panel.add(carIdField);
        panel.add(new JLabel("ExtraID: "));
        panel.add(extraIdField);

        int result = JOptionPane.showConfirmDialog(null,
                panel, "Delete Extra from a car", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {

            carService.deleteCarExtra(Long.parseLong(carIdField.getText()), Long.parseLong(extraIdField.getText()));
            JOptionPane.showMessageDialog(null, "Extra deleted successfully!");
        }
    }

    String getCar() throws CarExceptionDatabase, NumberFormatException {
        JTextField idField = new JTextField(12);

        JPanel panel = new JPanel();
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        int result = JOptionPane.showConfirmDialog(null, panel, "Search Car", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {

            String carStr = carService.getCar(Integer.parseInt(idField.getText())).toString();
            JOptionPane.showMessageDialog(null, carStr, "Result", JOptionPane.INFORMATION_MESSAGE);
            return carStr;
        }
        return "cancel";


    }

    void listCars() throws CarExceptionDatabase {
        StringBuilder carList = new StringBuilder();
        for (CarModel car : carService.getAllCars()) {
            carList.append(car.toString()).append('\n');
        }
        JOptionPane.showMessageDialog(null, carList.toString(), "Car List", JOptionPane.INFORMATION_MESSAGE);
    }

    void listExtras() throws CarExceptionDatabase {
        JTextField carIdField = new JTextField(12);

        JPanel panel = new JPanel();
        panel.add(new JLabel("Car ID:"));
        panel.add(carIdField);

        int result = JOptionPane.showConfirmDialog(null, panel, "List Extras for Car", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {

            int carId = Integer.parseInt(carIdField.getText());
            StringBuilder extrasList = new StringBuilder();
            carService.getAllExtras(carId).forEach(extra -> extrasList.append(extra.toString()).append('\n'));

            if (extrasList.isEmpty()) {
                JOptionPane.showMessageDialog(null,
                        "No extras found for the specified car.", "Extras List", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null,
                        extrasList.toString(), "Extras List", JOptionPane.INFORMATION_MESSAGE);
            }

        }
    }


}

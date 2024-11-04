package edu.bbte.idde.bmim2214.presentation;

import edu.bbte.idde.bmim2214.business.*;
import edu.bbte.idde.bmim2214.business.exceptions.CarExceptionDates;
import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionNoId;
import edu.bbte.idde.bmim2214.dataaccess.model.CarModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.sql.Date;
import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CarFrontend {
    private final CarService carService;
    private static final Logger log = LoggerFactory.getLogger(CarFrontend.class);


    public CarFrontend(CarService carService) {
        this.carService = carService;
    }

    public void display() {
        log.info("display");
        JFrame frame = new JFrame("Car Reselling");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        addButtonsToFrame(frame);
        frame.setLayout(new FlowLayout());
        frame.setVisible(true);
    }

    private void addButtonsToFrame(JFrame frame) {
        JButton addCarButton = createButton("Add Car", this::addCarAction);
        JButton updateCarButton = createButton("Update Car", this::updateCarAction);
        JButton deleteCarButton = createButton("Delete Car", this::deleteCarAction);
        JButton getCarButton = createButton("Search Car", e -> {
            String carDetails = getCarAction(e);
            log.info(carDetails);
        });
        JButton listCarsButton = createButton("List Cars", this::listCarsAction);

        frame.add(addCarButton);
        frame.add(updateCarButton);
        frame.add(deleteCarButton);
        frame.add(getCarButton);
        frame.add(listCarsButton);
    }

    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        return button;
    }

    private void addCarAction(ActionEvent e) {
        log.info("add car event triggered by: " + e.getActionCommand());
        try {
            addCar();
        } catch (IllegalArgumentException | CarExceptionDates | CarExceptionNoId | ParseException ex) {
            handleAddExceptions(ex);
        }
    }

    private void updateCarAction(ActionEvent e) {
        log.info("update car event triggered by: " + e.getActionCommand());
        try {
            updateCar();
        } catch (IllegalArgumentException | ParseException | CarExceptionDates | CarExceptionNoId ex) {
            handleUpdateExceptions(ex);
        }
    }

    private void deleteCarAction(ActionEvent e) {
        log.info("delete car event triggered by: " + e.getActionCommand());
        try {
            deleteCar();
        } catch (IllegalArgumentException | CarExceptionNoId ex) {
            JOptionPane.showMessageDialog(null, "Id does not exist");
        }
    }

    private String getCarAction(ActionEvent e) {
        log.info("get car event triggered by: " + e.getActionCommand());
        try {
            return getCar();
        } catch (IllegalArgumentException | CarExceptionNoId ex) {
            JOptionPane.showMessageDialog(null, "Id does not exist");
            return "Id does not exist";
        }
    }

    private void listCarsAction(ActionEvent e) {
        log.info("get all cars event triggered by: " + e.getActionCommand());
        try {
            listCars();
        } catch (CarExceptionNoId ex) {
            JOptionPane.showMessageDialog(null, "Failed to get all cars.");
        }
    }

    private void handleAddExceptions(Exception ex) {
        if (ex instanceof IllegalArgumentException) {
            JOptionPane.showMessageDialog(null, "Wrong parameters");
        } else if (ex instanceof ParseException) {
            JOptionPane.showMessageDialog(null, "Wrong date format. Usage: yyyy-mm-dd");
        } else if (ex instanceof CarExceptionDates) {
            JOptionPane.showMessageDialog(null, "The year should be between 1900 and the current year.");
        } else if (ex instanceof CarExceptionNoId) {
            JOptionPane.showMessageDialog(null, "Failed to insert the car.");
        }
    }

    private void handleUpdateExceptions(Exception ex) {
        if (ex instanceof IllegalArgumentException) {
            JOptionPane.showMessageDialog(null, "Wrong parameters");
        } else if (ex instanceof ParseException) {
            JOptionPane.showMessageDialog(null, "Wrong date format. Usage: yyyy-mm-dd");
        } else if (ex instanceof CarExceptionDates) {
            JOptionPane.showMessageDialog(null, "The year should be between 1900 and the current year.");
        } else if (ex instanceof CarExceptionNoId) {
            JOptionPane.showMessageDialog(null, "Id does not exist");
        }
    }

    private void addCar() throws ParseException, CarExceptionDates, CarExceptionNoId {
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

            carService.addCar(car);
            //System.out.println(car.getId());
            JOptionPane.showMessageDialog(null, "Car added successfully!");
        }
    }

    private void updateCar() throws CarExceptionNoId, ParseException, CarExceptionDates {
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

    private void deleteCar() throws CarExceptionNoId {
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

    private String getCar() throws CarExceptionNoId {
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

    private void listCars() throws CarExceptionNoId {
        StringBuilder carList = new StringBuilder();
        for (CarModel car : carService.getAllCars()) {
            carList.append(car.toString()).append('\n');
        }
        JOptionPane.showMessageDialog(null, carList.toString(), "Car List", JOptionPane.INFORMATION_MESSAGE);
    }
}

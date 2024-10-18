package edu.bbte.idde.bmim2214.presentation;

import edu.bbte.idde.bmim2214.business.*;
import edu.bbte.idde.bmim2214.business.exceptions.CarExceptionDates;
import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionNoId;
import edu.bbte.idde.bmim2214.dataaccess.model.CarModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;
import java.util.Date;

public class CarFrontend {
    private final CarService carService;

    public CarFrontend(CarService carService) {
        this.carService = carService;
    }

    public void display() {
        JFrame frame = new JFrame("Car Reselling");
        frame.setSize(700, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JButton addCarButton = new JButton("Add Car");
        JButton updateCarButton = new JButton("Update Car");
        JButton deleteCarButton = new JButton("Delete Car");
        JButton getCarButton = new JButton("Search Car");
        JButton listCarsButton = new JButton("List Cars");

        frame.setLayout(new FlowLayout());
        frame.add(addCarButton);
        frame.add(updateCarButton);
        frame.add(deleteCarButton);
        frame.add(getCarButton);
        frame.add(listCarsButton);

        addCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    addCar();
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, "Wrong parameters");
                    throw new RuntimeException(ex);
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "Wrong date format. Usage: yyyy-mm-dd");
                    throw new RuntimeException(ex);
                } catch (CarExceptionDates ex) {
                    JOptionPane.showMessageDialog(null, "The year should be between 1900 and the current year.");
                    throw new RuntimeException(ex);
                }
            }
        });

        updateCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    updateCar();
                } catch (CarExceptionNoId ex) {
                    JOptionPane.showMessageDialog(null, "Id does not exist");
                    throw new RuntimeException(ex);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, "Wrong parameters");
                    throw new RuntimeException(ex);
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, "Wrong date format. Usage: yyyy-mm-dd");
                    throw new RuntimeException(ex);
                } catch (CarExceptionDates ex) {
                    JOptionPane.showMessageDialog(null, "The year should be between 1900 and the current year.");
                    throw new RuntimeException(ex);
                }
            }
        });

        deleteCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    deleteCar();
                } catch (CarExceptionNoId | IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, "Id does not exist");
                    throw new RuntimeException(ex);
                }
            }
        });

        getCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    getCar();
                } catch (CarExceptionNoId | IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(null, "Id does not exist");
                    throw new RuntimeException(ex);
                }
            }
        });
        listCarsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listCars();
            }
        });

        frame.setVisible(true);
    }

    private void addCar() throws ParseException, CarExceptionDates {
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

        Date today = new Date();
        int result = JOptionPane.showConfirmDialog(null, panel, "Add Car", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {
            CarModel car = new CarModel();
            car.setBrand(brandField.getText());
            car.setName(modelField.getText());
            car.setYear(Integer.parseInt(yearField.getText()));
            car.setPrice(Double.parseDouble(priceField.getText()));
            car.setUploadDate(today);

            carService.addCar(car);
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

        Date today = new Date();
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

    private void getCar() throws CarExceptionNoId {
        JTextField idField = new JTextField(12);

        JPanel panel = new JPanel();
        panel.add(new JLabel("ID:"));
        panel.add(idField);
        int result = JOptionPane.showConfirmDialog(null, panel, "Search Car", JOptionPane.OK_CANCEL_OPTION);
        if (result == JOptionPane.OK_OPTION) {

            String carStr = carService.getCar(Integer.parseInt(idField.getText())).toString();
            JOptionPane.showMessageDialog(null, carStr, "Result", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void listCars() {
        StringBuilder carList = new StringBuilder();
        for (CarModel car : carService.getAllCars()) {
            carList.append(car.toString()).append("\n");
        }
        JOptionPane.showMessageDialog(null, carList.toString(), "Car List", JOptionPane.INFORMATION_MESSAGE);
    }
}

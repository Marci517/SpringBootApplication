package edu.bbte.idde.bmim2214.presentation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;

import edu.bbte.idde.bmim2214.business.exceptions.CarExceptionDates;
import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionDatabase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CarFrontend {
    private final CarActions carActions;
    private static final Logger log = LoggerFactory.getLogger(CarFrontend.class);


    public CarFrontend(CarActions carActions) {
        this.carActions = carActions;
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
        JButton listExtrasForCar = createButton("List extras for a car", this::listExtrasAction);
        JButton addExtra = createButton("Add an extra for a car", this::addExtraAction);
        JButton deleteExtra = createButton("Delete Extra from a car", this::deleteExtraAction);

        frame.add(addCarButton);
        frame.add(updateCarButton);
        frame.add(deleteCarButton);
        frame.add(getCarButton);
        frame.add(listCarsButton);
        frame.add(listExtrasForCar);
        frame.add(addExtra);
        frame.add(deleteExtra);
    }

    private JButton createButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.addActionListener(action);
        return button;
    }

    private void listExtrasAction(ActionEvent e) {
        log.info("list extras event triggered by: " + e.getActionCommand());
        try {
            carActions.listExtras();
        } catch (CarExceptionDatabase | NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Id does not exist");
        }
    }

    private void addExtraAction(ActionEvent e) {
        log.info("add extra action triggered by " + e.getActionCommand());
        try {
            carActions.addExtra();
        } catch (CarExceptionDatabase | NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Id does not exist");
        }
    }

    private void addCarAction(ActionEvent e) {
        log.info("add car event triggered by: " + e.getActionCommand());
        try {
            carActions.addCar();
        } catch (IllegalArgumentException | CarExceptionDates | CarExceptionDatabase | ParseException ex) {
            handleAddExceptions(ex);
        }
    }

    private void updateCarAction(ActionEvent e) {
        log.info("update car event triggered by: " + e.getActionCommand());
        try {
            carActions.updateCar();
        } catch (IllegalArgumentException | ParseException | CarExceptionDates | CarExceptionDatabase ex) {
            handleUpdateExceptions(ex);
        }
    }

    private void deleteCarAction(ActionEvent e) {
        log.info("delete car event triggered by: " + e.getActionCommand());
        try {
            carActions.deleteCar();
        } catch (IllegalArgumentException | CarExceptionDatabase ex) {
            JOptionPane.showMessageDialog(null, "Id does not exist");
        }
    }

    private void deleteExtraAction(ActionEvent e) {
        log.info("delete extra event triggered by: " + e.getActionCommand());
        try {
            carActions.deleteExtra();
        } catch (NumberFormatException | CarExceptionDatabase ex) {
            JOptionPane.showMessageDialog(null, "Id does not exist");
        }
    }

    private String getCarAction(ActionEvent e) {
        log.info("get car event triggered by: " + e.getActionCommand());
        try {
            return carActions.getCar();
        } catch (IllegalArgumentException | CarExceptionDatabase ex) {
            JOptionPane.showMessageDialog(null, "Id does not exist");
            return "Id does not exist";
        }
    }

    private void listCarsAction(ActionEvent e) {
        log.info("get all cars event triggered by: " + e.getActionCommand());
        try {
            carActions.listCars();
        } catch (CarExceptionDatabase ex) {
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
        } else if (ex instanceof CarExceptionDatabase) {
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
        } else if (ex instanceof CarExceptionDatabase) {
            JOptionPane.showMessageDialog(null, "Id does not exist");
        }
    }
}

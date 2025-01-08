package edu.bbte.idde.bmim2214.jsonapiservlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.bmim2214.business.AllService;
import edu.bbte.idde.bmim2214.business.serviceimps.AllServiceImp;
import edu.bbte.idde.bmim2214.business.exceptions.CarExceptionDates;
import edu.bbte.idde.bmim2214.common.ErrorMessage;
import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionDatabase;
import edu.bbte.idde.bmim2214.dataaccess.model.CarModel;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@WebServlet("/carModels")
public class CarServletJsonapi extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(CarServletJsonapi.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AllService carService = new AllServiceImp();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("GET /carModels");

        String idStr = req.getParameter("id");
        resp.setHeader("Content-Type", "application/json");
        objectMapper.setTimeZone(TimeZone.getTimeZone("Europe/Bucharest"));

        if (idStr == null) {
            try {
                List<CarModel> carModels;
                carModels = carService.getAllCars();
                objectMapper.writeValue(resp.getOutputStream(), carModels);
            } catch (CarExceptionDatabase | IOException e) {
                log.info("Failed to get all cars");
                resp.setStatus(500);
                ErrorMessage error = new ErrorMessage("Internal Server Error ",
                        "Failed to get all cars");
                objectMapper.writeValue(resp.getOutputStream(), error);
            }
        } else {
            try {
                int id = Integer.parseInt(idStr);
                CarModel carModel = carService.getCar(id);
                objectMapper.writeValue(resp.getOutputStream(), carModel);
            } catch (CarExceptionDatabase | IOException e) {
                log.info("Id does not exist");
                resp.setStatus(404);
                ErrorMessage error = new ErrorMessage("Not Found", "Id does not exist");
                objectMapper.writeValue(resp.getOutputStream(), error);
            } catch (NumberFormatException e) {
                log.info("Id does not exist");
                resp.setStatus(400);
                ErrorMessage error = new ErrorMessage("Bad Request", "Id does not exist");
                objectMapper.writeValue(resp.getOutputStream(), error);
            }
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("DELETE /carModels");

        String idStr = req.getParameter("id");
        resp.setHeader("Content-Type", "application/json");

        if (idStr == null) {
            log.info("Id does not exist");
            resp.setStatus(400);
            ErrorMessage error = new ErrorMessage("Bad Request", "Id does not exist");
            objectMapper.writeValue(resp.getOutputStream(), error);

        } else {
            try {
                int id = Integer.parseInt(idStr);
                carService.deleteCar(id);
                ErrorMessage error = new ErrorMessage("Success", "Car deleted");
                objectMapper.writeValue(resp.getOutputStream(), error);
                log.info("Car deleted!");
            } catch (CarExceptionDatabase | IOException e) {
                log.info("Id does not exist");
                resp.setStatus(404);
                ErrorMessage error = new ErrorMessage("Not Found", "Id does not exist");
                objectMapper.writeValue(resp.getOutputStream(), error);
            } catch (NumberFormatException e) {
                log.info("Id does not exist");
                resp.setStatus(400);
                ErrorMessage error = new ErrorMessage("Bad Request", "Id does not exist");
                objectMapper.writeValue(resp.getOutputStream(), error);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("POST /carModels");
        resp.setHeader("Content-Type", "application/json");
        try {
            CarModel carModel = objectMapper.readValue(req.getInputStream(), CarModel.class);
            carModel.setCarExtras(new ArrayList<>());
            LocalDate localDate = LocalDate.now();
            int year = localDate.getYear();
            Date today = new Date(year - 1900, localDate.getMonthValue() - 1, localDate.getDayOfMonth());
            carModel.setUploadDate(today);
            carService.addCar(carModel);
            ErrorMessage error = new ErrorMessage("Success", "Car added");
            objectMapper.writeValue(resp.getOutputStream(), error);
            log.info("Car added!");
        } catch (CarExceptionDates e) {
            log.info("Failed to add car.");
            resp.setStatus(400);
            ErrorMessage error = new ErrorMessage("Bad Request", "Dates should be between 1900 - actual year");
            objectMapper.writeValue(resp.getOutputStream(), error);
        } catch (IllegalArgumentException e) {
            log.info("Failed to add car.");
            resp.setStatus(400);
            ErrorMessage error = new ErrorMessage("Bad Request", "Wrong parameters");
            objectMapper.writeValue(resp.getOutputStream(), error);
        } catch (CarExceptionDatabase | IOException e) {
            log.info("Failed to add car.");
            resp.setStatus(500);
            ErrorMessage error = new ErrorMessage("Internal Server Error", "Failed to add car.");
            objectMapper.writeValue(resp.getOutputStream(), error);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("PUT /carModels");
        resp.setHeader("Content-Type", "application/json");
        try {
            CarModel carModel = objectMapper.readValue(req.getInputStream(), CarModel.class);
            LocalDate localDate = LocalDate.now();
            int year = localDate.getYear();
            Date today = new Date(year - 1900, localDate.getMonthValue() - 1, localDate.getDayOfMonth());
            carModel.setUploadDate(today);
            carService.updateCar(carModel);
            ErrorMessage error = new ErrorMessage("Success", "Car updated");
            objectMapper.writeValue(resp.getOutputStream(), error);
            log.info("Car updated!");
        } catch (CarExceptionDatabase e) {
            log.info("Failed to update car.");
            resp.setStatus(404);
            ErrorMessage error = new ErrorMessage("Not Found ", "Id does not exist.");
            objectMapper.writeValue(resp.getOutputStream(), error);
        } catch (CarExceptionDates e) {
            log.info("Failed to update car.");
            resp.setStatus(400);
            ErrorMessage error = new ErrorMessage("Bad Request", "Dates should be between 1900 - actual year");
            objectMapper.writeValue(resp.getOutputStream(), error);
        } catch (IllegalArgumentException e) {
            log.info("Failed to update car.");
            resp.setStatus(400);
            ErrorMessage error = new ErrorMessage("Bad Request", "Wrong parameters");
            objectMapper.writeValue(resp.getOutputStream(), error);
        } catch (IOException e) {
            log.info("Failed to update car.");
            resp.setStatus(500);
            ErrorMessage error = new ErrorMessage("Internal Server Error", "Failed to update car.");
            objectMapper.writeValue(resp.getOutputStream(), error);
        }
    }
}

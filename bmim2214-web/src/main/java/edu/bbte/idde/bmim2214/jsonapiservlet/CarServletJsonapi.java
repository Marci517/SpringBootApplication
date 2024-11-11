package edu.bbte.idde.bmim2214.jsonapiservlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.bmim2214.business.CarService;
import edu.bbte.idde.bmim2214.business.CarServiceImp;
import edu.bbte.idde.bmim2214.business.exceptions.CarExceptionDates;
import edu.bbte.idde.bmim2214.dataaccess.dao.CarDao;
import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionDatabase;
import edu.bbte.idde.bmim2214.dataaccess.factory.AbstractDaoFactory;
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
import java.util.List;
import java.util.TimeZone;

@WebServlet("/carModels")
public class CarServletJsonapi extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(CarServletJsonapi.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AbstractDaoFactory abstractDaoFactory = AbstractDaoFactory.getInstance();
    private final CarDao carDao = abstractDaoFactory.getCarDao();
    private final CarService carService = new CarServiceImp(carDao);

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
                resp.getWriter().write(" 500 Internal Server Error \n Failed to get all cars");
            }
        } else {
            try {
                int id = Integer.parseInt(idStr);
                CarModel carModel = carService.getCar(id);
                objectMapper.writeValue(resp.getOutputStream(), carModel);
            } catch (CarExceptionDatabase | IOException | NumberFormatException e) {
                log.info("Id does not exist");
                resp.setStatus(404);
                resp.getWriter().write(" 404 Not found \n Id does not exist");
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
            resp.setStatus(404);
            resp.getWriter().write(" 404 Not found \n Id does not exist");

        } else {
            try {
                int id = Integer.parseInt(idStr);
                carService.deleteCar(id);
                resp.getWriter().write("Car deleted!");
                log.info("Car deleted!");
            } catch (CarExceptionDatabase | IOException | NumberFormatException e) {
                log.info("Id does not exist");
                resp.setStatus(404);
                resp.getWriter().write(" 404 Not found \n Id does not exist");
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("POST /carModels");
        try {
            CarModel carModel = objectMapper.readValue(req.getInputStream(), CarModel.class);
            LocalDate localDate = LocalDate.now();
            int year = localDate.getYear();
            Date today = new Date(year - 1900, localDate.getMonthValue() - 1, localDate.getDayOfMonth());
            carModel.setUploadDate(today);
            carService.addCar(carModel);
            resp.getWriter().write("Car added!");
            log.info("Car added!");
        } catch (CarExceptionDates | CarExceptionDatabase | IllegalArgumentException | IOException e) {
            log.info("Failed to add car.");
            resp.setStatus(400);
            resp.getWriter().write(" 400 Bad Request");
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("PUT /carModels");
        try {
            CarModel carModel = objectMapper.readValue(req.getInputStream(), CarModel.class);
            LocalDate localDate = LocalDate.now();
            int year = localDate.getYear();
            Date today = new Date(year - 1900, localDate.getMonthValue() - 1, localDate.getDayOfMonth());
            carModel.setUploadDate(today);
            carService.updateCar(carModel);
            resp.getWriter().write("Car updated!");
            log.info("Car updated!");
        } catch (CarExceptionDates | CarExceptionDatabase | IllegalArgumentException | IOException e) {
            log.info("Failed to update car.");
            resp.setStatus(404);
            resp.getWriter().write(" 404 Not Found");
        }
    }
}

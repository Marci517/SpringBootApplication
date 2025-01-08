package edu.bbte.idde.bmim2214.jsonapiservlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.bbte.idde.bmim2214.business.AllService;
import edu.bbte.idde.bmim2214.business.serviceimps.AllServiceImp;
import edu.bbte.idde.bmim2214.common.ErrorMessage;
import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionDatabase;
import edu.bbte.idde.bmim2214.dataaccess.model.CarExtra;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.TimeZone;

@WebServlet("/carModels/extras")
public class ExtraServletJsonapi extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(ExtraServletJsonapi.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final AllService carService = new AllServiceImp();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("GET /carExtras/extras");

        String idStr = req.getParameter("id");
        resp.setHeader("Content-Type", "application/json");
        objectMapper.setTimeZone(TimeZone.getTimeZone("Europe/Bucharest"));
        try {
            List<CarExtra> carExtras;
            carExtras = carService.getAllExtras(Long.parseLong(idStr));
            objectMapper.writeValue(resp.getOutputStream(), carExtras);
        } catch (CarExceptionDatabase | IOException e) {
            log.info("Failed to get all extras");
            resp.setStatus(500);
            ErrorMessage error = new ErrorMessage("Internal Server Error ",
                    "Failed to get all extras");
            objectMapper.writeValue(resp.getOutputStream(), error);
        } catch (NumberFormatException e) {
            log.info("Failed to get all extras");
            resp.setStatus(500);
            ErrorMessage error = new ErrorMessage("Bad Request ",
                    "Failed to get all extras, no such an extraID");
            objectMapper.writeValue(resp.getOutputStream(), error);
        }

    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("DELETE /carModels/extras");

        String carIdStr = req.getParameter("carId");
        String extrarIdStr = req.getParameter("extraId");
        resp.setHeader("Content-Type", "application/json");

        if (carIdStr == null || extrarIdStr == null) {
            log.info("Id does not exist");
            resp.setStatus(400);
            ErrorMessage error = new ErrorMessage("Bad Request", "Id does not exist");
            objectMapper.writeValue(resp.getOutputStream(), error);

        } else {
            try {
                long carid = Long.parseLong(carIdStr);
                long extraid = Long.parseLong(extrarIdStr);
                carService.deleteCarExtra(carid, extraid);
                ErrorMessage error = new ErrorMessage("Success", "Extra deleted");
                objectMapper.writeValue(resp.getOutputStream(), error);
                log.info("Extra deleted!");
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
        log.info("POST /carModels/extras");
        resp.setHeader("Content-Type", "application/json");
        try {
            String carIdStr = req.getParameter("id");
            CarExtra carExtra = objectMapper.readValue(req.getInputStream(), CarExtra.class);
            carService.addCarExtra(Long.parseLong(carIdStr), carExtra);
            ErrorMessage error = new ErrorMessage("Success", "Extra added");
            objectMapper.writeValue(resp.getOutputStream(), error);
            log.info("Extra added!");
        } catch (IllegalArgumentException e) {
            log.info("Failed to add extra.");
            resp.setStatus(400);
            ErrorMessage error = new ErrorMessage("Bad Request", "Wrong parameters");
            objectMapper.writeValue(resp.getOutputStream(), error);
        } catch (CarExceptionDatabase | IOException e) {
            log.info("Failed to add extra.");
            resp.setStatus(500);
            ErrorMessage error = new ErrorMessage("Internal Server Error", "Failed to add extra.");
            objectMapper.writeValue(resp.getOutputStream(), error);
        }
    }

}
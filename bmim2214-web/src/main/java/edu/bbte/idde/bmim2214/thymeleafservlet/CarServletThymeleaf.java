package edu.bbte.idde.bmim2214.thymeleafservlet;

import edu.bbte.idde.bmim2214.business.CarService;
import edu.bbte.idde.bmim2214.business.CarServiceImp;
import edu.bbte.idde.bmim2214.dataaccess.dao.CarDao;
import edu.bbte.idde.bmim2214.dataaccess.exceptions.CarExceptionDatabase;
import edu.bbte.idde.bmim2214.dataaccess.factory.AbstractDaoFactory;
import edu.bbte.idde.bmim2214.dataaccess.model.CarModel;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet("/carModelsThyme")
public class CarServletThymeleaf extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(CarServletThymeleaf.class);
    private final AbstractDaoFactory abstractDaoFactory = AbstractDaoFactory.getInstance();
    private final CarDao carDao = abstractDaoFactory.getCarDao();
    private final CarService carService = new CarServiceImp(carDao);

    @Override
    public void init() throws ServletException {
        super.init();
        ThymeleafEngineFactory.buildEngine(getServletContext());
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("GET /carModelsThyme");

        String idStr = req.getParameter("id");

        if (idStr == null) {
            try {
                List<CarModel> carModels;
                carModels = carService.getAllCars();
                Map<String, Object> model = new ConcurrentHashMap<>();
                model.put("carModels", carModels);
                ThymeleafEngineFactory.process(req, resp, "index.html", model);
            } catch (CarExceptionDatabase | IOException e) {
                log.info("Failed to get all cars");
                resp.setStatus(500);
                resp.getWriter().write(" 500 Internal Server Error \n Failed to get all cars");
            }
        } else {
            try {
                int id = Integer.parseInt(idStr);
                CarModel carModel = carService.getCar(id);
                Map<String, Object> model = new ConcurrentHashMap<>();
                model.put("carModel", carModel);
                ThymeleafEngineFactory.process(req, resp, "index.html", model);
            } catch (CarExceptionDatabase | IOException | NumberFormatException e) {
                log.info("Id does not exist");
                resp.setStatus(404);
                resp.getWriter().write(" 404 Not found \n Id does not exist");
            }
        }
    }
}

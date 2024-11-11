package edu.bbte.idde.bmim2214.auth;

import edu.bbte.idde.bmim2214.thymeleafservlet.ThymeleafEngineFactory;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(LoginServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("GET /login");
        Map<String, Object> model = new ConcurrentHashMap<>();
        model.put("message", "Please log in!");
        ThymeleafEngineFactory.process(req, resp, "login.html", model);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        log.info("POST /login");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if ("admin".equals(username) && "admin".equals(password)) {
            HttpSession session = req.getSession();
            session.setAttribute("username", username);
            log.info("Success");
            resp.sendRedirect(req.getContextPath() + "/carModelsThyme");
        } else {
            Map<String, Object> model = new ConcurrentHashMap<>();
            model.put("message", "Invalid username or password!");
            log.info("Invalid username or password!");
            ThymeleafEngineFactory.process(req, resp, "login.html", model);
        }
    }
}

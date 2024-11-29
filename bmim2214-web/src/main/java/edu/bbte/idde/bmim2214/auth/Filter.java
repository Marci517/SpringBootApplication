package edu.bbte.idde.bmim2214.auth;

import edu.bbte.idde.bmim2214.thymeleafservlet.ThymeleafEngineFactory;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebFilter(urlPatterns = {"/carModelsThyme"})
public class Filter extends HttpFilter {
    private static final Logger log = LoggerFactory.getLogger(Filter.class);

    @Override
    public void init() {
        log.info("Initializing filter");
    }

    @Override
    protected void doFilter(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws IOException, ServletException {

        log.info("{} {}", req.getMethod(), req.getRequestURI());
        if (req.getSession().getAttribute("username") == null) {
            Map<String, Object> model = new ConcurrentHashMap<>();
            model.put("message", "Please log in!");
            ThymeleafEngineFactory.process(req, res, "login.html", model);
        } else {
            chain.doFilter(req, res);
        }
    }
}


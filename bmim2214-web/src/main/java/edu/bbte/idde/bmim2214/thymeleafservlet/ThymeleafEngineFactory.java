package edu.bbte.idde.bmim2214.thymeleafservlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.web.IWebExchange;
import org.thymeleaf.web.servlet.JakartaServletWebApplication;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;

public class ThymeleafEngineFactory {
    private static final Logger log = LoggerFactory.getLogger(ThymeleafEngineFactory.class);

    private static TemplateEngine engine;
    private static JakartaServletWebApplication application;

    public static synchronized void buildEngine(ServletContext servletContext) {
        log.info("Building Thymeleaf renderer");

        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setPrefix("/templates/");

        engine = new TemplateEngine();
        engine.setTemplateResolver(templateResolver);
        application = JakartaServletWebApplication.buildApplication(servletContext);
    }

    public static void process(
            HttpServletRequest req,
            HttpServletResponse resp,
            String view,
            Map<String, Object> model
    ) throws IOException {
        IWebExchange webExchange = application.buildExchange(req, resp);
        WebContext context = new WebContext(webExchange, Locale.getDefault(), model);
        engine.process(view, context, resp.getWriter());
    }
}

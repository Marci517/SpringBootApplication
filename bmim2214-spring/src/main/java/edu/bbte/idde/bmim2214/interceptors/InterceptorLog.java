package edu.bbte.idde.bmim2214.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

public class InterceptorLog implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(InterceptorLog.class);

    @Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex) {
        log.info("HTTP {} {} - Status: {}", request.getMethod(), request.getRequestURI(), response.getStatus());
    }
}

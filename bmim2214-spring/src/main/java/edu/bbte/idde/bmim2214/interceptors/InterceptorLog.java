package edu.bbte.idde.bmim2214.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class InterceptorLog implements HandlerInterceptor {

    @Override
    public void afterCompletion(HttpServletRequest request,
                                HttpServletResponse response, Object handler, Exception ex) {
        log.info("HTTP {} {} - Status: {}", request.getMethod(), request.getRequestURI(), response.getStatus());
    }
}

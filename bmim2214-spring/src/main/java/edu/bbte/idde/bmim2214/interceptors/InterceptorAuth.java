package edu.bbte.idde.bmim2214.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;

public class InterceptorAuth implements HandlerInterceptor {
    private static final String API_KEY = "1";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String apiKey = request.getHeader("key");

        if (apiKey == null || !apiKey.equals(API_KEY)) {
            response.sendError(HttpStatus.FORBIDDEN.value(), "Invalid API Key");
            return false;
        }
        return true;
    }
}

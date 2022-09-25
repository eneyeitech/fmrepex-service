package com.example.fmrepexservice.security;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.naming.AuthenticationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, org.springframework.security.core.AuthenticationException authException) throws IOException, ServletException {
        //System.out.println("Exception == "+authException);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, authException.getMessage());
        /**
         response.setContentType("application/json;charset=UTF-8");
         response.setStatus(HttpServletResponse.SC_FORBIDDEN);
         response.getWriter().write(Map.of(
         "message", "Access denied!",
         "timestamp", LocalDateTime.now(),
         "error","Forbidden",
         "status", 403

         ).toString());
         */
    }
}


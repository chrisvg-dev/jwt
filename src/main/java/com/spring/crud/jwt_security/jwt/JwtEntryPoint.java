package com.spring.crud.jwt_security.jwt;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {
    private static final Logger LOG = LoggerFactory.getLogger(JwtEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        String message = String.format( "Fail en el método commence: %s", authException.getMessage() );
        LOG.error( message );
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No tienes acceso a este sitio web.");
    }
}

package com.example.parking.permisos;

import com.example.parking.dto.response.ErrorResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;

@Component
public class RolInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (request.getServletPath().contains("/swagger-ui") || request.getServletPath().contains("/api-docs")){
            return true;
        }

        if (handler instanceof HandlerMethod) {

            HandlerMethod handlerMethod = (HandlerMethod) handler;
            String requestUri = request.getRequestURI();
            if (handlerMethod.getMethod().isAnnotationPresent(Ingreso.class)) {
                Ingreso annotation = handlerMethod.getMethod().getAnnotation(Ingreso.class);
                String[] roles = annotation.value();
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                if (authentication.isAuthenticated() && !authentication.getAuthorities().isEmpty()) {
                    GrantedAuthority authority = authentication.getAuthorities().iterator().next();
                    for (int i = 0; i < roles.length; i++) {
                        System.out.println("Rol posicion ---> "+ roles[i]);

                        if (roles[i].equals(authority.getAuthority())) {
                            return true;
                        }
                    }
                }
            }
        }
        errorException(response, HttpStatus.UNAUTHORIZED.value(), "Acceso denegado");
        return false;
    }

    protected void errorException(HttpServletResponse response, Integer httpStatus, String errorMessage) throws IOException {
        ErrorResponse error = new ErrorResponse(errorMessage);
        response.setStatus(httpStatus);
        response.setContentType("application/json");
        response.getWriter().write(new ObjectMapper().writeValueAsString(error));
        response.getWriter().flush();
    }

}
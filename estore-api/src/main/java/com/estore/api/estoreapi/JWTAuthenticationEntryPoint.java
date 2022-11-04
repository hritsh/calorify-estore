package com.estore.api.estoreapi;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.io.IOException;
/**
 * Implements AuthenticationEntryPoint which is used to commence an authentication scheme
 * 
 * {@link @Component} is a spring annotation that indicates that this class is a Component. Marks which classes are beans that are
 * Spring managed components
 * 
 * @author Team E
 */
@Component
public class JWTAuthenticationEntryPoint implements AuthenticationEntryPoint{
    /**
     * Used to send UNAUTHORIZED as a response when a user with an insufficient role accesses a privileged page
     * 
     * @param {@linkplain HttpServletRequest request }  
     * @param {@linkplain HttpServletResponse response }
     * @param {@linkplain AuthenticationException authException }
     * 
     * @throws IOException
     * @throws ServletException
     * 
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws ServletException, IOException {
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }
}

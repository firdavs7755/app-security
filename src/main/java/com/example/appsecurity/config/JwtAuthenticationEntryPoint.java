package com.example.appsecurity.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
//Hatolik boldi deb chiqadigan clas.Ushbu nastroyka tizimga kirishda emas.
// Tizimga kirib biror  amalni bajarayotganda token bn muammo bolsa chiqadi
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        System.err.println("Some error occured with token");
    }
}

package com.example.appsecurity.config;

import com.example.appsecurity.repositories.UserRepository;
import com.example.appsecurity.service.AuthService;
import com.example.appsecurity.service.JWTService;
import com.sun.security.auth.UserPrincipal;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import sun.net.httpserver.AuthFilter;

import java.io.IOException;
import java.security.PublicKey;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    AuthService authService;

    @Autowired
    JWTService jwtService;
    @Autowired
    UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
            if (StringUtils.hasText(jwt) && jwtService.validateToken(jwt)){
                String username = jwtService.getUserNameFromJwt(jwt);

                UserDetails userDetails = authService.loadUserByUsername(  username);
                UsernamePasswordAuthenticationToken  authentication = new UsernamePasswordAuthenticationToken(userDetails,null,userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication)  ;
            }
        } catch (Exception e){
            System.err.println("Exp: "+e.getMessage());
        }
    }

    public String getJwtFromRequest(HttpServletRequest servletRequest){
        String bearerToken = servletRequest.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")){
            return bearerToken.substring(7);
        }
        return null;
    }
}

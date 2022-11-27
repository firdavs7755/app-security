package com.example.appsecurity.service;

import com.example.appsecurity.entity.User;
import com.example.appsecurity.repositories.UserRepository;
import io.jsonwebtoken.*;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.logging.Logger;

@Component
public class JWTService {

    private static final Logger _logger = (Logger) LoggerFactory.getLogger(JWTService.class);
    @Autowired
    UserRepository userRepository;

    @Value("${api.secretkey}")
    private String secretkey;

    @Value("${api.jwtExpiration}")
    private Long maxAge;

    public String generateToken(Authentication authentication){
        User user = (User) authentication.getPrincipal();
        Date expDate = new Date(new Date().getTime()+maxAge);
        return Jwts.builder()
                    .setSubject(user.getUsername())
                    .setIssuedAt(new Date())
                    .setExpiration(expDate)
                    .signWith(SignatureAlgorithm.ES256,secretkey)
                    .compact();

    }

    public String getUserNameFromJwt(String token){
        Claims claim = Jwts.parser()
                .setSigningKey(secretkey)
                .parseClaimsJws(token)
                .getBody();
        return claim.getSubject();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(secretkey).parseClaimsJws(token);
            return true;
        } catch (SignatureException s){
            System.err.println("Invalid JWT signature:"+s.getMessage());
        }catch (MalformedJwtException m){
            System.err.println("Invalid JWT token:"+m.getMessage());
        }catch (ExpiredJwtException e){
            System.err.println("Expired JWT token:"+e.getMessage());
        }
        return false;
    }

}

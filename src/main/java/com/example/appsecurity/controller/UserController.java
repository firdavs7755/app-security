package com.example.appsecurity.controller;

import com.example.appsecurity.entity.User;
import com.example.appsecurity.enums.RoleNames;
import com.example.appsecurity.req.ReqSignIn;
import com.example.appsecurity.req.ReqUser;
import com.example.appsecurity.repositories.RoleRepositiory;
import com.example.appsecurity.repositories.UserRepository;
import com.example.appsecurity.service.JWTService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/sign")
public class UserController {

    @Autowired
    UserRepository userRepository;
    @Autowired
    RoleRepositiory roleRepositiory;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JWTService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;

    @PostMapping("/up")
    public HttpEntity<?> signUpUser(@RequestBody ReqUser reqUser){
        Optional<User> byUsername = Optional.ofNullable(userRepository.findByUsername(reqUser.getUsername()));
        if (byUsername.isPresent()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Bunaqa user mavjud");
        } else {
            User u = new User(
                    reqUser.getFill_name(),
                    reqUser.getPassword(),
                    reqUser.getEmail(),
                    reqUser.getUsername(),
                    roleRepositiory.findAllByRole(RoleNames.ROLE_USER  )
            );
            userRepository.save(u);
            return ResponseEntity.accepted().body("Successfully registered");
        }
    }

    @PostMapping("/in")
    public HttpEntity<?> sigInUSer(@RequestBody ReqSignIn reqSignIn){
        Optional<User> byUsername = Optional.ofNullable(userRepository.findByUsername(reqSignIn.getUsername()));
        if (byUsername.isPresent()){
            User user = byUsername.get();
            if (encoder.matches(reqSignIn.getUsername(),user.getPassword())){
                Authentication authenticate = new UsernamePasswordAuthenticationToken(reqSignIn.getUsername(),reqSignIn.getPassword());

                SecurityContextHolder.getContext().setAuthentication(authenticate);
                return ResponseEntity.ok("Bearer "+jwtService.generateToken(authenticate));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username or password wrong!");
            }
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Username or password wrong!");
        }
    }



}










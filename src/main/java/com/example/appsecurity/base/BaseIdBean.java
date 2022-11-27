package com.example.appsecurity.base;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;

import java.util.UUID;

public class BaseIdBean {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
}

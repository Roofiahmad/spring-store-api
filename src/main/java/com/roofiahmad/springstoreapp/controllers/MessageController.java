package com.roofiahmad.springstoreapp.controllers;

import com.roofiahmad.springstoreapp.entities.Message;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {
    @RequestMapping("/hello")
    public Message hello() {
        return new Message("Hello World!");
    }
}

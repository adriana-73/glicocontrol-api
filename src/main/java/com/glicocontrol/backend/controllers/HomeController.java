package com.glicocontrol.backend.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

    @RestController
    public class HomeController {

        @GetMapping("/")
        public String home() {
            return "API do Glicocontrol está a funcionar!";
        }
    }


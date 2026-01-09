package com.example.CashBuzz.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
@RestController
public class OAuthStatusController {

    @GetMapping("/login-success")
    public void success(HttpServletResponse response) throws IOException {
        response.sendRedirect("http://localhost:5500/dashboard.html");
    }

    @GetMapping("/auth-failure")
    public String failure() {
        return "OAUTH FAILED – CHECK GOOGLE CONFIG";
    }
}
package org.manthan.springredissample.controllers;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/")
public class HelloWorldController {

    @GetMapping("/")
    public void HelloWorld(HttpServletResponse response) throws IOException {
        response.sendRedirect("/actuator/info");
    }
}

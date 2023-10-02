package com.example.hng_task_5_videouploadertask.controller;



import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        // Handle the error and return a custom error page
        return "error-page"; // Assuming you have an error-page.html or error-page.jsp
    }
}

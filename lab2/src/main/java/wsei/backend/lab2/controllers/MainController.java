package wsei.backend.lab2.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    @GetMapping("/")
    public static String Indev() {
        return "Index";
    }
    
}

package org.example.controller;

import org.example.aop.Loggable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @Loggable
    @GetMapping("/index")
    public String index() {
        return "index";
    }
}

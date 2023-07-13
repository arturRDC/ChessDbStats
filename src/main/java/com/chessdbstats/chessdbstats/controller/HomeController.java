package com.chessdbstats.chessdbstats.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping(value={"/", "/home"})
    public String getHome() {
        return "home";
    }
    @GetMapping("/login")
    public String getLogin() {
        return "login";
    }
    @GetMapping("/register")
    public String getRegister() {
        return "register";
    }
    @GetMapping("/about")
    public String getAbout() {
        return "about";
    }
    @GetMapping("/demo")
    public String getDemo() {

        return "redirect:/dashboard/1";
    }
}

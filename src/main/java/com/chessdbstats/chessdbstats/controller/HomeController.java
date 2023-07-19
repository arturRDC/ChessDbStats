package com.chessdbstats.chessdbstats.controller;

import com.chessdbstats.chessdbstats.auth.JwtUtil;
import com.chessdbstats.chessdbstats.model.User;
import com.chessdbstats.chessdbstats.service.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
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
    @GetMapping("/edit-profile")
    public String editProfile(Model model) {
        User loggedInUser = customUserDetailsService.getLoggedInUser();
        model.addAttribute("loggedInUser", loggedInUser);
        return "edit-profile";
    }

}

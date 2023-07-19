package com.chessdbstats.chessdbstats.controller;

import com.chessdbstats.chessdbstats.model.User;
import com.chessdbstats.chessdbstats.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ExploreController {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @GetMapping("/explore/{id}")
    public String getExplore(@PathVariable Long id, Model model) {
        User loggedInUser = customUserDetailsService.getLoggedInUser();
        model.addAttribute("loggedInUser", loggedInUser);
        return "explore";
    }
}

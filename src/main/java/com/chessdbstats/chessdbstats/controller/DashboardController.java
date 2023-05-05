package com.chessdbstats.chessdbstats.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DashboardController {
    @RequestMapping("/dashboard")
    String getDashboard() {
        return "dashboard";
    }
}

package com.chessdbstats.chessdbstats.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("dashboard/")
public class DashboardController {
    @GetMapping("results/{collectionId}")
    String getDashboard(Model model, @PathVariable("collectionId") Integer collectionId) {
        model.addAttribute("collectionId", collectionId);
        return "results";
    }
}

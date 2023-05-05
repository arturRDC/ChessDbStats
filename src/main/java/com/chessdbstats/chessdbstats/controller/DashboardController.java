package com.chessdbstats.chessdbstats.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("dashboard/")
public class DashboardController {
    @GetMapping("{collectionId}")
    String getDashboard(Model model, @PathVariable("collectionId") Integer collectionId) {
        model.addAttribute("collectionId", collectionId);
        return "results";
    }
    @GetMapping("results/{collectionId}")
    String getResults(Model model, @PathVariable("collectionId") Integer collectionId) {
        model.addAttribute("collectionId", collectionId);
        return "results";
    }
    @GetMapping("logistics/{collectionId}")
    String getLogistics(Model model, @PathVariable("collectionId") Integer collectionId) {
        model.addAttribute("collectionId", collectionId);
        return "logistics";
    }
    @GetMapping("players/{collectionId}")
    String getPlayers(Model model, @PathVariable("collectionId") Integer collectionId) {
        model.addAttribute("collectionId", collectionId);
        return "players";
    }
    @GetMapping("square-frequency/{collectionId}")
    String getSquareFrequency(Model model, @PathVariable("collectionId") Integer collectionId) {
        model.addAttribute("collectionId", collectionId);
        return "square-frequency";
    }
}

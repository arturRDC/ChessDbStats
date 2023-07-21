package com.chessdbstats.chessdbstats.controller;

import com.chessdbstats.chessdbstats.model.Player;
import com.chessdbstats.chessdbstats.model.User;
import com.chessdbstats.chessdbstats.service.CustomUserDetailsService;
import com.chessdbstats.chessdbstats.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("dashboard/")
public class DashboardController {
    @Autowired
    private CustomUserDetailsService customUserDetailsService;
    @Autowired
    private StatsService statsService;
    @GetMapping("{collectionId}")
    String getDashboard(Model model, @PathVariable("collectionId") Integer collectionId) {
        model.addAttribute("collectionId", collectionId);
        User loggedInUser = customUserDetailsService.getLoggedInUser();
        model.addAttribute("loggedInUser", loggedInUser);
        return "results";
    }
    @GetMapping("results/{collectionId}")
    String getResults(Model model, @PathVariable("collectionId") Integer collectionId) {
        model.addAttribute("collectionId", collectionId);
        User loggedInUser = customUserDetailsService.getLoggedInUser();
        model.addAttribute("loggedInUser", loggedInUser);
        return "results";
    }
    @GetMapping("logistics/{collectionId}")
    String getLogistics(Model model, @PathVariable("collectionId") Integer collectionId) {
        model.addAttribute("collectionId", collectionId);
        User loggedInUser = customUserDetailsService.getLoggedInUser();
        model.addAttribute("loggedInUser", loggedInUser);
        return "logistics";
    }
    @GetMapping("players/{collectionId}")
    String getPlayers(Model model, @PathVariable("collectionId") Long collectionId) {
        model.addAttribute("collectionId", collectionId);

        List<Player> players = statsService.getPlayers(collectionId);

        model.addAttribute("players", players);
        User loggedInUser = customUserDetailsService.getLoggedInUser();
        model.addAttribute("loggedInUser", loggedInUser);
        return "players";
    }
    @GetMapping("square-frequency/{collectionId}")
    String getSquareFrequency(Model model, @PathVariable("collectionId") Integer collectionId) {
        model.addAttribute("collectionId", collectionId);
        User loggedInUser = customUserDetailsService.getLoggedInUser();
        model.addAttribute("loggedInUser", loggedInUser);
        return "square-frequency";
    }
}

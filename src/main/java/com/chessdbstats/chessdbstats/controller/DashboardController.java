package com.chessdbstats.chessdbstats.controller;

import com.chessdbstats.chessdbstats.model.Player;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

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

        List<Player> players = new ArrayList<>();
        players.add(new Player("Player 1", 10, 5, 3, 18));
        players.add(new Player("Player 2", 8, 7, 2, 17));
        players.add(new Player("Player 3", 8, 7, 2, 17));
        players.add(new Player("Player 4", 8, 7, 2, 17));
        players.add(new Player("Player 5", 8, 7, 2, 17));
        players.add(new Player("Player 6", 8, 7, 2, 17));
        players.add(new Player("Player 7", 8, 7, 2, 17));
        players.add(new Player("Player 8", 8, 7, 2, 17));
        players.add(new Player("Player 9", 8, 7, 2, 17));
        players.add(new Player("Player 10", 8, 7, 2, 17));

        model.addAttribute("players", players);
        return "players";
    }
    @GetMapping("square-frequency/{collectionId}")
    String getSquareFrequency(Model model, @PathVariable("collectionId") Integer collectionId) {
        model.addAttribute("collectionId", collectionId);
        return "square-frequency";
    }
}

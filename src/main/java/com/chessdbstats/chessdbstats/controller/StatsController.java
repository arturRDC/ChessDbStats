package com.chessdbstats.chessdbstats.controller;

import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
public class StatsController {
    @GetMapping("stats/openings")
    public static ResponseEntity<String> getOpenings() {
        Object[][] data = {{"Opening", "Wins", "Draws", "Losses", "Number of games"}, {"King's Gambit", 10, 24, 20, 54}, {"Catalan", 28, 19, 29, 76}, {"English", 16, 22, 23, 61}, {"Spanish", 16, 22, 23, 61}, {"Italian", 16, 22, 23, 61}};

        Gson gson = new Gson();
        String json = gson.toJson(data);
        return ResponseEntity.ok(json);
    }

    @GetMapping("stats/move-count")
    public static ResponseEntity<String> getMoveCount() {
                Object[][] data = {{"# of Moves"}, {12.2}, {9.1}, {12.2}, {22.9}, {0.9}, {36.6}, {9.1}, {30.5}, {6.1}, {2.7}, {0.9}, {2.7}, {27.1}, {3.4}, {5.5}, {21.0}, {7.9}, {1.2}, {4.6}, {1.5}, {7.9}, {2.0}, {45.7}, {12.2}, {30.5}, {15.2}, {30.5}, {1.8}};
        Gson gson = new Gson();
        String json = gson.toJson(data);
        return ResponseEntity.ok(json);
    }
    @GetMapping("stats/termination")
    public static ResponseEntity<String> getTemination() {


                Object[][] data = {
                        {"Game termination", "Number of Games"},
                        {"Normal", 11},
                        {"Lost On Time", 2},
                };
        Gson gson = new Gson();
        String json = gson.toJson(data);
        return ResponseEntity.ok(json);
    }

}


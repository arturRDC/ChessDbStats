package com.chessdbstats.chessdbstats.controller;

import com.google.gson.Gson;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class StatsController {

    // Results Page
    @GetMapping("api/v1/stats/openings/{collectionId}")
    public static ResponseEntity<String> getOpenings(@PathVariable("collectionId") Integer collectionId) {
        Object[][] data = {{"Opening", "Wins", "Draws", "Losses", "Number of games"}, {"King's Gambit", 10, 24, 20, 54}, {"Catalan", 28, 19, 29, 76}, {"English", 16, 22, 23, 61}, {"Spanish", 16, 22, 23, 61}, {"Italian", 16, 22, 23, 61}};

        Gson gson = new Gson();
        String json = gson.toJson(data);
        return ResponseEntity.ok(json);
    }


    @GetMapping("api/v1/stats/move-count/{collectionId}")
    public static ResponseEntity<String> getMoveCount(@PathVariable("collectionId") Integer collectionId) {
                Object[][] data = {{"# of Moves"}, {12.2}, {9.1}, {12.2}, {22.9}, {0.9}, {36.6}, {9.1}, {30.5}, {6.1}, {2.7}, {0.9}, {2.7}, {27.1}, {3.4}, {5.5}, {21.0}, {7.9}, {1.2}, {4.6}, {1.5}, {7.9}, {2.0}, {45.7}, {12.2}, {30.5}, {15.2}, {30.5}, {1.8}};
        Gson gson = new Gson();
        String json = gson.toJson(data);
        return ResponseEntity.ok(json);
    }
    @GetMapping("api/v1/stats/termination/{collectionId}")
    public static ResponseEntity<String> getTemination(@PathVariable("collectionId") Integer collectionId) {


                Object[][] data = {
                        {"Game termination", "Number of Games"},
                        {"Normal", 11},
                        {"Lost On Time", 2},
                };
        Gson gson = new Gson();
        String json = gson.toJson(data);
        return ResponseEntity.ok(json);
    }

    // Logistics Page
    @GetMapping("api/v1/stats/events/{collectionId}")
    public static ResponseEntity<String> getEvents(@PathVariable("collectionId") Integer collectionId) {
        Object[][] data = {{"Event", "Number of games"}, {"World Chess Championship 2023", 54}, {"Lichess Tournament", 28}, {"No Event", 61}};

        Gson gson = new Gson();
        String json = gson.toJson(data);
        return ResponseEntity.ok(json);
    }

    @GetMapping("api/v1/stats/sites/{collectionId}")
    public static ResponseEntity<String> getSites(@PathVariable("collectionId") Integer collectionId) {


        Object[][] data = {
                {"Site / Location", "Number of Games"},
                {"Lichess.org", 300},
                {"Chess.com", 200},
                {"Clube Natal RN", 12},
        };
        Gson gson = new Gson();
        String json = gson.toJson(data);
        return ResponseEntity.ok(json);
    }
    @GetMapping("api/v1/stats/time-controls/{collectionId}")
    public static ResponseEntity<String> getTimeControls(@PathVariable("collectionId") Integer collectionId) {


        Object[][] data = {
                {"Time Control", "Number of Games"},
                {"3/2", 300},
                {"2/1", 200},
                {"1/0", 12},
        };
        Gson gson = new Gson();
        String json = gson.toJson(data);
        return ResponseEntity.ok(json);
    }
    @GetMapping("api/v1/stats/dates/{collectionId}")
    public static ResponseEntity<String> getDates(@PathVariable("collectionId") Integer collectionId) {


        Object[][] data = {
                {"Date", "Number of Games"},
                {"2023.05.27", 8},
                {"2023.04.27", 15},
                {"2023.02.14", 12},
                {"2022.04.28", 20},
                {"2022.04.29", 7},
                {"2022.04.30", 2},
        };
        Gson gson = new Gson();
        String json = gson.toJson(data);
        return ResponseEntity.ok(json);
    }
    @GetMapping("api/v1/stats/square-frequencies/{collectionId}")
    public static ResponseEntity<String> getSquareFrequencies(@PathVariable("collectionId") Integer collectionId) {
        List<Map<String,Object>> squares = new ArrayList<>();
        squares.add(Map.of("row", '1', "col", 'a', "freq", 5));
        squares.add(Map.of("row", '1', "col", 'b', "freq", 4));
        squares.add(Map.of("row", '1', "col", 'c', "freq", 6));
        squares.add(Map.of("row", '1', "col", 'd', "freq", 9));
        squares.add(Map.of("row", '1', "col", 'e', "freq", 1));
        squares.add(Map.of("row", '1', "col", 'f', "freq", 1));
        squares.add(Map.of("row", '1', "col", 'g', "freq", 8));
        squares.add(Map.of("row", '1', "col", 'h', "freq", 2));
        squares.add(Map.of("row", '2', "col", 'a', "freq", 9));
        squares.add(Map.of("row", '2', "col", 'b', "freq", 7));
        squares.add(Map.of("row", '2', "col", 'c', "freq", 4));
        squares.add(Map.of("row", '2', "col", 'd', "freq", 6));
        squares.add(Map.of("row", '2', "col", 'e', "freq", 0));
        squares.add(Map.of("row", '2', "col", 'f', "freq", 1));
        squares.add(Map.of("row", '2', "col", 'g', "freq", 5));
        squares.add(Map.of("row", '2', "col", 'h', "freq", 2));
        squares.add(Map.of("row", '3', "col", 'a', "freq", 9));
        squares.add(Map.of("row", '3', "col", 'b', "freq", 9));
        squares.add(Map.of("row", '3', "col", 'c', "freq", 1));
        squares.add(Map.of("row", '3', "col", 'd', "freq", 2));
        squares.add(Map.of("row", '3', "col", 'e', "freq", 6));
        squares.add(Map.of("row", '3', "col", 'f', "freq", 8));
        squares.add(Map.of("row", '3', "col", 'g', "freq", 8));
        squares.add(Map.of("row", '3', "col", 'h', "freq", 9));
        squares.add(Map.of("row", '4', "col", 'a', "freq", 8));
        squares.add(Map.of("row", '4', "col", 'b', "freq", 5));
        squares.add(Map.of("row", '4', "col", 'c', "freq", 6));
        squares.add(Map.of("row", '4', "col", 'd', "freq", 8));
        squares.add(Map.of("row", '4', "col", 'e', "freq", 7));
        squares.add(Map.of("row", '4', "col", 'f', "freq", 1));
        squares.add(Map.of("row", '4', "col", 'g', "freq", 9));
        squares.add(Map.of("row", '4', "col", 'h', "freq", 0));
        squares.add(Map.of("row", '5', "col", 'a', "freq", 1));
        squares.add(Map.of("row", '5', "col", 'b', "freq", 5));
        squares.add(Map.of("row", '5', "col", 'c', "freq", 4));
        squares.add(Map.of("row", '5', "col", 'd', "freq", 7));
        squares.add(Map.of("row", '5', "col", 'e', "freq", 8));
        squares.add(Map.of("row", '5', "col", 'f', "freq", 8));
        squares.add(Map.of("row", '5', "col", 'g', "freq", 5));
        squares.add(Map.of("row", '5', "col", 'h', "freq", 3));
        squares.add(Map.of("row", '6', "col", 'a', "freq", 5));
        squares.add(Map.of("row", '6', "col", 'b', "freq", 8));
        squares.add(Map.of("row", '6', "col", 'c', "freq", 0));
        squares.add(Map.of("row", '6', "col", 'd', "freq", 4));
        squares.add(Map.of("row", '6', "col", 'e', "freq", 9));
        squares.add(Map.of("row", '6', "col", 'f', "freq", 9));
        squares.add(Map.of("row", '6', "col", 'g', "freq", 4));
        squares.add(Map.of("row", '6', "col", 'h', "freq", 4));
        squares.add(Map.of("row", '7', "col", 'a', "freq", 8));
        squares.add(Map.of("row", '7', "col", 'b', "freq", 4));
        squares.add(Map.of("row", '7', "col", 'c', "freq", 3));
        squares.add(Map.of("row", '7', "col", 'd', "freq", 1));
        squares.add(Map.of("row", '7', "col", 'e', "freq", 1));
        squares.add(Map.of("row", '7', "col", 'f', "freq", 3));
        squares.add(Map.of("row", '7', "col", 'g', "freq", 7));
        squares.add(Map.of("row", '7', "col", 'h', "freq", 8));
        squares.add(Map.of("row", '8', "col", 'a', "freq", 8));
        squares.add(Map.of("row", '8', "col", 'b', "freq", 1));
        squares.add(Map.of("row", '8', "col", 'c', "freq", 9));
        squares.add(Map.of("row", '8', "col", 'd', "freq", 1));
        squares.add(Map.of("row", '8', "col", 'e', "freq", 3));
        squares.add(Map.of("row", '8', "col", 'f', "freq", 6));
        squares.add(Map.of("row", '8', "col", 'g', "freq", 4));
        squares.add(Map.of("row", '8', "col", 'h', "freq", 8));

        Gson gson = new Gson();
        String json = gson.toJson(squares);
        return ResponseEntity.ok(json);
    }
}


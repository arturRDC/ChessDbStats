package com.chessdbstats.chessdbstats.controller;

import com.chessdbstats.chessdbstats.service.ChessOpeningsService;
import com.chessdbstats.chessdbstats.service.CollectionService;
import com.chessdbstats.chessdbstats.service.StatsService;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class StatsController {

    @Autowired
    CollectionService collectionService;
    @Autowired
    ChessOpeningsService chessOpeningsService;
    @Autowired
    StatsService statsService;

    @GetMapping("api/v1/stats/openings/{collectionId}")
    public ResponseEntity<String> getOpenings(@PathVariable("collectionId") Long collectionId) {
        Object[][] topEntries = statsService.getOpenings(collectionId);

        Gson gson = new Gson();
        String json = gson.toJson(topEntries);
        return ResponseEntity.ok(json);
    }


    @GetMapping("api/v1/stats/move-count/{collectionId}")
    public ResponseEntity<String> getMoveCount(@PathVariable("collectionId") Long collectionId) {
        Object [][] data = statsService.getMoveCount(collectionId);

        Gson gson = new Gson();
        String json = gson.toJson(data);
        return ResponseEntity.ok(json);
    }
    @GetMapping("api/v1/stats/termination/{collectionId}")
    public ResponseEntity<String> getTermination(@PathVariable("collectionId") Long collectionId) {
        Object [][] data = statsService.getTermination(collectionId);

        Gson gson = new Gson();
        String json = gson.toJson(data);
        return ResponseEntity.ok(json);
    }

    // Logistics Page
    @GetMapping("api/v1/stats/events/{collectionId}")
    public ResponseEntity<String> getEvents(@PathVariable("collectionId") Long collectionId) {
        Object[][] data = statsService.getEvents(collectionId);
        Gson gson = new Gson();
        String json = gson.toJson(data);
        return ResponseEntity.ok(json);
    }

    @GetMapping("api/v1/stats/sites/{collectionId}")
    public ResponseEntity<String> getSites(@PathVariable("collectionId") Long collectionId) {
        Object[][] data = statsService.getSites(collectionId);

        Gson gson = new Gson();
        String json = gson.toJson(data);
        return ResponseEntity.ok(json);
    }



    @GetMapping("api/v1/stats/time-controls/{collectionId}")
    public ResponseEntity<String> getTimeControls(@PathVariable("collectionId") Long collectionId) {
        Object[][] data = statsService.getTimeControls(collectionId);
        Gson gson = new Gson();
        String json = gson.toJson(data);
        return ResponseEntity.ok(json);
    }
    @GetMapping("api/v1/stats/dates/{collectionId}")
    public ResponseEntity<String> getDates(@PathVariable("collectionId") Long collectionId) {
        Object[][] data = statsService.getDates(collectionId);

        Gson gson = new Gson();
        String json = gson.toJson(data);
        return ResponseEntity.ok(json);
    }

    @GetMapping("api/v1/stats/square-frequencies/{collectionId}")
    public ResponseEntity<String> getSquareFrequencies(@PathVariable("collectionId") Long collectionId) {
        List<Map<String, Object>> squares = statsService.getSquareFrequencies(collectionId);
        Gson gson = new Gson();
        String json = gson.toJson(squares);
        return ResponseEntity.ok(json);
    }
}


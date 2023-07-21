package com.chessdbstats.chessdbstats.controller;

import com.chessdbstats.chessdbstats.model.Collection;
import com.chessdbstats.chessdbstats.model.Player;
import com.chessdbstats.chessdbstats.service.ChessOpeningsService;
import com.chessdbstats.chessdbstats.service.CollectionService;
import com.github.bhlangonijr.chesslib.game.*;
import com.github.bhlangonijr.chesslib.pgn.PgnHolder;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
public class StatsController {

    @Autowired
    CollectionService collectionService;
    @Autowired
    ChessOpeningsService chessOpeningsService;

    @GetMapping("api/v1/stats/openings/{collectionId}")
    public ResponseEntity<String> getOpenings(@PathVariable("collectionId") Long collectionId) {
        Collection col = collectionService.getCollectionById(collectionId);
        PgnHolder pgnHolder = new PgnHolder(col.getPgnPath());
        try {
            pgnHolder.loadPgn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Map<String, int[]> openingData = new HashMap<>();
        for (Game game : pgnHolder.getGames()) {
            String opening = game.getOpening();
            if (opening == null) {
                if (game.getEco() != null) {
                    String eco = game.getEco();
                    opening = ChessOpeningsService.getOpeningName(eco);
                } else {
                    opening = "Unknown Opening";
                }

            }
            if (!openingData.containsKey(opening)) {
                openingData.put(opening, new int[4]); // Index 0 for White Wins, 1 for Draws, 2 for Black Wins, 3 for Number of games
            }
            if (game.getResult() == GameResult.WHITE_WON) {
                openingData.get(opening)[0]++; // Incrementing white wins for that opening
            }
            if (game.getResult() == GameResult.DRAW) {
                openingData.get(opening)[1]++; // Incrementing draws for that opening
            }
            if (game.getResult() == GameResult.BLACK_WON) {
                openingData.get(opening)[2]++; // Incrementing black wins for that opening
            }
            openingData.get(opening)[3]++; // Incrementing number of games for that opening
        }

// Converting the hashmap data to the required format
        Object[][] data = new Object[openingData.size() + 1][5]; // 5 columns
        data[0] = new Object[]{"Opening", "White Wins", "Draws", "Black Wins", "Number of games"};

        int i = 1;
        for (String opening : openingData.keySet()) {
            data[i][0] = opening;
            data[i][1] = openingData.get(opening)[0]; // White Wins
            data[i][2] = openingData.get(opening)[1]; // Draws
            data[i][3] = openingData.get(opening)[2]; // Black Wins
            data[i][4] = openingData.get(opening)[3]; // Number of games
            i++;
        }

        // Sorting the array based on the number of games
        Arrays.sort(data, 1, data.length, new Comparator<Object[]>() {
            @Override
            public int compare(Object[] o1, Object[] o2) {
                int games1 = (int) o1[4];
                int games2 = (int) o2[4];
                return games2 - games1; // Sort in descending order of number of games
            }
        });

        // Extracting the top 6 entries
        Object[][] topEntries = Arrays.copyOfRange(data, 0, Math.min(7, data.length));


        Gson gson = new Gson();
        String json = gson.toJson(topEntries);
        return ResponseEntity.ok(json);
    }


    @GetMapping("api/v1/stats/move-count/{collectionId}")
    public ResponseEntity<String> getMoveCount(@PathVariable("collectionId") Long collectionId) {
        Collection col = collectionService.getCollectionById(collectionId);
        PgnHolder pgnHolder = new PgnHolder(col.getPgnPath());
        try {
            pgnHolder.loadPgn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Object[][] data = new Object[pgnHolder.getGames().size() + 1][1];
        data[0] = new Object[]{"# of Moves"};

        int index = 1;
        for (Game game : pgnHolder.getGames()) {
            data[index][0] = Integer.parseInt(game.getPlyCount());
            index++;
        }

        Gson gson = new Gson();
        String json = gson.toJson(data);
        return ResponseEntity.ok(json);
    }
    @GetMapping("api/v1/stats/termination/{collectionId}")
    public ResponseEntity<String> getTemination(@PathVariable("collectionId") Long collectionId) {
        Collection col = collectionService.getCollectionById(collectionId);
        PgnHolder pgnHolder = new PgnHolder(col.getPgnPath());
        try {
            pgnHolder.loadPgn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        int normal = 0;
        int lostTime = 0;
        int abandoned = 0;
        for (Game game : pgnHolder.getGames()) {
            if(game.getTermination() == Termination.TIME || game.getTermination() == Termination.TIME_FORFEIT) {
                lostTime++;
            } else if (game.getTermination() == Termination.ABANDONED){
                abandoned++;
            } else {
                normal++;
            }
        }

        Object[][] data = {
                        {"Game termination", "Number of Games"},
                        {"Normal", normal},
                        {"Lost On Time", lostTime},
                        {"Abandoned", abandoned},
                };
        Gson gson = new Gson();
        String json = gson.toJson(data);
        return ResponseEntity.ok(json);
    }

    // Logistics Page
    @GetMapping("api/v1/stats/events/{collectionId}")
    public ResponseEntity<String> getEvents(@PathVariable("collectionId") Long collectionId) {
        Collection col = collectionService.getCollectionById(collectionId);
        PgnHolder pgnHolder = new PgnHolder(col.getPgnPath());
        try {
            pgnHolder.loadPgn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Map<String, Integer> eventsData = new HashMap<>();
        String currentEvent;
        for (Game game : pgnHolder.getGames()) {
            currentEvent = game.getRound().getEvent().getName();
            if (!eventsData.containsKey(currentEvent)) {
                eventsData.put(currentEvent, 1);
            } else {
                int count = eventsData.get(currentEvent);
                eventsData.put(currentEvent, count + 1);
            }
        }
        Object[][] data = new Object[eventsData.size() + 1][2];
        data[0] = new Object[]{"Event", "Number of games"};
        int index = 1;
        for (Map.Entry<String, Integer> entry : eventsData.entrySet()) {
            data[index][0] = entry.getKey();
            data[index][1] = entry.getValue();
            index++;
        }

        Gson gson = new Gson();
        String json = gson.toJson(data);
        return ResponseEntity.ok(json);
    }

    @GetMapping("api/v1/stats/sites/{collectionId}")
    public ResponseEntity<String> getSites(@PathVariable("collectionId") Long collectionId) {
        Collection col = collectionService.getCollectionById(collectionId);
        PgnHolder pgnHolder = new PgnHolder(col.getPgnPath());
        try {
            pgnHolder.loadPgn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Map<String, Integer> sitesData = new HashMap<>();
        String currentSite;
        for (Game game : pgnHolder.getGames()) {
            currentSite = game.getRound().getEvent().getSite();
            currentSite = parseSiteString(currentSite);
            if (!sitesData.containsKey(currentSite)) {
                sitesData.put(currentSite, 1);
            } else {
                int count = sitesData.get(currentSite);
                sitesData.put(currentSite, count + 1);
            }
        }
        Object[][] data = new Object[sitesData.size() + 1][2];
        data[0] = new Object[]{"Sites / Location", "Number of games"};
        int index = 1;
        for (Map.Entry<String, Integer> entry : sitesData.entrySet()) {
            data[index][0] = entry.getKey();
            data[index][1] = entry.getValue();
            index++;
        }

        Gson gson = new Gson();
        String json = gson.toJson(data);
        return ResponseEntity.ok(json);
    }

    private static String parseSiteString(String input) {
        Pattern pattern = Pattern.compile("(https?://)?(www\\.)?([a-zA-Z0-9]+)\\.([a-zA-Z]+).*");
        Matcher matcher = pattern.matcher(input);

        if (matcher.find()) {
            return matcher.group(3) + "." + matcher.group(4);
        } else {
            return input;
        }
    }

    @GetMapping("api/v1/stats/time-controls/{collectionId}")
    public ResponseEntity<String> getTimeControls(@PathVariable("collectionId") Long collectionId) {
        Collection col = collectionService.getCollectionById(collectionId);
        PgnHolder pgnHolder = new PgnHolder(col.getPgnPath());
        try {
            pgnHolder.loadPgn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Map<String, Integer> timeData = new HashMap<>();
        String currentTime;
        for (Game game : pgnHolder.getGames()) {
            TimeControl tc = game.getRound().getEvent().getTimeControl();
            if(tc == null) {
                currentTime = "Unknown";
            } else {
                currentTime = tc.toString();
            }
            if (!timeData.containsKey(currentTime)) {
                timeData.put(currentTime, 1);
            } else {
                int count = timeData.get(currentTime);
                timeData.put(currentTime, count + 1);
            }
        }
        Object[][] data = new Object[timeData.size() + 1][2];
        data[0] = new Object[]{"Time Control", "Number of games"};
        int index = 1;
        for (Map.Entry<String, Integer> entry : timeData.entrySet()) {
            data[index][0] = entry.getKey();
            data[index][1] = entry.getValue();
            index++;
        }
        Gson gson = new Gson();
        String json = gson.toJson(data);
        return ResponseEntity.ok(json);
    }
    @GetMapping("api/v1/stats/dates/{collectionId}")
    public ResponseEntity<String> getDates(@PathVariable("collectionId") Long collectionId) {
        Collection col = collectionService.getCollectionById(collectionId);
        PgnHolder pgnHolder = new PgnHolder(col.getPgnPath());
        try {
            pgnHolder.loadPgn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        Map<String, Integer> datesData = new HashMap<>();
        String currentDate;
        for (Game game : pgnHolder.getGames()) {
            currentDate = game.getDate();
            if (currentDate == null) {
                currentDate = "Unknown";
            }
            if (!datesData.containsKey(currentDate)) {
                datesData.put(currentDate, 1);
            } else {
                int count = datesData.get(currentDate);
                datesData.put(currentDate, count + 1);
            }
        }
        Object[][] data = new Object[datesData.size() + 1][2];
        data[0] = new Object[]{"Date", "Number of games"};
        int index = 1;
        for (Map.Entry<String, Integer> entry : datesData.entrySet()) {
            data[index][0] = entry.getKey();
            data[index][1] = entry.getValue();
            index++;
        }

        Gson gson = new Gson();
        String json = gson.toJson(data);
        return ResponseEntity.ok(json);
    }

    @GetMapping("api/v1/stats/square-frequencies/{collectionId}")
    public ResponseEntity<String> getSquareFrequencies(@PathVariable("collectionId") Long collectionId) {
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


package com.chessdbstats.chessdbstats.service;

import com.chessdbstats.chessdbstats.model.Collection;
import com.chessdbstats.chessdbstats.model.Player;
import com.github.bhlangonijr.chesslib.game.Game;
import com.github.bhlangonijr.chesslib.game.GameResult;
import com.github.bhlangonijr.chesslib.game.Termination;
import com.github.bhlangonijr.chesslib.game.TimeControl;
import com.github.bhlangonijr.chesslib.pgn.PgnHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class StatsService {
    @Autowired
    CollectionService collectionService;
    public List<Player> getPlayers(Long collectionId) {
        PgnHolder pgnHolder = getPgnHolder(collectionId);

        Map<String, Player> playerData = new HashMap<>();
        for (Game game : pgnHolder.getGames()) {
            String whiteName = game.getWhitePlayer().getName();
            String blackName = game.getBlackPlayer().getName();
            if (whiteName == null) whiteName = "Unknown Player";
            if (blackName == null) blackName = "Unknown Player";


            if (!playerData.containsKey(whiteName)) {
                playerData.put(whiteName, new Player(whiteName)); // Index 0 for Wins, 1 for Draws, 2 for Losses, 3 for Number of games
            }
            if (!playerData.containsKey(blackName)) {
                playerData.put(blackName, new Player(blackName)); // Index 0 for Wins, 1 for Draws, 2 for Losses, 3 for Number of games
            }
            if (game.getResult() == GameResult.WHITE_WON) {
                playerData.get(whiteName).incrementGamesWon(); // Incrementing wins for white player
                playerData.get(blackName).incrementGamesLost(); // Incrementing losses for black player
            }
            if (game.getResult() == GameResult.DRAW) {
                playerData.get(whiteName).incrementGamesDrawn(); // Incrementing draws for white player
                playerData.get(blackName).incrementGamesDrawn(); // Incrementing draws for black player
            }
            if (game.getResult() == GameResult.BLACK_WON) {
                playerData.get(whiteName).incrementGamesLost(); // Incrementing losses for white player
                playerData.get(blackName).incrementGamesWon(); // Incrementing wins for black player
            }
            playerData.get(whiteName).incrementTotalGamesPlayed(); // Incrementing number of games for white player
            playerData.get(blackName).incrementTotalGamesPlayed(); // Incrementing number of games for black player
        }


        List<Player> players;
        players = new ArrayList<>(playerData.values().stream().toList());
        players.sort(Comparator.comparingInt(Player::getTotalGamesPlayed).reversed());
        return players;
    }

    private PgnHolder getPgnHolder(Long collectionId) {
        Collection col = collectionService.getCollectionById(collectionId);
        PgnHolder pgnHolder = new PgnHolder(col.getPgnPath());
        try {
            pgnHolder.loadPgn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return pgnHolder;
    }

    public List<Map<String, Object>> getSquareFrequencies(Long collectionId) {
        PgnHolder pgnHolder = getPgnHolder(collectionId);

        Map<String, Integer> frequencyMap = new HashMap<>();
        for (Game game : pgnHolder.getGames()) {
            List<String> moves;
            String halfMovesString = game.getHalfMoves().toString();
            if (halfMovesString.isEmpty()) continue;

            moves = Arrays.stream(game.getHalfMoves().toString().split(" ")).map(move->move.substring(2,4)).collect(Collectors.toList());
            for (String str : moves) {
                frequencyMap.put(str, frequencyMap.getOrDefault(str, 0) + 1);
            }
        }


        List<Map<String, Object>> resultList = new ArrayList<>();


        for (Character col : List.of('a','b','c','d','e','f','g','h')) {
            for (int row : List.of(1,2,3,4,5,6,7,8)) {
                int freq = frequencyMap.getOrDefault(col + String.valueOf(row), 0);
                resultList.add(Map.of("row", row,"col", col,"freq", freq));
            }
        }
        return resultList;
    }

    public Object[][] getOpenings(Long collectionId) {
        PgnHolder pgnHolder = getPgnHolder(collectionId);

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
        Arrays.sort(data, 1, data.length, (o1, o2) -> {
            int games1 = (int) o1[4];
            int games2 = (int) o2[4];
            return games2 - games1; // Sort in descending order of number of games
        });

        // Extracting the top 6 entries
        Object[][] topEntries = Arrays.copyOfRange(data, 0, Math.min(7, data.length));
        return topEntries;
    }

    public Object[][] getMoveCount(Long collectionId) {
        PgnHolder pgnHolder = getPgnHolder(collectionId);

        Object[][] data = new Object[pgnHolder.getGames().size() + 1][1];
        data[0] = new Object[]{"# of Moves"};

        int index = 1;
        for (Game game : pgnHolder.getGames()) {
            data[index][0] = Integer.parseInt(game.getPlyCount());
            index++;
        }
        return data;
    }

    public Object[][] getTermination(Long collectionId) {
        PgnHolder pgnHolder = getPgnHolder(collectionId);

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
        return data;
    }

    public Object[][] getEvents(Long collectionId) {
        PgnHolder pgnHolder = getPgnHolder(collectionId);

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
        return populateTable(eventsData, data);
    }

    public Object[][] getSites(Long collectionId) {
        PgnHolder pgnHolder = getPgnHolder(collectionId);

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
        return populateTable(sitesData, data);
    }

    private Object[][] populateTable(Map<String, Integer> mapData, Object[][] dataTable) {
        int index = 1;
        for (Map.Entry<String, Integer> entry : mapData.entrySet()) {
            dataTable[index][0] = entry.getKey();
            dataTable[index][1] = entry.getValue();
            index++;
        }
        return dataTable;
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

    public Object[][] getTimeControls(Long collectionId) {
        PgnHolder pgnHolder = getPgnHolder(collectionId);

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
        return populateTable(timeData, data);
    }

    public Object[][] getDates(Long collectionId) {
        PgnHolder pgnHolder = getPgnHolder(collectionId);

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
        return populateTable(datesData, data);
    }
}

package com.chessdbstats.chessdbstats.service;

import com.chessdbstats.chessdbstats.model.Collection;
import com.chessdbstats.chessdbstats.model.Player;
import com.github.bhlangonijr.chesslib.game.Game;
import com.github.bhlangonijr.chesslib.game.GameResult;
import com.github.bhlangonijr.chesslib.pgn.PgnHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatsService {
    @Autowired
    CollectionService collectionService;
    public List<Player> getPlayers(Long collectionId) {
        Collection col = collectionService.getCollectionById(collectionId);
        PgnHolder pgnHolder = new PgnHolder(col.getPgnPath());
        try {
            pgnHolder.loadPgn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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

    public List<Map<String, Object>> getSquareFrequencies(Long collectionId) {
        Collection col1 = collectionService.getCollectionById(collectionId);
        PgnHolder pgnHolder = new PgnHolder(col1.getPgnPath());
        try {
            pgnHolder.loadPgn();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

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
}

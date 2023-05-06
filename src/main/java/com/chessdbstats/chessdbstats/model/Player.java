package com.chessdbstats.chessdbstats.model;

public class Player {
    public Player(String name, Integer gamesWon, Integer gamesLost, Integer gamesDrawn, Integer totalGamesPlayed) {
        this.name = name;
        this.gamesWon = gamesWon;
        this.gamesLost = gamesLost;
        this.gamesDrawn = gamesDrawn;
        this.totalGamesPlayed = totalGamesPlayed;
    }

    private String name;
    private Integer gamesWon;
    private Integer gamesLost;
    private Integer gamesDrawn;
    private Integer totalGamesPlayed;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getGamesWon() {
        return gamesWon;
    }

    public void setGamesWon(Integer gamesWon) {
        this.gamesWon = gamesWon;
    }

    public Integer getGamesLost() {
        return gamesLost;
    }

    public void setGamesLost(Integer gamesLost) {
        this.gamesLost = gamesLost;
    }

    public Integer getGamesDrawn() {
        return gamesDrawn;
    }

    public void setGamesDrawn(Integer gamesDrawn) {
        this.gamesDrawn = gamesDrawn;
    }

    public Integer getTotalGamesPlayed() {
        return totalGamesPlayed;
    }

    public void setTotalGamesPlayed(Integer totalGamesPlayed) {
        this.totalGamesPlayed = totalGamesPlayed;
    }
}

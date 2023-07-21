package com.chessdbstats.chessdbstats.model;

public class Player {

    public Player(String name) {
        this.name = name;
        this.gamesWon = 0;
        this.gamesLost = 0;
        this.gamesDrawn = 0;
        this.totalGamesPlayed = 0;
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

    public void incrementGamesWon() {
        setGamesWon(getGamesWon() + 1);
    }
    public void incrementGamesLost() {
        setGamesLost(getGamesLost() + 1);
    }
    public void incrementGamesDrawn() {
        setGamesDrawn(getGamesDrawn() + 1);
    }
    public void incrementTotalGamesPlayed() {
        setTotalGamesPlayed(getTotalGamesPlayed() + 1);
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

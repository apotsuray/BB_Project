package ru.bets.project.smallbets.algorithms;

public class CheckingMatch {
    public String tournamentName, matchName;
    public Double value, odds;
    public int lifetime;
    public CheckingMatch(String tName, String mName, Double value, Double odds) {
        this.tournamentName = tName;
        this.matchName = mName;
        this.value = value;
        this.odds = odds;
        lifetime = 300;
    }
}

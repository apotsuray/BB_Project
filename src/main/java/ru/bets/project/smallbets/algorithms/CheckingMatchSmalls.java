package ru.bets.project.smallbets.algorithms;

public class CheckingMatchSmalls {
    public String tournamentName, matchName;
    public Double mean, advantage;
    public Double startTotal;
    public int lifetime;
    public CheckingMatchSmalls(String tName, String mName,Double mean, Double advantage,Double startTotal)
    {
        this.tournamentName = tName;
        this.matchName = mName;
        this.mean = mean;
        this.advantage = advantage;
        lifetime = 300;
        this.startTotal = startTotal;
    }
}

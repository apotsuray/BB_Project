package ru.bets.project.matches;

import ru.bets.project.utils.Index;

import java.util.List;

public abstract class Match {
    protected String teamHome;
    protected String teamAway;
    protected String league;
    protected double chet, nechet;
    protected List<Index> handicapHome;
    protected List<Index> handicapAway;
    protected List<Index> totalOver;
    protected List<Index> totalUnder;

    public String getTeamHome() {
        return teamHome;
    }

    public void setTeamHome(String teamHome) {
        this.teamHome = teamHome;
    }

    public String getTeamAway() {
        return teamAway;
    }

    public void setTeamAway(String teamAway) {
        this.teamAway = teamAway;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public double getChet() {
        return chet;
    }

    public void setChet(double chet) {
        this.chet = chet;
    }

    public double getNechet() {
        return nechet;
    }

    public void setNechet(double nechet) {
        this.nechet = nechet;
    }

    public List<Index> getHandicapHome() {
        return handicapHome;
    }

    public void setHandicapHome(List<Index> handicapHome) {
        this.handicapHome = handicapHome;
    }

    public List<Index> getHandicapAway() {
        return handicapAway;
    }

    public void setHandicapAway(List<Index> handicapAway) {
        this.handicapAway = handicapAway;
    }

    public List<Index> getTotalOver() {
        return totalOver;
    }

    public void setTotalOver(List<Index> totalOver) {
        this.totalOver = totalOver;
    }

    public List<Index> getTotalUnder() {
        return totalUnder;
    }

    public void setTotalUnder(List<Index> totalUnder) {
        this.totalUnder = totalUnder;
    }
}

package ru.bets.project.smallbets.fonmatch;

import java.util.ArrayList;
import java.util.List;

public class FonTournament {
    private String name;
    private List<FonMatch> matches;
    private boolean haveSmall;

    public FonTournament(String name) {
        this.name = name;
        haveSmall = false;
        matches = new ArrayList<>();
    }
    public void addMatch(String matchName, String time) {
        matches.add(new FonMatch(this.name,matchName,time));
    }

    public boolean isHaveSmall() {
        return haveSmall;
    }

    public void setHaveSmall(boolean haveSmall) {
        this.haveSmall = haveSmall;
    }

    public String getName() {
        return name;
    }

    public List<FonMatch> getMatches() {
        return matches;
    }
}

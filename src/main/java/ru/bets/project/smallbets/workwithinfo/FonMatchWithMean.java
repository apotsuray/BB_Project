package ru.bets.project.smallbets.workwithinfo;

import ru.bets.project.smallbets.SharedState;
import ru.bets.project.smallbets.fonmatch.FonMatch;

import java.util.Set;

public class FonMatchWithMean {
    protected String nameTournament;
    protected String nameMatch;
    protected double mean;
    protected FonMatch match;
    protected SharedState sharedState;
    public FonMatchWithMean(FonMatch match, SharedState sharedState) {
        this.nameTournament = match.getNameTournament();
        this.nameMatch = match.getName();
        this.match = match;
        this.sharedState = sharedState;
    }
    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }
}

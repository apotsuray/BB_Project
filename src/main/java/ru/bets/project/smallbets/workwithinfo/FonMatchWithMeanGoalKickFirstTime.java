package ru.bets.project.smallbets.workwithinfo;

import ru.bets.project.smallbets.SharedState;
import ru.bets.project.smallbets.algorithms.AlgorithmGoalKickOver;
import ru.bets.project.smallbets.algorithms.AlgorithmGoalKickOverHalf;
import ru.bets.project.smallbets.algorithms.AlgorithmGoalKickUnderHalf;
import ru.bets.project.smallbets.algorithms.CheckingMatchSmalls;
import ru.bets.project.smallbets.fonmatch.FonMatch;

import java.util.Objects;
import java.util.Set;

public class FonMatchWithMeanGoalKickFirstTime extends FonMatchWithMean implements Runnable {

    private Set<FonMatchWithMeanGoalKickFirstTime> mathesWithGoalKickFirstTime;

    public FonMatchWithMeanGoalKickFirstTime(FonMatch match, SharedState sharedState, Set<FonMatchWithMeanGoalKickFirstTime> mathesWithGoalKickFirstTime) {
        super(match,sharedState);
        this.mathesWithGoalKickFirstTime = mathesWithGoalKickFirstTime;
    }

    @Override
    public void run() {
        double currentTime = match.getDoubleTime();
        double sumScore = match.getGoalKick1stTime().getScore1() + match.getGoalKick1stTime().getScore2();
        double value = match.getGoalKick1stTime().getTotal().get(1).value - sumScore;

        if (match.getGoalKick1stTime().getTotal().get(1).odds >= 1.85) {
            mean = (45 - currentTime) / (value + 0.5);
        } else {
            mean = (45 - currentTime) / (value);
        }
        mathesWithGoalKickFirstTime.add(this);
        try {
            AlgorithmGoalKickUnderHalf.getCheckingMatches().add(new CheckingMatchSmalls(nameTournament, nameMatch, mean, 3.0, value));
        } catch (Exception e) {
        }
        value = match.getGoalKick1stTime().getTotal().get(0).value - sumScore;
        if (match.getGoalKick1stTime().getTotal().get(0).odds >= 1.85) {
            mean = (45 - currentTime) / (value - 0.5);
        } else {
            mean = (45 - currentTime) / value;
        }
        try {
            AlgorithmGoalKickOverHalf.getCheckingMatches().add(new CheckingMatchSmalls(nameTournament, nameMatch, mean, 3.5,  value));
        } catch (Exception e) {
        }
        sharedState.decrementActiveWorkWithInfoThreads();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FonMatchWithMeanGoalKickFirstTime that = (FonMatchWithMeanGoalKickFirstTime) o;
        return Objects.equals(nameMatch, that.nameMatch);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nameMatch);
    }
}

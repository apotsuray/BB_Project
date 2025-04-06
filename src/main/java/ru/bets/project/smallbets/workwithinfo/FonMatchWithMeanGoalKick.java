package ru.bets.project.smallbets.workwithinfo;

import ru.bets.project.smallbets.SharedState;
import ru.bets.project.smallbets.algorithms.AlgorithmGoalKickOver;
import ru.bets.project.smallbets.algorithms.AlgorithmGoalKickUnder;
import ru.bets.project.smallbets.algorithms.CheckingMatchSmalls;
import ru.bets.project.smallbets.fonmatch.FonMatch;

import java.util.Objects;
import java.util.Set;

public class FonMatchWithMeanGoalKick extends FonMatchWithMean implements Runnable {

    private Set<FonMatchWithMeanGoalKick> mathesWithGoalKick;
    public FonMatchWithMeanGoalKick(FonMatch match, SharedState sharedState, Set<FonMatchWithMeanGoalKick> mathesWithGoalKick) {
        super(match,sharedState);
        this.mathesWithGoalKick = mathesWithGoalKick;
    }
    @Override
    public void run() {
        double sumScore = match.getGoalKick().getScore1() + match.getGoalKick().getScore2();
        double value = match.getGoalKick().getTotal().get(1).value - sumScore;

        if (match.getGoalKick().getTotal().get(1).odds >= 1.85) {
            mean = 45 / (value + 0.5);
        } else {
            mean = 45 / value;
        }
        mathesWithGoalKick.add(this);
        try {
            AlgorithmGoalKickUnder.getCheckingMatches().add(new CheckingMatchSmalls(nameTournament, nameMatch, mean, 3.0, match.getGoalKick().getTotal().get(1).value));
        } catch (Exception e) {
        }
        value = match.getGoalKick().getTotal().get(0).value - sumScore;
        if (match.getGoalKick().getTotal().get(0).odds >= 1.85) {
            mean = 45 / (value - 0.5);
        } else {
            mean = 45 / value;
        }
        try {
            AlgorithmGoalKickOver.getCheckingMatches().add(new CheckingMatchSmalls(nameTournament, nameMatch, mean, 3.5, match.getGoalKick().getTotal().get(0).value));
        } catch (Exception e) {
        }
        sharedState.decrementActiveWorkWithInfoThreads();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FonMatchWithMeanGoalKick that = (FonMatchWithMeanGoalKick) o;
        return Objects.equals(nameMatch, that.nameMatch);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nameMatch);
    }

}

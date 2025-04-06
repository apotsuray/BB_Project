package ru.bets.project.smallbets.workwithinfo;

import ru.bets.project.smallbets.SharedState;
import ru.bets.project.smallbets.algorithms.AlgorithmThrowInOver;
import ru.bets.project.smallbets.algorithms.AlgorithmThrowInUnder;
import ru.bets.project.smallbets.algorithms.CheckingMatchSmalls;
import ru.bets.project.smallbets.fonmatch.FonMatch;

import java.util.Objects;
import java.util.Set;

public class FonMatchWithMeanThrowIn extends FonMatchWithMean implements Runnable {

    private Set<FonMatchWithMeanThrowIn> mathesWithThrowIn;

    public FonMatchWithMeanThrowIn(FonMatch match, SharedState sharedState, Set<FonMatchWithMeanThrowIn> mathesWithThrowIn) {
        super(match, sharedState);
        this.mathesWithThrowIn = mathesWithThrowIn;
    }

    @Override
    public void run() {
        double sumScore = match.getThrowIn().getScore1() + match.getThrowIn().getScore2();
        double value = match.getThrowIn().getTotal().get(1).value - sumScore;

        if (match.getThrowIn().getTotal().get(1).odds >= 1.85) {
            mean = 45 / (value + 0.5);
        } else {
            mean = 45 / value;
        }
        mathesWithThrowIn.add(this);
        try {
            AlgorithmThrowInUnder.getCheckingMatches().add(new CheckingMatchSmalls(nameTournament, nameMatch, mean, 5.0, match.getThrowIn().getTotal().get(1).value));
        } catch (Exception e) {
        }
        value = match.getThrowIn().getTotal().get(0).value - sumScore;
        if (match.getThrowIn().getTotal().get(0).odds >= 1.85) {
            mean = 45 / (value - 0.5);
        } else {
            mean = 45 / value;
        }
        try {
            AlgorithmThrowInOver.getCheckingMatches().add(new CheckingMatchSmalls(nameTournament, nameMatch, mean, 4.5, match.getThrowIn().getTotal().get(0).value));
        } catch (Exception e) {
        }
        sharedState.decrementActiveWorkWithInfoThreads();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FonMatchWithMeanThrowIn that = (FonMatchWithMeanThrowIn) o;
        return Objects.equals(nameMatch, that.nameMatch);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nameMatch);
    }

}

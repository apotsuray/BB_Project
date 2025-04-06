package ru.bets.project.smallbets.workwithinfo;

import ru.bets.project.smallbets.SharedState;
import ru.bets.project.smallbets.algorithms.AlgorithmFoulMore;
import ru.bets.project.smallbets.algorithms.AlgorithmFoulMoreHalf;
import ru.bets.project.smallbets.algorithms.AlgorithmFoulUnderHalf;
import ru.bets.project.smallbets.algorithms.CheckingMatchSmalls;
import ru.bets.project.smallbets.fonmatch.FonMatch;

import java.util.Objects;
import java.util.Set;

public class FonMatchWithMeanFoulFirstTime extends FonMatchWithMean implements Runnable {

    private Set<FonMatchWithMeanFoulFirstTime> mathesWithFoulFirstTime;

    public FonMatchWithMeanFoulFirstTime(FonMatch match, SharedState sharedState, Set<FonMatchWithMeanFoulFirstTime> mathesWithFoulFirstTime) {
        super(match, sharedState);
        this.mathesWithFoulFirstTime = mathesWithFoulFirstTime;
    }

    @Override
    public void run() {
        double currentTime = match.getDoubleTime();
        double sumScore = match.getFoul1stTime().getScore1() + match.getFoul1stTime().getScore2();
        double value = match.getFoul1stTime().getTotal().get(1).value - sumScore;
        if (match.getFoul1stTime().getTotal().get(1).odds >= 1.85) {
            mean = (45 - currentTime) / (value + 0.5);
        } else {
            mean = (45 - currentTime) / value;
        }
        mathesWithFoulFirstTime.add(this);
        try {
            AlgorithmFoulUnderHalf.getCheckingMatches().add(new CheckingMatchSmalls(nameTournament, nameMatch, mean, 4.0, value));
        } catch (Exception e) {
        }
        value = match.getFoul1stTime().getTotal().get(0).value - sumScore;
        if (match.getFoul1stTime().getTotal().get(0).odds >= 1.85) {
            mean = (45 - currentTime) / (value - 0.5);
        } else {
            mean = (45 - currentTime) / value;
        }

        try {
            AlgorithmFoulMoreHalf.getCheckingMatches().add(new CheckingMatchSmalls(nameTournament, nameMatch, mean, 3.5, value));
        } catch (Exception e) {
        }

        sharedState.decrementActiveWorkWithInfoThreads();

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FonMatchWithMeanFoulFirstTime that = (FonMatchWithMeanFoulFirstTime) o;
        return Objects.equals(nameMatch, that.nameMatch);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nameMatch);
    }
}

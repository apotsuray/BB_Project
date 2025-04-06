package ru.bets.project.smallbets.workwithinfo;

import ru.bets.project.smallbets.SharedState;
import ru.bets.project.smallbets.algorithms.AlgorithmThrowInOver;
import ru.bets.project.smallbets.algorithms.AlgorithmThrowInOverHalf;
import ru.bets.project.smallbets.algorithms.AlgorithmThrowInUnderHalf;
import ru.bets.project.smallbets.algorithms.CheckingMatchSmalls;
import ru.bets.project.smallbets.fonmatch.FonMatch;

import java.util.Objects;
import java.util.Set;

public class FonMatchWithMeanThrowInFirstTime extends FonMatchWithMean implements Runnable {
    private Set<FonMatchWithMeanThrowInFirstTime> mathesWithThrowInFirstTime;

    public FonMatchWithMeanThrowInFirstTime(FonMatch match, SharedState sharedState, Set<FonMatchWithMeanThrowInFirstTime> mathesWithThrowInFirstTime) {
        super(match, sharedState);
        this.mathesWithThrowInFirstTime = mathesWithThrowInFirstTime;
    }

    @Override
    public void run() {
        double currentTime = match.getDoubleTime();
        double sumScore = match.getThrowIn1stTime().getScore1() + match.getThrowIn1stTime().getScore2();
        double value = match.getThrowIn1stTime().getTotal().get(1).value - sumScore;

        if (match.getThrowIn1stTime().getTotal().get(1).odds >= 1.85) {
            mean = (45 - currentTime) / (value + 0.5);
        } else {
            mean = (45 - currentTime) / value;
        }
        mathesWithThrowInFirstTime.add(this);
        try {
            AlgorithmThrowInUnderHalf.getCheckingMatches().add(new CheckingMatchSmalls(nameTournament, nameMatch, mean, 5.0, value));
        } catch (Exception e) {
        }
        value = match.getThrowIn1stTime().getTotal().get(0).value - sumScore;

        if (match.getThrowIn1stTime().getTotal().get(0).odds >= 1.85) {
            mean = (45 - currentTime) / (value - 0.5);
        } else {
            mean = (45 - currentTime) / value;
        }
        try {
            AlgorithmThrowInOverHalf.getCheckingMatches().add(new CheckingMatchSmalls(nameTournament, nameMatch, mean, 4.5, match.getThrowIn1stTime().getTotal().get(0).value - (match.getThrowIn1stTime().getScore1() + match.getThrowIn1stTime().getScore2())));
        } catch (Exception e) {
        }
        sharedState.decrementActiveWorkWithInfoThreads();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FonMatchWithMeanThrowInFirstTime that = (FonMatchWithMeanThrowInFirstTime) o;
        return Objects.equals(nameMatch, that.nameMatch);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nameMatch);
    }

}

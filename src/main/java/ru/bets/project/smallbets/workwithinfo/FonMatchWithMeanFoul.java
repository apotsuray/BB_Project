package ru.bets.project.smallbets.workwithinfo;

import ru.bets.project.smallbets.SharedState;
import ru.bets.project.smallbets.algorithms.AlgorithmFoulMore;
import ru.bets.project.smallbets.algorithms.AlgorithmFoulUnder;
import ru.bets.project.smallbets.algorithms.CheckingMatchSmalls;
import ru.bets.project.smallbets.fonmatch.FonMatch;

import java.util.Objects;
import java.util.Set;

public class FonMatchWithMeanFoul extends FonMatchWithMean implements Runnable {

    private Set<FonMatchWithMeanFoul> mathesWithFoul;

    public FonMatchWithMeanFoul(FonMatch match, SharedState sharedState, Set<FonMatchWithMeanFoul> mathesWithFoul) {
        super(match,sharedState);
        this.mathesWithFoul = mathesWithFoul;
    }
    @Override
    public void run() {
        double sumScore = match.getFoul().getScore1() + match.getFoul().getScore2();
        double value = match.getFoul().getTotal().get(1).value - sumScore;

        if (match.getFoul().getTotal().get(1).odds >= 1.85) {
            mean = 45 / (value + 0.5);
        } else {
            mean = 45 / value;
        }
        mathesWithFoul.add(this);
        try {
            AlgorithmFoulUnder.getCheckingMatches().add(new CheckingMatchSmalls(nameTournament, nameMatch, mean, 4.0, match.getFoul().getTotal().get(1).value));
        } catch (Exception e) {
        }
        value = match.getFoul().getTotal().get(0).value - sumScore;
        if (match.getFoul().getTotal().get(0).odds >= 1.85) {
            mean = 45 / (value - 0.5);
        } else {
            mean = 45 / value;
        }
        try {
            AlgorithmFoulMore.getCheckingMatches().add(new CheckingMatchSmalls(nameTournament, nameMatch, mean, 3.5, match.getFoul().getTotal().get(1).value));
        } catch (Exception e) {
        }
        sharedState.decrementActiveWorkWithInfoThreads();
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FonMatchWithMeanFoul that = (FonMatchWithMeanFoul) o;
        return Objects.equals(nameMatch, that.nameMatch);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(nameMatch);
    }

}

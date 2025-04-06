package ru.bets.project.smallbets.adderfilter;

import ru.bets.project.smallbets.SharedState;
import ru.bets.project.smallbets.algorithms.AlgorithmCardMoreHalf;
import ru.bets.project.smallbets.algorithms.CheckingMatch;

public class AddFilterOverHalf extends AddFilter implements Runnable{

    public AddFilterOverHalf(SharedState sharedState, String tname, String mname, double value, double odd) {
        this.sharedState = sharedState;
        this.tname = tname;
        this.mname = mname;
        this.value = value;
        this.odd = odd;
    }
    @Override
    public void run() {
        try {
            sharedState.waitForAlgorithmThreads();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        AlgorithmCardMoreHalf.getCheckingMatches().add(new CheckingMatch(tname, mname, value, odd));
    }
}

package ru.bets.project.smallbets.adderfilter;

import ru.bets.project.smallbets.SharedState;
import ru.bets.project.smallbets.algorithms.AlgorithmCardUnderHalf;
import ru.bets.project.smallbets.algorithms.CheckingMatch;

public class AddFilterUnderHalf extends AddFilter implements Runnable{

    public AddFilterUnderHalf(SharedState sharedState, String tname, String mname, double value, double odd) {
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
        AlgorithmCardUnderHalf.getCheckingMatches().add(new CheckingMatch(tname, mname, value, odd));
    }
}

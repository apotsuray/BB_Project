package ru.bets.project.fonbet;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bets.project.smallbets.Parser;
import ru.bets.project.smallbets.SharedState;
import ru.bets.project.smallbets.algorithms.*;
import ru.bets.project.telegram.Telegram;

@Component
public class SmallBet {
    private final Telegram telegram;
    @Autowired
    public SmallBet(Telegram telegram) {
        this.telegram = telegram;
    }

    public void execute() {
        SharedState sharedState = new SharedState(telegram);
        new Thread(new Parser(sharedState)).start();
        int i = 0;
        new Thread(new AlgorithmCardMoreHalf(sharedState, i++)).start();
        new Thread(new AlgorithmCardOver(sharedState, i++)).start();
        new Thread(new AlgorithmCardUnder(sharedState, i++)).start();
        new Thread(new AlgorithmCardUnderHalf(sharedState, i++)).start();
        new Thread(new AlgorithmFoulMore(sharedState, i++)).start();
        new Thread(new AlgorithmFoulMoreHalf(sharedState, i++)).start();
        new Thread(new AlgorithmFoulUnder(sharedState, i++)).start();
        new Thread(new AlgorithmFoulUnderHalf(sharedState, i++)).start();
        new Thread(new AlgorithmGoalKickOver(sharedState, i++)).start();
        new Thread(new AlgorithmGoalKickOverHalf(sharedState, i++)).start();
        new Thread(new AlgorithmGoalKickUnder(sharedState, i++)).start();
        new Thread(new AlgorithmGoalKickUnderHalf(sharedState, i++)).start();
        new Thread(new AlgorithmThrowInOver(sharedState, i++)).start();
        new Thread(new AlgorithmThrowInOverHalf(sharedState, i++)).start();
        new Thread(new AlgorithmThrowInUnder(sharedState, i++)).start();
        new Thread(new AlgorithmThrowInUnderHalf(sharedState, i++)).start();
    }
}

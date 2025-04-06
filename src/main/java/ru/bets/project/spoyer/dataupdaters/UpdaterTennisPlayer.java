package ru.bets.project.spoyer.dataupdaters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class UpdaterTennisPlayer extends Thread{
    private final UpdaterId updaterId;
    private final UpdaterLeagues updaterLeagues;
    private final UpdaterNameOn1xBet updaterNameOn1xBet;
    private final UpdaterNameOnBet365 updaterNameOnBet365;
    private final List<Updatable> updaters;
    @Autowired
    public UpdaterTennisPlayer(UpdaterId updaterId, UpdaterLeagues updaterLeagues, UpdaterNameOn1xBet updaterNameOn1xBet, UpdaterNameOnBet365 updaterNameOnBet365) {
        this.updaterId = updaterId;
        this.updaterLeagues = updaterLeagues;
        this.updaterNameOn1xBet = updaterNameOn1xBet;
        this.updaterNameOnBet365 = updaterNameOnBet365;
        updaters = new ArrayList<>();
    }

    @Override
    public void run() {
        updaters.add(updaterId);
        updaters.add(updaterLeagues);
        updaters.add(updaterNameOn1xBet);
        updaters.add(updaterNameOnBet365);
        for (Updatable updatable : updaters) {
            updatable.update();
        }
    }
}

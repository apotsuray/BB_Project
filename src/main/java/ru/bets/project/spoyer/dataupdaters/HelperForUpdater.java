package ru.bets.project.spoyer.dataupdaters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bets.project.models.spoyermodels.TennisPlayer;
import ru.bets.project.services.spoyerservices.TennisPlayerService;
import ru.bets.project.utils.Pair;

import java.util.ArrayList;
import java.util.List;
@Component
public class HelperForUpdater {
    private final TennisPlayerService tennisPlayerService;
    @Autowired
    public HelperForUpdater(TennisPlayerService tennisPlayerService) {
        this.tennisPlayerService = tennisPlayerService;
    }

    public List<Pair> helperForAddNameSpoyer()
    {
        List<Pair> listName = new ArrayList<>();
        List<TennisPlayer> tennisPlayers = tennisPlayerService.findALl();
        for (TennisPlayer tennisPlayer : tennisPlayers){
            if(tennisPlayer.getNameOnSpoyer()!=null)
            {
                listName.add(new Pair(tennisPlayer.getNameOnBet365(), tennisPlayer.getNameOnSpoyer()));
            }else {
                listName.add(new Pair(tennisPlayer.getNameOnBet365(), "xxx"));
            }
        }
        return listName;
    }
}

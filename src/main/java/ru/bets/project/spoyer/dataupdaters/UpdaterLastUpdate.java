package ru.bets.project.spoyer.dataupdaters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bets.project.models.spoyermodels.TennisPlayer;
import ru.bets.project.services.spoyerservices.TennisPlayerService;

import java.time.LocalDate;
@Component
public class UpdaterLastUpdate {
    private final TennisPlayerService tennisPlayerService;

    @Autowired
    public UpdaterLastUpdate(TennisPlayerService tennisPlayerService) {
        this.tennisPlayerService = tennisPlayerService;
    }

    public void updateLastUpdate(int idOnSpoyer, LocalDate newLastUpdate) {
        TennisPlayer tennisPlayer = tennisPlayerService.getByIdOnSpoyer(idOnSpoyer);
        tennisPlayerService.updateLastUpdate(tennisPlayer, newLastUpdate);
    }
}

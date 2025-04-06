package ru.bets.project.spoyer.assist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bets.project.models.spoyermodels.Leagues;
import ru.bets.project.models.spoyermodels.WhoServeFirst;
import ru.bets.project.services.spoyerservices.LeaguesService;
import ru.bets.project.services.spoyerservices.WhoServeFirstService;

import java.time.LocalDate;

@Component
public class AdderFirstServer {
    private final LeaguesService leaguesService;
    private final WhoServeFirstService whoServeFirstService;

    @Autowired
    public AdderFirstServer(LeaguesService leaguesService, WhoServeFirstService whoServeFirstService) {
        this.leaguesService = leaguesService;
        this.whoServeFirstService = whoServeFirstService;
    }

    public void addWhoServeFirst(int idGame, String nameHome, String nameAway, String server,
                                 LocalDate date, int idSpoyer, String nameOnSpoyer, String court) {
        leaguesService.save(new Leagues(nameOnSpoyer, court, idSpoyer));
        whoServeFirstService.save(new WhoServeFirst(idGame, nameHome, nameAway, server, date, idSpoyer));
    }
}

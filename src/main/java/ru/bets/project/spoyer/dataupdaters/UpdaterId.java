package ru.bets.project.spoyer.dataupdaters;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bets.project.models.spoyermodels.TennisPlayer;
import ru.bets.project.services.spoyerservices.TennisPlayerService;
import ru.bets.project.spoyer.assist.JsonNodeBuilder;
import ru.bets.project.utils.Pair;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
@Component
public class UpdaterId implements Updatable{
    private final TennisPlayerService tennisPlayerService;
    private final HelperForUpdater helperForUpdater;
    private final JsonNodeBuilder jsonNodeBuilder;
    @Autowired
    public UpdaterId(TennisPlayerService tennisPlayerService, HelperForUpdater helperForUpdater, JsonNodeBuilder jsonNodeBuilder) {
        this.tennisPlayerService = tennisPlayerService;
        this.helperForUpdater = helperForUpdater;
        this.jsonNodeBuilder = jsonNodeBuilder;
    }
    public void update() {

        List<Pair> listNamesov = helperForUpdater.helperForAddNameSpoyer();
        JsonNode jsonNodeBet365;
        JsonNode jsonNodeSpoyer;
        try {
            jsonNodeBet365 = jsonNodeBuilder.getElements("task=pre&bookmaker=bet365&sport=tennis");
            jsonNodeSpoyer = jsonNodeBuilder.getElements("task=predata&sport=tennis&day=today");
        } catch (IOException | InterruptedException e) {
            return;
        }

        JsonNode gamesPre365 = jsonNodeBet365.get("games_pre");
        JsonNode gamesPreSpoyer = jsonNodeSpoyer.get("games_pre");

        Iterator<JsonNode> elementsBet365 = gamesPre365.elements();
        while (elementsBet365.hasNext()) {
            JsonNode game = elementsBet365.next();
            boolean haveHome = false;
            boolean haveAway = false;
            String home365 = game.get("home").asText();
            String away365 = game.get("away").asText();
            String homeSpoyer = "", awaySpoyer = "";
            int idHomeSpoyer = -1, idAwaySpoyer = -1;
            for (Pair value : listNamesov) {
                if (home365.equals(value.getNameOnBet365())) {
                    if (!value.getNameOnSpoyer().equals("xxx")) {
                        homeSpoyer = value.getNameOnSpoyer();
                        haveHome = true;
                    }
                    break;
                }
            }
            for (Pair pair : listNamesov) {
                if (away365.equals(pair.getNameOnBet365())) {
                    if (!pair.getNameOnSpoyer().equals("xxx")) {
                        awaySpoyer = pair.getNameOnSpoyer();
                        haveAway = true;
                    }
                    break;
                }
            }
            if(haveHome ^ haveAway){
                if (haveHome) {
                    for (JsonNode gamePreSpoyer : gamesPreSpoyer) {
                        if (homeSpoyer.equals(gamePreSpoyer.get("home").get("name").asText())) {
                            awaySpoyer = gamePreSpoyer.get("away").get("name").asText();
                            idAwaySpoyer = gamePreSpoyer.get("away").get("id").asInt();
                            break;
                        }
                    }
                } else {
                    for (JsonNode gamePreSpoyer : gamesPreSpoyer) {
                        if (awaySpoyer.equals(gamePreSpoyer.get("away").get("name").asText())) {
                            homeSpoyer = gamePreSpoyer.get("home").get("name").asText();
                            idHomeSpoyer = gamePreSpoyer.get("home").get("id").asInt();
                            break;
                        }
                    }
                }
                if (!homeSpoyer.isEmpty() && !awaySpoyer.isEmpty()) {

                    if (haveHome) {
                        updateIdOnSpoyer(away365, awaySpoyer, idAwaySpoyer);
                    } else {
                        updateIdOnSpoyer(home365, homeSpoyer, idHomeSpoyer);
                    }

                }
            }
        }
    }
    private void updateIdOnSpoyer(String name365,String nameOnSpoyer,int idOnSpoyer) {
        TennisPlayer tennisPlayer = tennisPlayerService.getByNameOnBet365(name365);
        tennisPlayerService.updateIdOnSpoyer(tennisPlayer, nameOnSpoyer,idOnSpoyer);
    }

}

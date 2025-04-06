package ru.bets.project.spoyer.dataupdaters;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bets.project.models.spoyermodels.Leagues;
import ru.bets.project.services.spoyerservices.LeaguesService;
import ru.bets.project.spoyer.assist.JsonNodeBuilder;
import ru.bets.project.utils.Pair;

import java.io.IOException;
import java.util.*;

@Component
public class UpdaterLeagues implements Updatable{
    private final HelperForUpdater helperForUpdater;
    private final LeaguesService leaguesService;
    private final JsonNodeBuilder jsonNodeBuilder;
    @Autowired
    public UpdaterLeagues(HelperForUpdater helperForUpdater, LeaguesService leaguesService, JsonNodeBuilder jsonNodeBuilder) {
        this.helperForUpdater = helperForUpdater;
        this.leaguesService = leaguesService;
        this.jsonNodeBuilder = jsonNodeBuilder;
    }

    public void update() {
        List<Pair> listNames = helperForUpdater.helperForAddNameSpoyer();
        Set<String> viewed = new HashSet<>();
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
            String league365 = game.get("league").asText();
            String leagueSpoyer = "";
            String homeSpoyer = "", awaySpoyer = "";
            int idHomeSpoyer = -1, idAwaySpoyer = -1;
            for (Pair listName : listNames) {
                if (home365.equals(listName.getNameOnBet365())) {
                    if (!listName.getNameOnSpoyer().equals("xxx")) {
                        homeSpoyer = listName.getNameOnSpoyer();
                        haveHome = true;
                    }
                    break;
                }
            }
            for (Pair listName : listNames) {
                if (away365.equals(listName.getNameOnBet365())) {
                    if (!listName.getNameOnSpoyer().equals("xxx")) {
                        awaySpoyer = listName.getNameOnSpoyer();
                        haveAway = true;
                    }
                    break;
                }
            }
            if (haveHome) {
                for (JsonNode gamePreSpoyer : gamesPreSpoyer) {
                    if (homeSpoyer.equals(gamePreSpoyer.get("home").get("name").asText())) {
                        awaySpoyer = gamePreSpoyer.get("away").get("name").asText();
                        idAwaySpoyer = gamePreSpoyer.get("away").get("id").asInt();
                        leagueSpoyer = gamePreSpoyer.get("league").get("name").asText();
                        break;
                    }
                }
            }
            if (haveAway) {
                for (JsonNode gamePreSpoyer : gamesPreSpoyer) {
                    if (awaySpoyer.equals(gamePreSpoyer.get("away").get("name").asText())) {
                        homeSpoyer = gamePreSpoyer.get("home").get("name").asText();
                        idHomeSpoyer = gamePreSpoyer.get("home").get("id").asInt();
                        leagueSpoyer = gamePreSpoyer.get("league").get("name").asText();
                        break;
                    }
                }
            }
            if (!viewed.contains(league365) && !league365.contains("ITF") && !league365.contains("WTA") && !league365.contains("Women")) {
                updateNameOnBet365(league365, leagueSpoyer);
                viewed.add(league365);
            }
        }
    }
    private void updateNameOnBet365(String league365,String leagueSpoyer) {
        Leagues league = leaguesService.getByNameOnSpoyer(leagueSpoyer);
        leaguesService.updateNameOnBet365(league,league365);

    }
}

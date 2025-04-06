package ru.bets.project.spoyer.dataupdaters;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bets.project.models.spoyermodels.TennisPlayer;
import ru.bets.project.services.spoyerservices.TennisPlayerService;
import ru.bets.project.spoyer.assist.JsonNodeBuilder;

import java.io.IOException;
import java.util.Iterator;
@Component
public class UpdaterNameOnBet365 implements Updatable{
    final private TennisPlayerService tennisPlayerService;
    private final JsonNodeBuilder jsonNodeBuilder;
    @Autowired
    public UpdaterNameOnBet365(TennisPlayerService tennisPlayerService, JsonNodeBuilder jsonNodeBuilder) {
        this.tennisPlayerService = tennisPlayerService;
        this.jsonNodeBuilder = jsonNodeBuilder;
    }
    public void update() {
        JsonNode jsonNodeSpoyer;
        try {
            jsonNodeSpoyer = jsonNodeBuilder.getElements("task=pre&bookmaker=bet365&sport=tennis");
        } catch (IOException | InterruptedException e) {
            return;
        }
        JsonNode gamesPre = jsonNodeSpoyer.get("games_pre");
        Iterator<JsonNode> elementsSpoyer = gamesPre.elements();
        while (elementsSpoyer.hasNext()) {
            JsonNode game = elementsSpoyer.next();
            String league = game.get("league").asText();
            String home = game.get("home").asText();
            String away = game.get("away").asText();
            if (!league.contains("MD") && !home.contains("/")) {
                tennisPlayerService.save(new TennisPlayer(home));
                tennisPlayerService.save(new TennisPlayer(away));
            }
        }
    }
}

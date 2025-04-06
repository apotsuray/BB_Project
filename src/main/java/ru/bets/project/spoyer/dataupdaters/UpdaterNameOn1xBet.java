package ru.bets.project.spoyer.dataupdaters;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bets.project.models.spoyermodels.TennisPlayer;
import ru.bets.project.services.spoyerservices.TennisPlayerService;
import ru.bets.project.spoyer.assist.JsonNodeBuilder;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

@Component
public class UpdaterNameOn1xBet implements Updatable {
    private final TennisPlayerService tennisPlayerService;
    private final JsonNodeBuilder jsonNodeBuilder;

    @Autowired
    public UpdaterNameOn1xBet(TennisPlayerService tennisPlayerService, JsonNodeBuilder jsonNodeBuilder) {
        this.tennisPlayerService = tennisPlayerService;
        this.jsonNodeBuilder = jsonNodeBuilder;
    }

    public void update() {
        List<String> listNames365 = tennisPlayerService.getListNames365();
        List<String> listNames1x = tennisPlayerService.getListNames1xbet();
        JsonNode jsonNodeSpoyer;
        JsonNode jsonNode1x;
        try {
            jsonNodeSpoyer = jsonNodeBuilder.getElements("task=pre&bookmaker=bet365&sport=tennis");
            jsonNode1x = jsonNodeBuilder.getElements("task=1xpreall");
        } catch (IOException | InterruptedException e) {
            return;
        }
        JsonNode gamesPre = jsonNodeSpoyer.get("games_pre");
        Iterator<JsonNode> elementsSpoyer = gamesPre.elements();

        while (elementsSpoyer.hasNext()) {
            JsonNode game = elementsSpoyer.next();
            boolean player1 = false;//если нет соответствия игроку в иксе - будет тру
            boolean player2 = false; //если нет соответствия игроку в иксе - будет тру
            String playerOn1X = "xxx";
            String league365 = game.get("league").asText();
            String home365 = game.get("home").asText();
            String away365 = game.get("away").asText();
            boolean stop1=false, stop2=false;
            for (int n = 0; n < listNames365.size() || !(stop1&&stop2); n++) {
                if (home365.equals(listNames365.get(n))) {
                    if (listNames1x.get(n).equals("xxx")) {
                        player1 = true;
                    } else {
                        playerOn1X = listNames1x.get(n);
                    }
                    stop1=true;
                }
                if (away365.equals(listNames365.get(n))) {
                    if (listNames1x.get(n).equals("xxx")) {
                        player2 = true;
                    } else {
                        playerOn1X = listNames1x.get(n);
                    }
                    stop2=true;
                }
            }
            if (player1 && !player2 || !player1 && player2) {
                for(JsonNode game1x : jsonNode1x){
                    String sport1x = game1x.get("S").asText();
                    String away1x = game1x.get("A").asText();
                    String home1x = game1x.get("H").asText();
                    if (sport1x.equals("Теннис") && game1x.get("P").asInt() == 0 && game1x.get("T").asInt() == 1 && (home1x.equals(playerOn1X)
                            ||  away1x.contains(playerOn1X)) && !league365.contains("ITF")
                            && !league365.contains("WTA") && !league365.contains("Women") && !league365.contains("UTR")) {
                        updateNameOn1xBet(home365, home1x);
                        updateNameOn1xBet(away365, away1x);
                    }
                }
            }
        }
    }

    private void updateNameOn1xBet(String name365, String name1x) {
        TennisPlayer tennisPlayer = tennisPlayerService.getByNameOnBet365(name365);
        tennisPlayerService.updateNameOn1xBet(tennisPlayer, name1x);
    }
}

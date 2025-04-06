package ru.bets.project.spoyer;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bets.project.matches.Match1xbet;
import ru.bets.project.matches.MatchTennis365;
import ru.bets.project.services.spoyerservices.TennisPlayerService;
import ru.bets.project.spoyer.assist.JsonNodeBuilder;
import ru.bets.project.telegram.Telegram;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Component
public class ApiSpoyer extends Thread {

    private final TennisPlayerService tennisPlayerService;
    private final Telegram telegram;
    private final JsonNodeBuilder jsonNodeBuilder;
    private final Observer observer;
    @Autowired
    public ApiSpoyer(TennisPlayerService tennisPlayerService, Telegram telegram, JsonNodeBuilder jsonNodeBuilder, Observer observer) {
        this.tennisPlayerService = tennisPlayerService;
        this.telegram = telegram;
        this.jsonNodeBuilder = jsonNodeBuilder;
        this.observer = observer;
    }


    @Override
    public void run() {
        List<String> listNames365 = tennisPlayerService.getListNames365();
        List<String> listNames1x = tennisPlayerService.getListNames1xbet();
        List<MatchTennis365> listMatchesBet365 = new ArrayList<>();
        JsonNode jsonNodeSpoyer;
        try {
            jsonNodeSpoyer = jsonNodeBuilder.getElements( "task=pre&bookmaker=bet365&sport=tennis");
        } catch (IOException | InterruptedException e) {
            telegram.sendMessageToChat("Ошибка получения данных от Spoyer: " + e.getMessage());
            return;
        }
        Iterator<JsonNode> elementsSpoyer = jsonNodeSpoyer.get("games_pre").elements();
        while (elementsSpoyer.hasNext()) {
            JsonNode game = elementsSpoyer.next();
            String league = game.get("league").asText();
            if (league.contains("WD") || league.contains("MD")) {
                continue;
            }
            long time = game.get("time").asLong();
            String id = game.get("game_id").asText();
            try {
                JsonNode jsonNodeMatch365 = jsonNodeBuilder.getElements("task=preodds&bookmaker=bet365&game_id=" + id);
                listMatchesBet365.add(new MatchTennis365(jsonNodeMatch365, league, time));
            } catch (IOException | InterruptedException e) {
                continue;
            }
        }
        for (int i = 0; i < listMatchesBet365.size(); i++) {
            if (listMatchesBet365.get(i).getTotalOver().isEmpty()) {
                listMatchesBet365.remove(i);
                i--;
            }
        }
        JsonNode jsonNode1x;
        try {
            jsonNode1x = jsonNodeBuilder.getElements("task=1xpreall");
        } catch (IOException | InterruptedException e) {
            telegram.sendMessageToChat("Ошибка получения данных от Spoyer: " + e.getMessage());
            return;
        }

        List<Match1xbet> listMatches1x = new ArrayList<>();
        Iterator<JsonNode> elements1x = jsonNode1x.elements();
        while (elements1x.hasNext()) {
            JsonNode game = elements1x.next();
            String s = game.get("S").asText();
            if (s.equals("Теннис") && game.get("P").asInt() == 1 && game.get("T").asInt() == 1) {
                listMatches1x.add(new Match1xbet(game));
            }
        }
        for (int i = 0; i < listMatches1x.size(); i++) {
            if (listMatches1x.get(i).getHandicapHome().isEmpty() && listMatches1x.get(i).getTotalOver().isEmpty()) {
                listMatches1x.remove(i);
                i--;
            }
        }

        observer.observe(listMatchesBet365,listNames365,listMatches1x,listNames1x);

        telegram.sendMessageToChat("Просмотрено");

    }

}

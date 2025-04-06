package ru.bets.project.spoyer.assist;

import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import ru.bets.project.models.spoyermodels.TennisPlayer;
import ru.bets.project.services.spoyerservices.LeaguesService;
import ru.bets.project.services.spoyerservices.TennisPlayerService;
import ru.bets.project.spoyer.dataupdaters.UpdaterLastUpdate;

import java.io.IOException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Iterator;

@Component
@PropertySource("classpath:application.properties")
public class Calculate {
    private final TennisPlayerService tennisPlayerService;
    private final LeaguesService leaguesService;
    private final UpdaterLastUpdate updaterLastUpdate;
    private final AdderFirstServer adderFirstServer;
    private final JsonNodeBuilder jsonNodeBuilder;
    @Autowired
    public Calculate(TennisPlayerService tennisPlayerService, LeaguesService leaguesService, UpdaterLastUpdate updaterLastUpdate, AdderFirstServer adderFirstServer, JsonNodeBuilder jsonNodeBuilder) {
        this.tennisPlayerService = tennisPlayerService;
        this.leaguesService = leaguesService;
        this.updaterLastUpdate = updaterLastUpdate;
        this.adderFirstServer = adderFirstServer;
        this.jsonNodeBuilder = jsonNodeBuilder;
    }

    public void funcAddServeToPlayer(String nameOn365) {
        TennisPlayer tennisPlayer = tennisPlayerService.getByNameOnBet365(nameOn365);
        int idOnSpoyer;
        if (tennisPlayer == null) {
            return;
        } else {
            idOnSpoyer = tennisPlayer.getIdOnSpoyer();
        }
        String nameOnSpoyer = tennisPlayerService.getByIdOnSpoyer(idOnSpoyer).getNameOnSpoyer();
        LocalDate last_update = tennisPlayerService.getByIdOnSpoyer(idOnSpoyer).getLastUpdate();
        JsonNode jsonNodeSpoyer;

        try {
            jsonNodeSpoyer = jsonNodeBuilder.getElements("task=enddata&sport=tennis&team=" + idOnSpoyer);
        } catch (IOException | InterruptedException e) {
            return;
        }
        JsonNode gamesEnd = jsonNodeSpoyer.get("games_end");
        Iterator<JsonNode> elementsSpoyer = gamesEnd.elements();
        while (elementsSpoyer.hasNext()) {
            JsonNode game = elementsSpoyer.next();
            long time = game.get("time").asLong();
            Instant instant = Instant.ofEpochSecond(time);
            LocalDate dateMatch = instant.atZone(ZoneId.systemDefault()).toLocalDate();
            if ((dateMatch.toEpochDay() - last_update.toEpochDay()) < -7) {
                continue;
            }
            JsonNode jsonNodeEvent;
            try {
                jsonNodeEvent = jsonNodeBuilder.getElements("task=eventdata&game_id=" + gamesEnd.get("game_id").asText());
            } catch (IOException | InterruptedException e) {
                continue;
            }
            JsonNode jsonNodeResult = jsonNodeEvent.get("result").get(0);
            String textEvent = "";
            try {
                textEvent = jsonNodeResult.get("events").get(0).get("text").asText();
            } catch (Exception e) {
                continue;
            }
            int gameId = jsonNodeResult.get("id").asInt();
            String home = jsonNodeResult.get("home").get("name").asText();
            String away = jsonNodeResult.get("away").get("name").asText();
            String groundCourt;
            try {
                groundCourt = jsonNodeResult.get("extra").get("ground").asText();
            } catch (Exception e) {
                groundCourt = "null";
            }
            String leagueSpoyer = jsonNodeResult.get("league").get("name").asText();
            int idLeagueSpoyer = jsonNodeResult.get("league").get("id").asInt();
            long unixTimeStamp = jsonNodeResult.get("time").asLong();

            Instant instantTime = Instant.ofEpochSecond(unixTimeStamp);
            LocalDate dateTime = instantTime.atZone(ZoneId.systemDefault()).toLocalDate();


            String who_first_serve = "";
            if (textEvent.contains("Game 1")) {
                if (home.equals(nameOnSpoyer)) {
                    if (textEvent.contains(nameOn365)) {
                        if (textEvent.contains("holds to")) {
                            who_first_serve = home;
                        } else {
                            if (textEvent.contains("breaks to")) {
                                who_first_serve = away;
                            }
                        }
                    } else {
                        if (textEvent.contains("holds to")) {
                            who_first_serve = away;
                        } else {
                            if (textEvent.contains("breaks to")) {
                                who_first_serve = home;
                            }
                        }
                    }
                } else {
                    if (textEvent.contains(nameOn365)) {
                        if (textEvent.contains("holds to")) {
                            who_first_serve = away;
                        } else {
                            if (textEvent.contains("breaks to")) {
                                who_first_serve = home;
                            }
                        }
                    } else {
                        if (textEvent.contains("holds to")) {
                            who_first_serve = home;
                        } else {
                            if (textEvent.contains("breaks to")) {
                                who_first_serve = away;
                            }
                        }
                    }
                }
            }
            adderFirstServer.addWhoServeFirst(gameId, home, away, who_first_serve, dateTime, idLeagueSpoyer, leagueSpoyer, groundCourt);
        }
        updaterLastUpdate.updateLastUpdate(idOnSpoyer, last_update);

    }

    public String funcCalculateServer(String nameHome365, String nameAway365, String league365) {
        String info = "";
        String leagueSpoyer = leaguesService.getNameOnSpoyerByNameOnBet365(league365);
        String homeSpoyer = tennisPlayerService.getByNameOnBet365(nameHome365).getNameOnSpoyer();
        String awaySpoyer = tennisPlayerService.getByNameOnBet365(nameAway365).getNameOnSpoyer();
        if (homeSpoyer == null || awaySpoyer == null) {
            info += "Не хватает информации по подачам.";
            return info;
        }
        info += homeSpoyer + " v " + awaySpoyer + "\n";
        //info +=getPercentServeAllGame(homeSpoyer, awaySpoyer, leagueSpoyer);
        return info;
    }
    public String goodDopInfo(String ground, String type, String who, double value, double odds){
        return "";
    }
}

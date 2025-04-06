package ru.bets.project.matches;

import com.fasterxml.jackson.databind.JsonNode;
import ru.bets.project.utils.Index;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;

public class MatchTennis365 extends Match{
    private double p160,p161,p162,p163,p164,p175,p176;
    private double p260,p261,p262,p263,p264,p275,p276;
    private double odds;
    private double probability;
    private LocalDate date;
    public MatchTennis365(JsonNode game,String league,long time) {
        this.league = league;
        Instant instant = Instant.ofEpochSecond(time);
        date = instant.atZone(ZoneId.systemDefault()).toLocalDate();
        handicapHome = new ArrayList<>();
        handicapAway = new ArrayList<>();
        totalOver = new ArrayList<>();
        totalUnder = new ArrayList<>();
        try {
            teamHome = game.get("result").get(0).get("main").get("sp").get("to_win_match").get(0).get("name").asText();
            teamAway = game.get("result").get(0).get("main").get("sp").get("to_win_match").get(1).get("name").asText();
        } catch (Exception e) {
            teamHome = "Home";
            teamAway = "Away";
        }
        JsonNode set = game.get("result").get(0).get("set").get("sp").get("first_set_score");
        try {
            calculateProbability(set);
            calculateBet();
        } catch (Exception e) {
        }
    }
    private void calculateProbability(JsonNode set) {
        odds = set.get(7).get("odds").asDouble();
        p160 = 1 / odds;
        odds = set.get(8).get("odds").asDouble();
        p161 = 1 / odds;
        odds = set.get(9).get("odds").asDouble();
        p162 = 1 / odds;
        odds = set.get(10).get("odds").asDouble();
        p163 = 1 / odds;
        odds = set.get(11).get("odds").asDouble();
        p164 = 1 / odds;
        odds = set.get(12).get("odds").asDouble();
        p175 = 1 / odds;
        odds = set.get(13).get("odds").asDouble();
        p176 = 1 / odds;
        odds = set.get(14).get("odds").asDouble();
        p260 = 1 / odds;
        odds = set.get(15).get("odds").asDouble();
        p261 = 1 / odds;
        odds = set.get(16).get("odds").asDouble();
        p262 = 1 / odds;
        odds = set.get(17).get("odds").asDouble();
        p263 = 1 / odds;
        odds = set.get(18).get("odds").asDouble();
        p264 = 1 / odds;
        odds = set.get(19).get("odds").asDouble();
        p275 = 1 / odds;
        odds = set.get(20).get("odds").asDouble();
        p276 = 1 / odds;
        probability = p160 + p161 + p162 + p163 + p164 + p175 + p176 + p260 + p261 + p262 + p263 + p264 + p275 + p276;
        p160 = p160 / probability;
        p161 = p161 / probability;
        p162 = p162 / probability;
        p163 = p163 / probability;
        p164 = p164 / probability;
        p175 = p175 / probability;
        p176 = p176 / probability;
        p260 = p260 / probability;
        p261 = p261 / probability;
        p262 = p262 / probability;
        p263 = p263 / probability;
        p264 = p264 / probability;
        p275 = p275 / probability;
        p276 = p276 / probability;

    }
    private void calculateBet(){
        double tm9 = p160 + p161 + p162 + p163 + p260 + p261 + p262 + p263;
        totalUnder.add(new Index(9.5,tm9));
        double tb9 = 1 - tm9;
        totalOver.add(new Index(9.5,tb9));
        double h1Minus25 = p160 + p161 + p162 + p163;
        double h2Minus25 = p260 + p261 + p262 + p263;
        handicapHome.add(new Index(-2.5,h1Minus25));
        handicapAway.add(new Index(-2.5,h2Minus25));
        double h1Plus25 = 1 - h1Minus25;
        double h2Plus25 = 1 - h2Minus25;
        handicapHome.add(new Index(2.5,h1Plus25));
        handicapAway.add(new Index(2.5,h2Plus25));
        chet = p160 + p162 + p164 + p175 + p260 + p262 + p264 + p275;
        nechet = 1 - chet;
    }

    public LocalDate getDate() {
        return date;
    }
}

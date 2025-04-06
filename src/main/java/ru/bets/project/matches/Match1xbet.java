package ru.bets.project.matches;

import com.fasterxml.jackson.databind.JsonNode;
import ru.bets.project.utils.Index;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class Match1xbet extends Match{
    private int period;
    private String strPeriod;
    private double winHome,winAway;
    private int typeGame;
    private int idGame;
    private String stake;
    private List<Index> individualTotalOverHome;
    private List<Index> individualTotalOverAway;
    private List<Index> individualTotalUnderHome;
    private List<Index> individualTotalUnderAway;
    public Match1xbet(JsonNode game) {
        handicapHome = new ArrayList<>();
        handicapAway = new ArrayList<>();
        totalOver = new ArrayList<>();
        totalUnder = new ArrayList<>();
        individualTotalOverHome = new ArrayList<>();
        individualTotalOverAway = new ArrayList<>();
        individualTotalUnderHome = new ArrayList<>();
        individualTotalUnderAway = new ArrayList<>();
        teamHome = game.get("H").asText();
        teamAway = game.get("A").asText();
        league = game.get("C").asText();
        period = game.get("P").asInt();
        strPeriod = game.get("NP").asText();
        if (strPeriod.isEmpty())
            strPeriod = "Основная игра";
        typeGame = game.get("T").asInt();
        idGame = game.get("I").asInt();
        stake = game.get("Stake").asText();

        JsonNode jsonNodeData = game.get("EE");
        Iterator<JsonNode> elements = jsonNodeData.elements();
        while (elements.hasNext()) {
            JsonNode data = elements.next();
            double value = data.get("P").asDouble();
            double odds = data.get("C").asDouble();
            Index index = new Index(value, odds);
            switch (data.get("T").asInt()) {
                case 1: {
                    winHome = data.get("C").asDouble();
                    break;
                }
                case 3: {
                    winAway = data.get("C").asDouble();
                    break;
                }
                case 7:{
                    handicapHome.add(index);
                    break;
                }
                case 8:{
                    handicapAway.add(index);
                    break;
                }
                case 9:{
                    totalOver.add(index);
                    break;
                }
                case 10: {
                    insertSorted(index);
                    break;
                }
                case 11: {

                    individualTotalOverHome.add(index);
                    break;
                }
                case 12: {

                    individualTotalUnderHome.add(index);
                    break;
                }
                case 13: {

                    individualTotalOverAway.add(index);
                    break;
                }
                case 14: {

                    individualTotalUnderAway.add(index);
                    break;
                }
                case 182: {
                    chet = data.get("C").asDouble();
                    break;
                }
                case 183: {
                    nechet = data.get("C").asDouble();
                    break;
                }
                default:
                    break;
            }
        }
    }
    private void insertSorted( Index index) {
        int pos = Collections.binarySearch(totalUnder, index);
        if (pos < 0) {
            pos = -(pos + 1);
        }
        totalUnder.add(pos, index);
    }
}

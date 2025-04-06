package ru.bets.project.matches;

import ru.bets.project.utils.Index;

import java.util.ArrayList;
import java.util.List;

public class YellowCard {

    private double win1;
    private double win2;
    private double draw;
    private double x1, x2, notX;
    private List<Index> handicap1;
    private List<Index> handicap2;
    private List<Index> totalOver;
    private List<Index> totalUnder;
    private List<Index> individualTotalOver1;
    private List<Index> individualTotalOver2;
    private List<Index> individualTotalUnder1;
    private List<Index> individualTotalUnder2;

    public YellowCard() {
        handicap1 = new ArrayList<>();
        handicap2 = new ArrayList<>();
        totalOver = new ArrayList<>();
        totalUnder = new ArrayList<>();
        individualTotalOver1 = new ArrayList<>();
        individualTotalOver2 = new ArrayList<>();
        individualTotalUnder1 = new ArrayList<>();
        individualTotalUnder2 = new ArrayList<>();
    }

    public double getWin1() {
        return win1;
    }

    public void setWin1(double win1) {
        this.win1 = win1;
    }

    public double getWin2() {
        return win2;
    }

    public void setWin2(double win2) {
        this.win2 = win2;
    }

    public double getDraw() {
        return draw;
    }

    public void setDraw(double draw) {
        this.draw = draw;
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getNotX() {
        return notX;
    }

    public void setNotX(double notX) {
        this.notX = notX;
    }

    public List<Index> getHandicap1() {
        return handicap1;
    }

    public void setHandicap1(List<Index> handicap1) {
        this.handicap1 = handicap1;
    }

    public List<Index> getHandicap2() {
        return handicap2;
    }

    public void setHandicap2(List<Index> handicap2) {
        this.handicap2 = handicap2;
    }

    public List<Index> getTotalOver() {
        return totalOver;
    }

    public void setTotalOver(List<Index> totalOver) {
        this.totalOver = totalOver;
    }

    public List<Index> getTotalUnder() {
        return totalUnder;
    }

    public void setTotalUnder(List<Index> totalUnder) {
        this.totalUnder = totalUnder;
    }

    public List<Index> getIndividualTotalOver1() {
        return individualTotalOver1;
    }

    public void setIndividualTotalOver1(List<Index> individualTotalOver1) {
        this.individualTotalOver1 = individualTotalOver1;
    }

    public List<Index> getIndividualTotalOver2() {
        return individualTotalOver2;
    }

    public void setIndividualTotalOver2(List<Index> individualTotalOver2) {
        this.individualTotalOver2 = individualTotalOver2;
    }

    public List<Index> getIndividualTotalUnder1() {
        return individualTotalUnder1;
    }

    public void setIndividualTotalUnder1(List<Index> individualTotalUnder1) {
        this.individualTotalUnder1 = individualTotalUnder1;
    }

    public List<Index> getIndividualTotalUnder2() {
        return individualTotalUnder2;
    }

    public void setIndividualTotalUnder2(List<Index> individualTotalUnder2) {
        this.individualTotalUnder2 = individualTotalUnder2;
    }
}

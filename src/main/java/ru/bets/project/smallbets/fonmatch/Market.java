package ru.bets.project.smallbets.fonmatch;

import ru.bets.project.utils.Index;

import java.util.ArrayList;
import java.util.List;

public class Market {
    protected int score1;
    protected int score2;
    protected double win1;
    protected double draw;
    protected double win2;
    protected double win1Draw;
    protected double win1Win2;
    protected double win2Draw;
    protected List<Index> handicap;
    protected List<Index> total;
    protected List<Index> individualTotal1;
    protected List<Index> individualTotal2;
    public Market(){

    }
    public Market(String[] odds, String score) {
        if (!odds[0].isEmpty()) {
            win1 = Double.parseDouble(odds[0]);
        }
        if (!odds[1].isEmpty()) {
            draw = Double.parseDouble(odds[1]);
        }
        if (!odds[2].isEmpty()) {
            win2 = Double.parseDouble(odds[2]);
        }
        if (!odds[3].isEmpty()) {
            win1Draw = Double.parseDouble(odds[3]);
        }
        if (!odds[4].isEmpty()) {
            win1Win2 = Double.parseDouble(odds[4]);
        }
        if (!odds[5].isEmpty()) {
            win2Draw = Double.parseDouble(odds[5]);
        }
        double value;
        double odd;
        handicap = new ArrayList<>();
        if (!odds[6].isEmpty()) {
            value = Double.parseDouble(odds[6]);
            odd = Double.parseDouble(odds[7]);
            handicap.add(new Index(value, odd));
        }
        if (!odds[8].isEmpty()) {
            value = Double.parseDouble(odds[8]);
            odd = Double.parseDouble(odds[9]);
            handicap.add(new Index(value, odd));
        }
        total = new ArrayList<>();
        if (!odds[10].isEmpty()) {
            value = Double.parseDouble(odds[10]);
            odd = Double.parseDouble(odds[11]);
            total.add(new Index(value, odd));
            value = Double.parseDouble(odds[10]);
            odd = Double.parseDouble(odds[12]);
            total.add(new Index(value, odd));
        }
        individualTotal1 = new ArrayList<>();
        individualTotal2 = new ArrayList<>();
        score1 = setScore1(score);
        score2 = setScore2(score);
    }

    public int getScore2() {
        return score2;
    }

    public int getScore1() {
        return score1;
    }
    public List<Index> getHandicap() {
        return handicap;
    }

    public List<Index> getTotal() {
        return total;
    }

    public double getWin1() {
        return win1;
    }

    public double getDraw() {
        return draw;
    }

    public double getWin2() {
        return win2;
    }

    private int setScore1(String s) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; Character.isDigit(s.charAt(i)); i++) {
            builder.append(s.charAt(i));
        }
        int score1 = Integer.parseInt(builder.toString());
        return score1;
    }

    private int setScore2(String s) {
        StringBuilder builder = new StringBuilder();
        int i = 0;
        while (Character.isDigit(s.charAt(i))) {
            i++;
        }
        i++;
        while (i < s.length() && Character.isDigit(s.charAt(i))) {
            builder.append(s.charAt(i));
            i++;
        }
        int score2 = Integer.parseInt(builder.toString());
        return score2;
    }
}

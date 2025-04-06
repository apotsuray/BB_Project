package ru.bets.project.smallbets.fonmatch;

import ru.bets.project.utils.Index;

import java.util.ArrayList;

public class FonMatch extends Market {
    private String nameTournament;
    private String name; //название матча (команды)
    private String time; //время матча

    private FonCorner corner; //угловые
    private FonCorner corner1stTime; //1-й тайм угловые
    private FonYellowCard yellowCard; //жк
    private FonYellowCard yellowCard1stTime;    //1-й тайм жк
    private FonFoul foul;                       //фолы
    private FonFoul foul1stTime;                //1-й тайм фолы
    private FonThrowIn throwIn;                 // вба
    private FonThrowIn throwIn1stTime;          //1-й тайм вба
    private FonGoalKick goalKick;               //уотв
    private FonGoalKick goalKick1stTime;        //1-й тайм уотв

    public FonMatch(String nameTournament, String name, String time) {
        super();
        this.nameTournament = nameTournament;
        this.name = name;
        this.time = time;
    }

    public void setOdds(String[] matchOdds) {
        if (!matchOdds[0].isEmpty()) {
            win1 = Double.parseDouble(matchOdds[0]);
        }
        if (!matchOdds[1].isEmpty()) {
            draw = Double.parseDouble(matchOdds[1]);
        }
        if (!matchOdds[2].isEmpty()) {
            win2 = Double.parseDouble(matchOdds[2]);
        }
        if (!matchOdds[3].isEmpty()) {
            win1Draw = Double.parseDouble(matchOdds[3]);
        }
        if (!matchOdds[4].isEmpty()) {
            win1Win2 = Double.parseDouble(matchOdds[4]);
        }
        if (!matchOdds[5].isEmpty()) {
            win2Draw = Double.parseDouble(matchOdds[5]);
        }
        double value;
        double odds;
        handicap = new ArrayList<>();
        if (!matchOdds[6].isEmpty()) {
            value = Double.parseDouble(matchOdds[6]);
            odds = Double.parseDouble(matchOdds[7]);
            handicap.add(new Index(value, odds));
        }
        if (!matchOdds[8].isEmpty()) {
            value = Double.parseDouble(matchOdds[8]);

            odds = Double.parseDouble(matchOdds[9]);
            handicap.add(new Index(value, odds));
        }
        total = new ArrayList<>();
        if (!matchOdds[10].isEmpty() && !matchOdds[11].isEmpty() && !matchOdds[12].isEmpty()) {

            value = Double.parseDouble(matchOdds[10]);
            odds = Double.parseDouble(matchOdds[11]);
            total.add(new Index(value, odds));
            value = Double.parseDouble(matchOdds[10]);
            odds = Double.parseDouble(matchOdds[12]);
            total.add(new Index(value, odds));
        }
    }

    public void setYellowCard(String[] yellowCardOdds, String score) {
        yellowCard = new FonYellowCard(yellowCardOdds, score);
    }

    public void setYellowCard1stTime(String[] yellowCardOdds, String score) {
        yellowCard1stTime = new FonYellowCard(yellowCardOdds, score);
    }

    public FonYellowCard getYellowCard() {
        return yellowCard;
    }

    public FonYellowCard getYellowCard1stTime() {
        return yellowCard1stTime;
    }

    public void setFoul(String[] foulOdds, String score) {
        foul = new FonFoul(foulOdds, score);
    }

    public void setFoul1stTime(String[] foulOdds, String score) {
        foul1stTime = new FonFoul(foulOdds, score);
    }

    public void setThrowIn(String[] throwInOdds, String score) {
        throwIn = new FonThrowIn(throwInOdds, score);
    }

    public void setThrowIn1stTime(String[] throwInOdds, String score) {
        throwIn1stTime = new FonThrowIn(throwInOdds, score);
    }

    public void setGoalKick(String[] goalKicknOdds, String score) {
        goalKick = new FonGoalKick(goalKicknOdds, score);
    }

    public void setGoalKick1stTime(String[] goalKickOdds, String score) {
        goalKick1stTime = new FonGoalKick(goalKickOdds, score);
    }

    public void setCorner(String[] cornerdOdds, String score) {
        corner = new FonCorner(cornerdOdds, score);
    }

    public FonFoul getFoul() {
        return foul;
    }

    public FonFoul getFoul1stTime() {
        return foul1stTime;
    }

    public FonThrowIn getThrowIn() {
        return throwIn;
    }

    public FonThrowIn getThrowIn1stTime() {
        return throwIn1stTime;
    }

    public FonGoalKick getGoalKick() {
        return goalKick;
    }

    public FonGoalKick getGoalKick1stTime() {
        return goalKick1stTime;
    }

    public String getNameTournament() {
        return nameTournament;
    }

    public String getTime() {
        return time;
    }

    public Double getDoubleTime() {
        if (time.isEmpty() || time.contains("Матч не начался")) {
            return 0.0;
        }
        if (time.length() == 4) {
            double minute =Character.getNumericValue(time.charAt(0));//Char.GetNumericValue(time[0]);
            double sec = Character.getNumericValue(time.charAt(2));
            sec = sec * 10 + Character.getNumericValue(time.charAt(3));
            sec = sec * 1.6666;
            sec = sec / 100;
            double currentTime = minute + sec;
            return currentTime;
        } else {
            double minute = Character.getNumericValue(time.charAt(0));
            minute = minute * 10 + Character.getNumericValue(time.charAt(1));
            double sec = Character.getNumericValue(time.charAt(3));
            sec = sec * 10 + Character.getNumericValue(time.charAt(4));
            sec = sec * 1.6666;
            sec = sec / 100;
            double currentTime = minute + sec;
            return currentTime;
        }
    }

    public String getName() {
        return name;
    }


}


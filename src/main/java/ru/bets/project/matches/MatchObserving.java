package ru.bets.project.matches;

public class MatchObserving {
    private double h1Minus25;
    private double h1Plus25;
    private double h2Minus25;
    private double h2Plus25;
    private double over95;
    private double under95;
    private double chet;
    private double nechet;
    private MatchTennis365 matchTennis365;
    private Match1xbet match1xbet;
    public MatchObserving(MatchTennis365 matchTennis365) {
        h1Minus25 = matchTennis365.getHandicapHome().get(0).odds;
        h1Plus25 = matchTennis365.getHandicapHome().get(1).odds;
        h2Minus25 = matchTennis365.getHandicapAway().get(0).odds;
        h2Plus25 = matchTennis365.getHandicapAway().get(1).odds;
        over95 = matchTennis365.getTotalOver().get(0).odds;
        under95 = matchTennis365.getTotalUnder().get(0).odds;
        chet = matchTennis365.getChet();
        nechet = matchTennis365.getNechet();
        this.matchTennis365 = matchTennis365;
    }

    public MatchObserving(Match1xbet match1xbet) {
        chet = match1xbet.getChet();
        nechet = match1xbet.getNechet();
        this.match1xbet = match1xbet;
    }

    public double getH1Minus25() {
        return h1Minus25;
    }

    public void setH1Minus25() {
        for (int i = 0; i < match1xbet.getHandicapHome().size(); i++) {
            if (match1xbet.getHandicapHome().get(i).value == -2.5) {
                h1Minus25 = match1xbet.getHandicapHome().get(i).odds;
                break;
            }
        }
    }

    public double getH1Plus25() {
        return h1Plus25;
    }

    public void setH1Plus25() {
        for (int i = 0; i < match1xbet.getHandicapHome().size(); i++) {
            if (match1xbet.getHandicapHome().get(i).value == 2.5) {
                h1Plus25 = match1xbet.getHandicapHome().get(i).odds;
                break;
            }
        }
    }

    public double getH2Minus25() {
        return h2Minus25;
    }

    public void setH2Minus25() {
        for (int i = 0; i < match1xbet.getHandicapAway().size(); i++) {
            if (match1xbet.getHandicapAway().get(i).value == -2.5) {
                h2Minus25 = match1xbet.getHandicapAway().get(i).odds;
                break;
            }
        }
    }

    public double getH2Plus25() {
        return h2Plus25;
    }

    public void setH2Plus25() {
        for (int i = 0; i < match1xbet.getHandicapAway().size(); i++) {
            if (match1xbet.getHandicapAway().get(i).value == 2.5) {
                h2Plus25 = match1xbet.getHandicapAway().get(i).odds;
                break;
            }
        }
    }

    public double getOver95() {
        return over95;
    }

    public void setOver95() {
        for (int i = 0; i < match1xbet.getTotalOver().size(); i++) {
            if (match1xbet.getTotalOver().get(i).value == 9.5) {
                over95 = match1xbet.getTotalOver().get(i).odds;
                break;
            }
        }
    }

    public double getUnder95() {
        return under95;
    }

    public void setUnder95() {
        for (int i = 0; i < match1xbet.getTotalUnder().size(); i++) {
            if (match1xbet.getTotalUnder().get(i).value == 9.5) {
                under95 = match1xbet.getTotalUnder().get(i).odds;
                break;
            }
        }
    }

    public double getChet() {
        return chet;
    }

    public void setChet(double chet) {
        this.chet = chet;
    }

    public double getNechet() {
        return nechet;
    }

    public void setNechet(double nechet) {
        this.nechet = nechet;
    }

    public MatchTennis365 getMatchTennis365() {
        return matchTennis365;
    }

    public Match1xbet getMatch1xbet() {
        return match1xbet;
    }
}

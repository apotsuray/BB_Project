package ru.bets.project.utils;

public class Pair {
    private String nameOnBet365;
    private String nameOnSpoyer;

    public Pair(String nameOnBet365, String nameOnSpoyer) {
        this.nameOnBet365 = nameOnBet365;
        this.nameOnSpoyer = nameOnSpoyer;
    }

    public String getNameOnBet365() {
        return nameOnBet365;
    }

    public void setNameOnBet365(String nameOnBet365) {
        this.nameOnBet365 = nameOnBet365;
    }

    public String getNameOnSpoyer() {
        return nameOnSpoyer;
    }

    public void setNameOnSpoyer(String nameOnSpoyer) {
        this.nameOnSpoyer = nameOnSpoyer;
    }
}

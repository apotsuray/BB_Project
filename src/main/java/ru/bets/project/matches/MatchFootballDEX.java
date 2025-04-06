package ru.bets.project.matches;

public class MatchFootballDEX extends Match{
    private YellowCard card;
    private YellowCard card1half;
    private YellowCard card2half;
    public MatchFootballDEX(String teamHome,String teamAway) {
        this.teamHome = teamHome;
        this.teamAway = teamAway;
        card = new YellowCard();
        card1half = new YellowCard();
        card2half = new YellowCard();
    }

    public YellowCard getCard() {
        return card;
    }

    public void setCard(YellowCard card) {
        this.card = card;
    }

    public YellowCard getCard1half() {
        return card1half;
    }

    public void setCard1half(YellowCard card1half) {
        this.card1half = card1half;
    }

    public YellowCard getCard2half() {
        return card2half;
    }

    public void setCard2half(YellowCard card2half) {
        this.card2half = card2half;
    }
}

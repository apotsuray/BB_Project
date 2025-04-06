package ru.bets.project.models.smarttablesmodels;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "matches")
public class SmartMatch {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "date")
    private LocalDate date;
    @ManyToOne()
    @JoinColumn(name = "id_tournament",referencedColumnName = "id")
    private Tournament tournament;
    @ManyToOne
    @JoinColumn(name = "id_team_home", referencedColumnName = "id")
    private Team teamHome;
    @ManyToOne
    @JoinColumn(name = "id_team_away", referencedColumnName = "id")
    private Team teamAway;
    @ManyToOne()
    @JoinColumn(name = "id_referee",referencedColumnName = "id")
    private Referee referee;

    @ManyToOne
    @JoinColumn(name = "id_score", referencedColumnName = "id")
    private Score score;
    @ManyToOne
    @JoinColumn(name = "id_score_first_time", referencedColumnName = "id")
    private Score scoreFirstTime;
    @ManyToOne
    @JoinColumn(name = "id_score_second_time", referencedColumnName = "id")
    private Score scoreSecondTime;
    @Column(name = "odd_home")
    private double oddHome;
    @Column(name = "odd_draw")
    private double oddDraw;
    @Column(name = "odd_away")
    private double oddAway;
    @Column(name = "principled_duel")
    private boolean principledDuel;
    @Column(name = "minute_first_red_card_home")
    private int minuteFirstRedCardHome;
    @Column(name = "minute_first_red_card_away")
    private int minuteFirstRedCardAway;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Team getTeamHome() {
        return teamHome;
    }

    public void setTeamHome(Team teamHome) {
        this.teamHome = teamHome;
    }

    public Team getTeamAway() {
        return teamAway;
    }

    public void setTeamAway(Team teamAway) {
        this.teamAway = teamAway;
    }

    public Referee getReferee() {
        return referee;
    }

    public void setReferee(Referee referee) {
        this.referee = referee;
    }

    public Score getScore() {
        return score;
    }

    public void setScore(Score score) {
        this.score = score;
    }

    public Score getScoreFirstTime() {
        return scoreFirstTime;
    }

    public void setScoreFirstTime(Score scoreFirstTime) {
        this.scoreFirstTime = scoreFirstTime;
    }

    public Score getScoreSecondTime() {
        return scoreSecondTime;
    }

    public void setScoreSecondTime(Score scoreSecondTime) {
        this.scoreSecondTime = scoreSecondTime;
    }

    public double getOddHome() {
        return oddHome;
    }

    public void setOddHome(double oddHome) {
        this.oddHome = oddHome;
    }

    public double getOddDraw() {
        return oddDraw;
    }

    public void setOddDraw(double oddDraw) {
        this.oddDraw = oddDraw;
    }

    public double getOddAway() {
        return oddAway;
    }

    public void setOddAway(double oddAway) {
        this.oddAway = oddAway;
    }

    public boolean isPrincipledDuel() {
        return principledDuel;
    }

    public void setPrincipledDuel(boolean principledDuel) {
        this.principledDuel = principledDuel;
    }

    public int getMinuteFirstRedCardHome() {
        return minuteFirstRedCardHome;
    }

    public void setMinuteFirstRedCardHome(int minuteFirstRedCardHome) {
        this.minuteFirstRedCardHome = minuteFirstRedCardHome;
    }

    public int getMinuteFirstRedCardAway() {
        return minuteFirstRedCardAway;
    }

    public void setMinuteFirstRedCardAway(int minuteFirstRedCardAway) {
        this.minuteFirstRedCardAway = minuteFirstRedCardAway;
    }
}

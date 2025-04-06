package ru.bets.project.models.spoyermodels;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "who_serve_first")
public class WhoServeFirst {
    @Id
    @Column(name = "id_game")
    private int idGame;
    @Column(name = "home")
    @Size(min = 2, max = 64)
    private String home;
    @Column(name = "away")
    @Size(min = 2, max = 64)
    private String away;
    @Column(name = "server")
    @Size(min = 2, max = 64)
    private String server;
    @Column(name = "date_match")
    private LocalDate dateMatch;
    @Column(name = "id_tournament_spoyer")
    private int idTournamentSpoyer;

    public WhoServeFirst() {
    }

    public WhoServeFirst(int idGame, String home, String away, String server, LocalDate dateMatch, int idTournamentSpoyer) {
        this.idGame = idGame;
        this.home = home;
        this.away = away;
        this.server = server;
        this.dateMatch = dateMatch;
        this.idTournamentSpoyer = idTournamentSpoyer;
    }

    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public String getHome() {
        return home;
    }

    public void setHome(String home) {
        this.home = home;
    }

    public String getAway() {
        return away;
    }

    public void setAway(String away) {
        this.away = away;
    }

    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public LocalDate getDateMatch() {
        return dateMatch;
    }

    public void setDateMatch(LocalDate dateMatch) {
        this.dateMatch = dateMatch;
    }

    public int getIdTournamentSpoyer() {
        return idTournamentSpoyer;
    }

    public void setIdTournamentSpoyer(int idTournamentSpoyer) {
        this.idTournamentSpoyer = idTournamentSpoyer;
    }
}

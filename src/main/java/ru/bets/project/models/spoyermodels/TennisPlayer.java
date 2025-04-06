package ru.bets.project.models.spoyermodels;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "tennisplayer")
public class TennisPlayer {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name_on_bet365")
    @Size(min = 2, max = 64)
    private String nameOnBet365;
    @Column(name = "name_on_1xbet")
    @Size(min = 2, max = 64)
    private String nameOn1xbet;
    @Column(name = "name_on_spoyer")
    @Size(min = 2, max = 64)
    private String nameOnSpoyer;
    @Column(name = "id_on_bet365")
    private int idOnBet365;
    @Column(name = "id_on_spoyer")
    private int idOnSpoyer;
    @Column(name = "last_update")
    private LocalDate lastUpdate;

    public TennisPlayer() {

    }
    public TennisPlayer(String nameOnBet365) {
        this.nameOnBet365 = nameOnBet365;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameOnBet365() {
        return nameOnBet365;
    }

    public void setNameOnBet365(String nameOnBet365) {
        this.nameOnBet365 = nameOnBet365;
    }

    public String getNameOn1xbet() {
        return nameOn1xbet;
    }

    public void setNameOn1xbet(String nameOn1xbet) {
        this.nameOn1xbet = nameOn1xbet;
    }

    public String getNameOnSpoyer() {
        return nameOnSpoyer;
    }

    public void setNameOnSpoyer(String nameOnSpoyer) {
        this.nameOnSpoyer = nameOnSpoyer;
    }

    public int getIdOnBet365() {
        return idOnBet365;
    }

    public void setIdOnBet365(int idOnBet365) {
        this.idOnBet365 = idOnBet365;
    }

    public int getIdOnSpoyer() {
        return idOnSpoyer;
    }

    public void setIdOnSpoyer(int idOnSpoyer) {
        this.idOnSpoyer = idOnSpoyer;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}

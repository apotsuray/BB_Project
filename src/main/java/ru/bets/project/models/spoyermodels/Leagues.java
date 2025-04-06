package ru.bets.project.models.spoyermodels;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "leagues")
public class Leagues {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name_on_bet365")
    @Size(min = 2, max = 64)
    private String nameOnBet365;
    @Column(name = "name_on_spoyer")
    @Size(min = 2, max = 64)
    private String nameOnSpoyer;
    @Column(name = "court")
    @Size(min = 2, max = 20)
    private String court;
    @Column(name = "id_on_bet365")
    private int idOnBet365;
    @Column(name = "id_on_spoyer")
    private int idOnSpoyer;
    public Leagues() {

    }
    public Leagues(String nameOnSpoyer, String court, int idSpoyer)
    {
        this.nameOnSpoyer = nameOnSpoyer;
        this.court = court;
        this.idOnSpoyer = idSpoyer;
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

    public String getNameOnSpoyer() {
        return nameOnSpoyer;
    }

    public void setNameOnSpoyer(String nameOnSpoyer) {
        this.nameOnSpoyer = nameOnSpoyer;
    }

    public String getCourt() {
        return court;
    }

    public void setCourt(String court) {
        this.court = court;
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
}

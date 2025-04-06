package ru.bets.project.models.smarttablesmodels;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "team")
public class Team {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "fonbet_name")
    private String fonbetName;
    @Column(name = "smart_table_name")
    private String smartTableName;
    @Column(name = "flash_score_name")
    private String flashScoreName;
    @Column(name = "last_update")
    private LocalDate lastUpdate;
    @ManyToOne()
    @JoinColumn(name = "id_tournament",referencedColumnName = "id")
    private Tournament tournament;
    @OneToMany(mappedBy = "teamHome")
    private List<SmartMatch> homeMatches;
    @OneToMany(mappedBy = "teamAway")
    private List<SmartMatch> awayMatches;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFonbetName() {
        return fonbetName;
    }

    public void setFonbetName(String fonbetName) {
        this.fonbetName = fonbetName;
    }

    public String getSmartTableName() {
        return smartTableName;
    }

    public void setSmartTableName(String smartTableName) {
        this.smartTableName = smartTableName;
    }

    public String getFlashScoreName() {
        return flashScoreName;
    }

    public void setFlashScoreName(String flashScoreName) {
        this.flashScoreName = flashScoreName;
    }

    public LocalDate getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(LocalDate lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public List<SmartMatch> getHomeMatches() {
        return homeMatches;
    }

    public void setHomeMatches(List<SmartMatch> homeMatches) {
        this.homeMatches = homeMatches;
    }

    public List<SmartMatch> getAwayMatches() {
        return awayMatches;
    }

    public void setAwayMatches(List<SmartMatch> awayMatches) {
        this.awayMatches = awayMatches;
    }
}

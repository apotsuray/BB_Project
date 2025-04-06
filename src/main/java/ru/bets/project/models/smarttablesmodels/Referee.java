package ru.bets.project.models.smarttablesmodels;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "referee")
public class Referee {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "smart_table_name")
    private String smartTableName;
    @Column(name = "flash_score_name")
    private String flashScoreName;
    @Column(name = "last_update")
    private LocalDate lastUpdate;
    @ManyToOne()
    @JoinColumn(name = "id_tournament",referencedColumnName = "id")
    private Tournament tournament;
    @OneToMany(mappedBy = "referee")
    private List<SmartMatch> matches;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<SmartMatch> getMatches() {
        return matches;
    }

    public void setMatches(List<SmartMatch> matches) {
        this.matches = matches;
    }
}

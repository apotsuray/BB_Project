package ru.bets.project.models.smarttablesmodels;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "tournament")
public class Tournament {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "full_name")
    private String fullName;
    @Column(name = "top")
    private int top;

    @OneToMany(mappedBy = "tournament")
    private List<Referee> referee;
    @OneToMany(mappedBy = "tournament")
    private List<Team> teams;
    @OneToMany(mappedBy = "tournament")
    private List<SmartMatch> smartMatches;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getTop() {
        return top;
    }

    public void setTop(int top) {
        this.top = top;
    }

    public List<Referee> getReferee() {
        return referee;
    }

    public void setReferee(List<Referee> referee) {
        this.referee = referee;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public List<SmartMatch> getSmartMatches() {
        return smartMatches;
    }

    public void setSmartMatches(List<SmartMatch> smartMatches) {
        this.smartMatches = smartMatches;
    }
}

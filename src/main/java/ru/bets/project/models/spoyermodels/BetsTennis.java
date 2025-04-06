package ru.bets.project.models.spoyermodels;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@Entity
@Table(name = "bets_tennis")
public class BetsTennis {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "namehome")
    @Size(min = 2, max = 30)
    private String nameHome;
    @Column(name = "nameaway")
    @Size(min = 2, max = 30)
    private String nameAway;
    @Column(name = "totalgamehome")
    private int totalGameHome;
    @Column(name = "percenttotalhome")
    private double percentTotalHome;
    @Column(name = "totalgameaway")
    private int totalGameAway;
    @Column(name = "percenttotalaway")
    private double percentTotalAway;
    @Column(name = "totalh2h")
    private int totalh2h;
    @Column(name = "percenth2hhome")
    private double percenth2hHome;
    @Column(name = "percenth2haway")
    private double percenth2hAway;
    @Column(name = "date")
    private LocalDate date;
    @Column(name = "type")
    @Size(min = 2, max = 15)
    private String type;
    @Column(name = "who")
    @Size(min = 2, max = 10)
    private String who;
    @Column(name = "value")
    private double value;
    @Column(name = "odd")
    private double odd;
    @Column(name = "roi")
    private double roi;
    @Column(name = "result")
    @Size(min = 2, max = 20)
    private String result;

    public BetsTennis(String nameHome, String nameAway, int totalGameHome, double percentTotalHome,
                      int totalGameAway, double percentTotalAway, int totalh2h, double percenth2hHome,
                      double percenth2hAway, LocalDate date, String type, String who, double value,
                      double odd, double roi) {
        this.nameHome = nameHome;
        this.nameAway = nameAway;
        this.totalGameHome = totalGameHome;
        this.percentTotalHome = percentTotalHome;
        this.totalGameAway = totalGameAway;
        this.percentTotalAway = percentTotalAway;
        this.totalh2h = totalh2h;
        this.percenth2hHome = percenth2hHome;
        this.percenth2hAway = percenth2hAway;
        this.date = date;
        this.type = type;
        this.who = who;
        this.value = value;
        this.odd = odd;
        this.roi = roi;
    }

    public BetsTennis() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNameHome() {
        return nameHome;
    }

    public void setNameHome(String nameHome) {
        this.nameHome = nameHome;
    }

    public String getNameAway() {
        return nameAway;
    }

    public void setNameAway(String nameAway) {
        this.nameAway = nameAway;
    }

    public int getTotalGameHome() {
        return totalGameHome;
    }

    public void setTotalGameHome(int totalGameHome) {
        this.totalGameHome = totalGameHome;
    }

    public double getPercentTotalHome() {
        return percentTotalHome;
    }

    public void setPercentTotalHome(double percentTotalHome) {
        this.percentTotalHome = percentTotalHome;
    }

    public int getTotalGameAway() {
        return totalGameAway;
    }

    public void setTotalGameAway(int totalGameAway) {
        this.totalGameAway = totalGameAway;
    }

    public double getPercentTotalAway() {
        return percentTotalAway;
    }

    public void setPercentTotalAway(double percentTotalAway) {
        this.percentTotalAway = percentTotalAway;
    }

    public int getTotalh2h() {
        return totalh2h;
    }

    public void setTotalh2h(int totalh2h) {
        this.totalh2h = totalh2h;
    }

    public double getPercenth2hHome() {
        return percenth2hHome;
    }

    public void setPercenth2hHome(double percenth2hHome) {
        this.percenth2hHome = percenth2hHome;
    }

    public double getPercenth2hAway() {
        return percenth2hAway;
    }

    public void setPercenth2hAway(double percenth2hAway) {
        this.percenth2hAway = percenth2hAway;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getOdd() {
        return odd;
    }

    public void setOdd(double odd) {
        this.odd = odd;
    }

    public double getRoi() {
        return roi;
    }

    public void setRoi(double roi) {
        this.roi = roi;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}

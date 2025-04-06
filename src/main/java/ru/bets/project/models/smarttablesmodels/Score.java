package ru.bets.project.models.smarttablesmodels;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "score")
public class Score {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "home")
    private int home;

    @Column(name = "away")
    private int away;

    @Column(name = "corner_home")
    private int cornerHome;

    @Column(name = "corner_away")
    private int cornerAway;

    @Column(name = "foul_home")
    private int foulHome;

    @Column(name = "foul_away")
    private int foulAway;

    @Column(name = "yellow_card_home")
    private Integer yellowCardHome;

    @Column(name = "yellow_card_away")
    private Integer yellowCardAway;

    @Column(name = "red_card_home")
    private Integer redCardHome;

    @Column(name = "red_card_away")
    private Integer redCardAway;

    @Column(name = "shot_on_target_home")
    private Integer shotOnTargetHome;

    @Column(name = "shot_on_targer_away")
    private Integer shotOnTargerAway;

    @Column(name = "offside_home")
    private Integer offsideHome;

    @Column(name = "offside_away")
    private Integer offsideAway;

    @Column(name = "throwin_home")
    private Integer throwinHome;

    @Column(name = "throwin_away")
    private Integer throwinAway;

    @Column(name = "goal_kick_home")
    private Integer goalKickHome;

    @Column(name = "goal_kick_away")
    private Integer goalKickAway;

    @Column(name = "shotongoal_home")
    private Integer shotongoalHome;

    @Column(name = "shotongoal_away")
    private Integer shotongoalAway;

    @Column(name = "pass_home")
    private Integer passHome;

    @Column(name = "pass_away")
    private Integer passAway;

    @Column(name = "penalty_home")
    private Integer penaltyHome;

    @Column(name = "penalty_away")
    private Integer penaltyAway;

    @Column(name = "blocked_shots_home")
    private Integer blockedShotsHome;

    @Column(name = "blocked_shots_away")
    private Integer blockedShotsAway;

    @Column(name = "possession_home")
    private Integer possessionHome;

    @Column(name = "possession_away")
    private Integer possessionAway;

    @Column(name = "accurate_passes_home")
    private Integer accuratePassesHome;

    @Column(name = "accurate_passes_away")
    private Integer accuratePassesAway;

    @Column(name = "passing_accuracy_home")
    private Integer passingAccuracyHome;

    @Column(name = "passing_accuracy_away")
    private Integer passingAccuracyAway;

    @Column(name = "dribbling_home")
    private Integer dribblingHome;

    @Column(name = "dribbling_away")
    private Integer dribblingAway;

    @Column(name = "air_fight_home")
    private Integer airFightHome;

    @Column(name = "air_fight_away")
    private Integer airFightAway;

    @Column(name = "air_fight_success_home")
    private Integer airFightSuccessHome;

    @Column(name = "air_fight_success_away")
    private Integer airFightSuccessAway;

    @Column(name = "tackle_home")
    private Integer tackleHome;

    @Column(name = "tackle_away")
    private Integer tackleAway;

    @Column(name = "tackle_succes_home")
    private Integer tackleSuccesHome;

    @Column(name = "tackle_succes_away")
    private Integer tackleSuccesAway;

    @Column(name = "interception_home")
    private Integer interceptionHome;

    @Column(name = "interception_away")
    private Integer interceptionAway;

    @Column(name = "substitution_home")
    private Integer substitutionHome;

    @Column(name = "substitution_away")
    private Integer substitutionAway;

    @Column(name = "free_kick_home")
    private Integer freeKickHome;

    @Column(name = "free_kick_away")
    private Integer freeKickAway;

    @Column(name = "saves_home")
    private Integer savesHome;

    @Column(name = "saves_away")
    private Integer savesAway;
    @OneToMany(mappedBy = "score")
    private List<SmartMatch> score;
    @OneToMany(mappedBy = "scoreFirstTime")
    private List<SmartMatch> scoreFirstTime;
    @OneToMany(mappedBy = "scoreSecondTime")
    private List<SmartMatch> scoreSecondTime;
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHome() {
        return home;
    }

    public void setHome(int home) {
        this.home = home;
    }

    public int getAway() {
        return away;
    }

    public void setAway(int away) {
        this.away = away;
    }

    public int getCornerHome() {
        return cornerHome;
    }

    public void setCornerHome(int cornerHome) {
        this.cornerHome = cornerHome;
    }

    public int getCornerAway() {
        return cornerAway;
    }

    public void setCornerAway(int cornerAway) {
        this.cornerAway = cornerAway;
    }

    public int getFoulHome() {
        return foulHome;
    }

    public void setFoulHome(int foulHome) {
        this.foulHome = foulHome;
    }

    public int getFoulAway() {
        return foulAway;
    }

    public void setFoulAway(int foulAway) {
        this.foulAway = foulAway;
    }

    public Integer getYellowCardHome() {
        return yellowCardHome;
    }

    public void setYellowCardHome(Integer yellowCardHome) {
        this.yellowCardHome = yellowCardHome;
    }

    public Integer getYellowCardAway() {
        return yellowCardAway;
    }

    public void setYellowCardAway(Integer yellowCardAway) {
        this.yellowCardAway = yellowCardAway;
    }

    public Integer getRedCardHome() {
        return redCardHome;
    }

    public void setRedCardHome(Integer redCardHome) {
        this.redCardHome = redCardHome;
    }

    public Integer getRedCardAway() {
        return redCardAway;
    }

    public void setRedCardAway(Integer redCardAway) {
        this.redCardAway = redCardAway;
    }

    public Integer getShotOnTargetHome() {
        return shotOnTargetHome;
    }

    public void setShotOnTargetHome(Integer shotOnTargetHome) {
        this.shotOnTargetHome = shotOnTargetHome;
    }

    public Integer getShotOnTargerAway() {
        return shotOnTargerAway;
    }

    public void setShotOnTargerAway(Integer shotOnTargerAway) {
        this.shotOnTargerAway = shotOnTargerAway;
    }

    public Integer getOffsideHome() {
        return offsideHome;
    }

    public void setOffsideHome(Integer offsideHome) {
        this.offsideHome = offsideHome;
    }

    public Integer getOffsideAway() {
        return offsideAway;
    }

    public void setOffsideAway(Integer offsideAway) {
        this.offsideAway = offsideAway;
    }

    public Integer getThrowinHome() {
        return throwinHome;
    }

    public void setThrowinHome(Integer throwinHome) {
        this.throwinHome = throwinHome;
    }

    public Integer getThrowinAway() {
        return throwinAway;
    }

    public void setThrowinAway(Integer throwinAway) {
        this.throwinAway = throwinAway;
    }

    public Integer getGoalKickHome() {
        return goalKickHome;
    }

    public void setGoalKickHome(Integer goalKickHome) {
        this.goalKickHome = goalKickHome;
    }

    public Integer getGoalKickAway() {
        return goalKickAway;
    }

    public void setGoalKickAway(Integer goalKickAway) {
        this.goalKickAway = goalKickAway;
    }

    public Integer getShotongoalHome() {
        return shotongoalHome;
    }

    public void setShotongoalHome(Integer shotongoalHome) {
        this.shotongoalHome = shotongoalHome;
    }

    public Integer getShotongoalAway() {
        return shotongoalAway;
    }

    public void setShotongoalAway(Integer shotongoalAway) {
        this.shotongoalAway = shotongoalAway;
    }

    public Integer getPassHome() {
        return passHome;
    }

    public void setPassHome(Integer passHome) {
        this.passHome = passHome;
    }

    public Integer getPassAway() {
        return passAway;
    }

    public void setPassAway(Integer passAway) {
        this.passAway = passAway;
    }

    public Integer getPenaltyHome() {
        return penaltyHome;
    }

    public void setPenaltyHome(Integer penaltyHome) {
        this.penaltyHome = penaltyHome;
    }

    public Integer getPenaltyAway() {
        return penaltyAway;
    }

    public void setPenaltyAway(Integer penaltyAway) {
        this.penaltyAway = penaltyAway;
    }

    public Integer getBlockedShotsHome() {
        return blockedShotsHome;
    }

    public void setBlockedShotsHome(Integer blockedShotsHome) {
        this.blockedShotsHome = blockedShotsHome;
    }

    public Integer getBlockedShotsAway() {
        return blockedShotsAway;
    }

    public void setBlockedShotsAway(Integer blockedShotsAway) {
        this.blockedShotsAway = blockedShotsAway;
    }

    public Integer getPossessionHome() {
        return possessionHome;
    }

    public void setPossessionHome(Integer possessionHome) {
        this.possessionHome = possessionHome;
    }

    public Integer getPossessionAway() {
        return possessionAway;
    }

    public void setPossessionAway(Integer possessionAway) {
        this.possessionAway = possessionAway;
    }

    public Integer getAccuratePassesHome() {
        return accuratePassesHome;
    }

    public void setAccuratePassesHome(Integer accuratePassesHome) {
        this.accuratePassesHome = accuratePassesHome;
    }

    public Integer getAccuratePassesAway() {
        return accuratePassesAway;
    }

    public void setAccuratePassesAway(Integer accuratePassesAway) {
        this.accuratePassesAway = accuratePassesAway;
    }

    public Integer getPassingAccuracyHome() {
        return passingAccuracyHome;
    }

    public void setPassingAccuracyHome(Integer passingAccuracyHome) {
        this.passingAccuracyHome = passingAccuracyHome;
    }

    public Integer getPassingAccuracyAway() {
        return passingAccuracyAway;
    }

    public void setPassingAccuracyAway(Integer passingAccuracyAway) {
        this.passingAccuracyAway = passingAccuracyAway;
    }

    public Integer getDribblingHome() {
        return dribblingHome;
    }

    public void setDribblingHome(Integer dribblingHome) {
        this.dribblingHome = dribblingHome;
    }

    public Integer getDribblingAway() {
        return dribblingAway;
    }

    public void setDribblingAway(Integer dribblingAway) {
        this.dribblingAway = dribblingAway;
    }

    public Integer getAirFightHome() {
        return airFightHome;
    }

    public void setAirFightHome(Integer airFightHome) {
        this.airFightHome = airFightHome;
    }

    public Integer getAirFightAway() {
        return airFightAway;
    }

    public void setAirFightAway(Integer airFightAway) {
        this.airFightAway = airFightAway;
    }

    public Integer getAirFightSuccessHome() {
        return airFightSuccessHome;
    }

    public void setAirFightSuccessHome(Integer airFightSuccessHome) {
        this.airFightSuccessHome = airFightSuccessHome;
    }

    public Integer getAirFightSuccessAway() {
        return airFightSuccessAway;
    }

    public void setAirFightSuccessAway(Integer airFightSuccessAway) {
        this.airFightSuccessAway = airFightSuccessAway;
    }

    public Integer getTackleHome() {
        return tackleHome;
    }

    public void setTackleHome(Integer tackleHome) {
        this.tackleHome = tackleHome;
    }

    public Integer getTackleAway() {
        return tackleAway;
    }

    public void setTackleAway(Integer tackleAway) {
        this.tackleAway = tackleAway;
    }

    public Integer getTackleSuccesHome() {
        return tackleSuccesHome;
    }

    public void setTackleSuccesHome(Integer tackleSuccesHome) {
        this.tackleSuccesHome = tackleSuccesHome;
    }

    public Integer getTackleSuccesAway() {
        return tackleSuccesAway;
    }

    public void setTackleSuccesAway(Integer tackleSuccesAway) {
        this.tackleSuccesAway = tackleSuccesAway;
    }

    public Integer getInterceptionHome() {
        return interceptionHome;
    }

    public void setInterceptionHome(Integer interceptionHome) {
        this.interceptionHome = interceptionHome;
    }

    public Integer getInterceptionAway() {
        return interceptionAway;
    }

    public void setInterceptionAway(Integer interceptionAway) {
        this.interceptionAway = interceptionAway;
    }

    public Integer getSubstitutionHome() {
        return substitutionHome;
    }

    public void setSubstitutionHome(Integer substitutionHome) {
        this.substitutionHome = substitutionHome;
    }

    public Integer getSubstitutionAway() {
        return substitutionAway;
    }

    public void setSubstitutionAway(Integer substitutionAway) {
        this.substitutionAway = substitutionAway;
    }

    public Integer getFreeKickHome() {
        return freeKickHome;
    }

    public void setFreeKickHome(Integer freeKickHome) {
        this.freeKickHome = freeKickHome;
    }

    public Integer getFreeKickAway() {
        return freeKickAway;
    }

    public void setFreeKickAway(Integer freeKickAway) {
        this.freeKickAway = freeKickAway;
    }

    public Integer getSavesHome() {
        return savesHome;
    }

    public void setSavesHome(Integer savesHome) {
        this.savesHome = savesHome;
    }

    public Integer getSavesAway() {
        return savesAway;
    }

    public void setSavesAway(Integer savesAway) {
        this.savesAway = savesAway;
    }

    public List<SmartMatch> getScore() {
        return score;
    }

    public void setScore(List<SmartMatch> score) {
        this.score = score;
    }

    public List<SmartMatch> getScoreFirstTime() {
        return scoreFirstTime;
    }

    public void setScoreFirstTime(List<SmartMatch> scoreFirstTime) {
        this.scoreFirstTime = scoreFirstTime;
    }

    public List<SmartMatch> getScoreSecondTime() {
        return scoreSecondTime;
    }

    public void setScoreSecondTime(List<SmartMatch> scoreSecondTime) {
        this.scoreSecondTime = scoreSecondTime;
    }
}

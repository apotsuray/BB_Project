package ru.bets.project.spoyer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bets.project.matches.Match;
import ru.bets.project.matches.Match1xbet;
import ru.bets.project.matches.MatchObserving;
import ru.bets.project.matches.MatchTennis365;
import ru.bets.project.spoyer.assist.Calculate;
import ru.bets.project.spoyer.assist.InsertBet;
import ru.bets.project.telegram.Telegram;

import java.time.LocalDate;
import java.util.List;
@Component
public class Observer {
    private final InsertBet insertBet;
    private final Calculate calculate;
    private final Telegram telegram;
    @Autowired
    public Observer(InsertBet insertBet, Calculate calculate, Telegram telegram) {
        this.insertBet = insertBet;
        this.calculate = calculate;
        this.telegram = telegram;
    }

    public void observe(List<MatchTennis365> listMatchesBet365, List<String> listNames365, List<Match1xbet> listMatches1x, List<String> listNames1x) {
        for (MatchTennis365 matchTennis365 : listMatchesBet365) {
            for (int i = 0; i < listNames365.size(); i++) {
                if (matchTennis365.getTeamHome().equals(listNames365.get(i))) {
                    for (Match1xbet matche1x : listMatches1x) {
                        if (matche1x.getTeamHome().equals(listNames1x.get(i))) {
                            MatchObserving matchObservingBet365 = new MatchObserving(matchTennis365);
                            MatchObserving matchObserving1x = new MatchObserving(matche1x);
                            matchObserving1x.setOver95();
                            matchObserving1x.setUnder95();
                            matchObserving1x.setH1Minus25();
                            matchObserving1x.setH1Plus25();
                            matchObserving1x.setH2Minus25();
                            matchObserving1x.setH2Plus25();
                            prepareInfo(matchObservingBet365, matchObserving1x);
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }
    private void prepareInfo(MatchObserving matchObservingBet365, MatchObserving matchObserving1x){
        Roi roi = new Roi(matchObservingBet365,matchObserving1x);
        if (roi.haveGoodRoi() &&
                !matchObservingBet365.getMatchTennis365().getLeague().contains("WTA") &&
                !matchObservingBet365.getMatchTennis365().getLeague().contains("ITF")) {
            calculate.funcAddServeToPlayer(matchObservingBet365.getMatchTennis365().getTeamHome());
            calculate.funcAddServeToPlayer(matchObservingBet365.getMatchTennis365().getTeamAway());
            String info = calculate.funcCalculateServer(matchObservingBet365.getMatchTennis365().getTeamHome(), matchObservingBet365.getMatchTennis365().getTeamAway(), matchObservingBet365.getMatchTennis365().getLeague());
            StringBuilder head = new StringBuilder();
            head.append(matchObservingBet365.getMatchTennis365().getDate()).append('\n');
            head.append(matchObservingBet365.getMatchTennis365().getTeamHome()).append(" v ").append(matchObservingBet365.getMatchTennis365().getTeamAway()).append('\n');
            head.append(matchObserving1x.getMatch1xbet().getTeamHome()).append(" v ").append(matchObserving1x.getMatch1xbet().getTeamAway()).append('\n');
            head.append(info);
            String sendToStat = head.toString();
            int index = sendToStat.indexOf("покрытие: ") + 10;
            StringBuilder ground = new StringBuilder();
            while (sendToStat.charAt(index) != '\n') {
                ground.append(sendToStat.charAt(index));
                index++;
            }
            findGoodRoi(matchObservingBet365,matchObserving1x,roi,sendToStat, ground.toString());

        }
    }
    private void findGoodRoi(MatchObserving matchObservingBet365,MatchObserving matchObserving1x, Roi roi,String sendToStat, String ground) {
        if (roi.isGoodRoi(roi.getRoih1Minus25())) {
            buildBet(sendToStat, "roi -2.5 home", matchObservingBet365.getMatchTennis365().getDate(),
                    "Handicap", "Home", -2.5, matchObserving1x.getH1Minus25(), roi.getRoih1Minus25(), ground);
        }
        if (roi.isGoodRoi(roi.getRoih1Plus25())) {
            buildBet(sendToStat, "roi +2.5 home", matchObservingBet365.getMatchTennis365().getDate(),
                    "Handicap", "Home", 2.5, matchObserving1x.getH1Plus25(), roi.getRoih1Plus25(),  ground);
        }
        if (roi.isGoodRoi(roi.getRoih2Minus25())) {
            buildBet(sendToStat, "roi -2.5 away", matchObservingBet365.getMatchTennis365().getDate(),
                    "Handicap", "Away", -2.5, matchObserving1x.getH2Minus25(), roi.getRoih2Minus25(), ground);
        }
        if (roi.isGoodRoi(roi.getRoih2Plus25())) {
            buildBet(sendToStat, "roi +2.5 away", matchObservingBet365.getMatchTennis365().getDate(),
                    "Handicap", "Away", 2.5, matchObserving1x.getH2Plus25(), roi.getRoih2Plus25(),  ground);
        }
        if (roi.isGoodRoi(roi.getRoiOv95())) {
            buildBet(sendToStat, "roi over 9.5", matchObservingBet365.getMatchTennis365().getDate(),
                    "Total", "Over", 9.5, matchObserving1x.getOver95(), roi.getRoiOv95(),  ground);
        }
        if (roi.isGoodRoi(roi.getRoiUnd95())) {
            buildBet(sendToStat, "roi under 9.5", matchObservingBet365.getMatchTennis365().getDate(),
                    "Total", "Under", 9.5, matchObserving1x.getUnder95(), roi.getRoiUnd95(),  ground);
        }
        if (roi.isGoodRoi(roi.getRoiChet())) {
            buildBet(sendToStat, "roi Чёт", matchObservingBet365.getMatchTennis365().getDate(),
                    "Chetnechet", "Chet", 1, matchObserving1x.getChet(), roi.getRoiChet(),  ground);

        }
        if (roi.isGoodRoi(roi.getRoiNechet())) {
            buildBet(sendToStat, "roi Нечёт", matchObservingBet365.getMatchTennis365().getDate(),
                    "Chetnechet", "Nechet", 1, matchObserving1x.getNechet(), roi.getRoiNechet(), ground);
        }
    }
    private void buildBet(String sendToStat, String roiType,LocalDate date,
                String type, String who, double value, double odd,double roi, String ground){

        insertBet.insertBet(sendToStat + roiType+ ": " + roi + " кэф " + odd + "\n",
                date, type, who, value, odd, roi);
        sendToStat += roiType + ": " + roi + " кэф " + odd;
        sendToStat += calculate.goodDopInfo(ground, type, who, value, odd);
        telegram.sendMessageToChat(sendToStat);
    }
}

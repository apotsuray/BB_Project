package ru.bets.project.spoyer.assist;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.bets.project.models.spoyermodels.BetsTennis;
import ru.bets.project.services.spoyerservices.BetsTennisService;

import java.time.LocalDate;
@Component
public class InsertBet {
    private final BetsTennisService betsTennisService;

    @Autowired
    public InsertBet(BetsTennisService betsTennisService) {
        this.betsTennisService = betsTennisService;
    }

    public void insertBet(String info, LocalDate date, String type, String who, double value, double odds, double roi) {
        StringBuilder stringBuilder = new StringBuilder();

        int index = info.indexOf("\n") + 1;
        index = info.indexOf("\n", index) + 1;

        int indxhelp = info.indexOf(" v ", index);
        StringBuilder home = new StringBuilder();
        while (index < indxhelp) {
            home.append(info.charAt(index));
            index++;
        }
        index += 3;
        indxhelp = info.indexOf('\n', indxhelp);
        StringBuilder away = new StringBuilder();
        while (index < indxhelp) {
            away.append(info.charAt(index));
            index++;
        }
        /*
         *
         * Отсюда идем по стате домашнего
         *
         */
        index = buildStringMoveDigit(info, stringBuilder, index, 7);
        int totalHome = Integer.parseInt(stringBuilder.toString());

        while (!Character.isDigit(info.charAt(index))) {
            index++;
        }
        index = buildString(info, stringBuilder, '.', index, 0);
        double percentTH = Double.parseDouble(stringBuilder.toString());

        index = buildString(info, stringBuilder, '.', index, 2);
        double percent20H = Double.parseDouble(stringBuilder.toString());

        index = buildString(info, stringBuilder, '\n', index, 2);
        double percent5H = Double.parseDouble(stringBuilder.toString());

        /*
         *
         * Отсюда идем по стате гостя
         *
         */
        index = buildStringMoveDigit(info, stringBuilder, index, 7);
        int totalAway = Integer.parseInt(stringBuilder.toString());

        while (!Character.isDigit(info.charAt(index))) {
            index++;
        }
        index = buildString(info, stringBuilder, '.', index, 0);
        double percentTA = Double.parseDouble(stringBuilder.toString());

        index = buildString(info, stringBuilder, '.', index, 2);
        double percent20A = Double.parseDouble(stringBuilder.toString());

        index = buildString(info, stringBuilder, '\n', index, 2);
        double percent5A = Double.parseDouble(stringBuilder.toString());

        /*
         *
         * H2H
         *
         */

        index = buildStringMoveDigit(info, stringBuilder, index, 7);
        int totalH2H = Integer.parseInt(stringBuilder.toString());

        index = buildString(info, stringBuilder, ' ', index, 2);
        double percentH2HH = Double.parseDouble(stringBuilder.toString());

        index = buildString(info, stringBuilder, '\n', index, 2);
        double percentH2HA = Double.parseDouble(stringBuilder.toString());



        /*
         *
         * Покрытие
         *
         */


        betsTennisService.save(new BetsTennis(home.toString(), away.toString(), totalHome, percentTH, totalAway, percentTA, totalH2H, percentH2HH,
                percentH2HA, date, type, who, value, odds, roi));
    }
    private int buildString(String info, StringBuilder str,char ch, int index, int moveCarriage) {
        str.setLength(0);
        if (moveCarriage == 2) {
            index = info.indexOf(": ", index);
            index += 2;
        }
        while (info.charAt(index) != ch) {
            if (info.charAt(index) == ',') {
                str.append('.');
            } else {
                str.append(info.charAt(index));
            }
            index++;
        }
        return index;
    }
    private int buildStringMoveDigit(String info, StringBuilder str,int index,int moveCarriage) {
        str.setLength(0);
        if (moveCarriage == 7) {
            index = info.indexOf("Всего: ", index);
            index += 7;
        }
        while (Character.isDigit(info.charAt(index))) {
            str.append(info.charAt(index));
            index++;
        }
        return index;
    }
}

package ru.bets.project.smallbets.algorithms;

import ru.bets.project.smallbets.SharedState;
import ru.bets.project.smallbets.fonmatch.FonMatch;
import ru.bets.project.smallbets.fonmatch.FonTournament;
import ru.bets.project.telegram.Telegram;

import java.util.ArrayList;
import java.util.List;

public class AlgorithmGoalKickUnder implements Runnable {
    private final SharedState sharedState;
    private final int threadIndex;
    private static List<CheckingMatchSmalls> checkingMatches;

    public AlgorithmGoalKickUnder(SharedState sharedState, int threadIndex) {
        this.sharedState = sharedState;
        this.threadIndex = threadIndex;
        checkingMatches = new ArrayList<>();
    }

    @Override
    public void run() {

        while (!Thread.currentThread().isInterrupted()) {
            try {
                sharedState.waitForParserThread(threadIndex);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            for (int i = 0; i < checkingMatches.size(); i++) {
                checkingMatches.get(i).lifetime--;
                label:
                for (FonTournament fonTournament : sharedState.getListTournament()) {
                    if (fonTournament.isHaveSmall()) {
                        for (FonMatch fonMatch : fonTournament.getMatches()) {
                            if (fonMatch.getGoalKick() != null && !fonMatch.getGoalKick().getTotal().isEmpty()) {
                                if (checkingMatches.get(i).tournamentName.equals(fonTournament.getName()) &&
                                        checkingMatches.get(i).matchName.equals(fonMatch.getName())) {
                                    int zapas = (int) fonMatch.getGoalKick().getTotal().get(1).value;
                                    zapas = zapas - (fonMatch.getGoalKick().getScore1() + fonMatch.getGoalKick().getScore2());
                                    double itog = zapas * checkingMatches.get(i).mean + fonMatch.getDoubleTime();

                                    if (itog - 90 >= checkingMatches.get(i).advantage) {
                                        StringBuilder stringBuilder = new StringBuilder();
                                        stringBuilder.append("🥅 ")
                                                .append(fonTournament.getName()).append('\n')
                                                .append(fonMatch.getName()).append('\n')
                                                .append("Ставка: Тотал уотв меньше ")
                                                .append(fonMatch.getGoalKick().getTotal().get(1).value).append(" за ")
                                                .append(fonMatch.getGoalKick().getTotal().get(1).odds).append('\n')
                                                .append("Время матча: ").append(fonMatch.getTime()).append('\n')
                                                .append("Счет уотв: ").append(fonMatch.getGoalKick().getScore1()).append(" : ")
                                                .append(fonMatch.getGoalKick().getScore2());
                                        sharedState.getTelegram().sendMessageToChat(stringBuilder.toString());
                                           
                                            /*
                                            добавляем ставку в бд
                                            */
                                        checkingMatches.remove(i);
                                        i--;
                                        break label;
                                    }
                                }
                            }
                        }
                    }
                }

                if (i != -1 && checkingMatches.get(i).lifetime <= 0) {
                    checkingMatches.remove(i);
                    i--;
                }
            }
            sharedState.signalParserThread();
        }

    }

    public static List<CheckingMatchSmalls> getCheckingMatches() {
        return checkingMatches;
    }
}


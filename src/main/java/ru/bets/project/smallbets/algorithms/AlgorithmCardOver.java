package ru.bets.project.smallbets.algorithms;

import ru.bets.project.smallbets.SharedState;
import ru.bets.project.smallbets.fonmatch.FonMatch;
import ru.bets.project.smallbets.fonmatch.FonTournament;
import ru.bets.project.telegram.Telegram;

import java.util.ArrayList;
import java.util.List;

public class AlgorithmCardOver implements Runnable {
    private final SharedState sharedState;
    private final int threadIndex;
    private static List<CheckingMatch> checkingMatches;

    public AlgorithmCardOver(SharedState sharedState, int threadIndex) {
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
                            if (fonMatch.getYellowCard() != null && !fonMatch.getYellowCard().getTotal().isEmpty()) {
                                if (checkingMatches.get(i).tournamentName.equals(fonTournament.getName()) && checkingMatches.get(i).matchName.equals(fonMatch.getName())) {
                                    if ((fonMatch.getYellowCard().getTotal().get(0).value <= checkingMatches.get(i).value && fonMatch.getYellowCard().getTotal().get(0).odds >= checkingMatches.get(i).odds)
                                            || fonMatch.getYellowCard().getTotal().get(0).value < checkingMatches.get(i).value) {
                                        StringBuilder stringBuilder = new StringBuilder();
                                        stringBuilder.append("🟨 ")
                                                .append(fonTournament.getName()).append('\n')
                                                .append(fonMatch.getName()).append('\n')
                                                .append("Ставка: Тотал желтых карточек больше ")
                                                .append(fonMatch.getYellowCard().getTotal().get(0).value).append(" за ")
                                                .append(fonMatch.getYellowCard().getTotal().get(0).odds).append('\n')
                                                .append("Время матча: ").append(fonMatch.getTime()).append('\n')
                                                .append("Счет жк: ").append(fonMatch.getYellowCard().getScore1()).append(" : ")
                                                .append(fonMatch.getYellowCard().getScore2());
                                        sharedState.getTelegram().sendMessageToChat(stringBuilder.toString());
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

    public static List<CheckingMatch> getCheckingMatches() {
        return checkingMatches;
    }
}

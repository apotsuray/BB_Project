package ru.bets.project.smallbets;

import org.openqa.selenium.WebElement;
import ru.bets.project.smallbets.fonmatch.FonMatch;
import ru.bets.project.smallbets.workwithinfo.*;
import ru.bets.project.smallbets.fonmatch.FonYellowCard;

import java.util.List;
import java.util.Set;

public class BuilderTournament implements Runnable {
    private FonMatch match;
    private String nameTournament;
    private String nameMatch;
    private String time;
    private int n;
    private int indexTournament;
    private FonYellowCard y;
    private SharedState sharedState;
    private List<WebElement> eventsElement;
    private final Set<String> matheshWithYellowCard;

    private final Set<FonMatchWithMeanFoulFirstTime> mathesWithFoulFirstTime;
    private final Set<FonMatchWithMeanFoul> mathesWithFoul;
    private final Set<FonMatchWithMeanThrowInFirstTime> mathesWithThrowInFirstTime;
    private final Set<FonMatchWithMeanThrowIn> mathesWithThrowIn;
    private final Set<FonMatchWithMeanGoalKickFirstTime> mathesWithGoalKickFirstTime;
    private final Set<FonMatchWithMeanGoalKick> mathesWithGoalKick;

    public BuilderTournament(int n, int indexTournament, SharedState sharedState, List<WebElement> eventsElement, Set<String> matheshWithYellowCard, Set<FonMatchWithMeanFoulFirstTime> mathesWithFoulFirstTime, Set<FonMatchWithMeanFoul> mathesWithFoul, Set<FonMatchWithMeanThrowInFirstTime> mathesWithThrowInFirstTime, Set<FonMatchWithMeanThrowIn> mathesWithThrowIn, Set<FonMatchWithMeanGoalKickFirstTime> mathesWithGoalKickFirstTime, Set<FonMatchWithMeanGoalKick> mathesWithGoalKick) {
        this.n = n;
        this.indexTournament = indexTournament;
        this.sharedState = sharedState;
        this.eventsElement = eventsElement;
        this.matheshWithYellowCard = matheshWithYellowCard;
        this.mathesWithFoulFirstTime = mathesWithFoulFirstTime;
        this.mathesWithFoul = mathesWithFoul;
        this.mathesWithThrowInFirstTime = mathesWithThrowInFirstTime;
        this.mathesWithThrowIn = mathesWithThrowIn;
        this.mathesWithGoalKickFirstTime = mathesWithGoalKickFirstTime;
        this.mathesWithGoalKick = mathesWithGoalKick;
    }

    @Override
    public void run() {

        String name;
        int indexMatch = -1;
        for (int i = n; i < eventsElement.size(); i += 16) {
            try {
                name = getName(eventsElement.get(i).getText());
            } catch (Exception e) {
                continue;
            }
            if (name.equals("1")) //если нашли турнир,значит следующие матчи будут принадлежать этому турниру
            {
                break;
            }
            if (isMatchFromTournament(name, indexTournament))         //если в строке название команд, то этот матч добавляется к турниру
            {
                indexMatch++;
                i++;
                String[] matchOdds=buildOdd(i);
                sharedState.getListTournament().get(indexTournament).getMatches().get(indexMatch).setOdds(matchOdds);
                i--;
                continue;
            }
            switch (name) {
                case "жёлтые карты": {
                    i++;
                    String[] yellowCardOdds=buildOdd(i);
                    try {
                        sharedState.getListTournament().get(indexTournament).getMatches().get(indexMatch).setYellowCard(yellowCardOdds, getScoreMatch(eventsElement.get(i - 1).getText()));

                        synchronized (matheshWithYellowCard) {
                            if (!matheshWithYellowCard.contains(sharedState.getListTournament().get(indexTournament).getMatches().get(indexMatch).getName())) {
                                sharedState.incrementActiveWorkWithInfoThreads();
                                FindFilter findFilter = new FindFilter(sharedState.getListTournament().get(indexTournament).getName(),
                                        sharedState.getListTournament().get(indexTournament).getMatches().get(indexMatch).getName(),
                                        sharedState.getListTournament().get(indexTournament).getMatches().get(indexMatch).getTime(),
                                        sharedState.getListTournament().get(indexTournament).getMatches().get(indexMatch).getYellowCard(),
                                        sharedState.getListTournament().get(indexTournament).getMatches().get(indexMatch),
                                        sharedState);
                                new Thread(findFilter).start();
                                matheshWithYellowCard.add(sharedState.getListTournament().get(indexTournament).getMatches().get(indexMatch).getName());
                            }
                        }
                    } catch (Exception e) {
                    }
                    i--;
                    break;
                }
                case "1-й тайм жёлтые карты": {
                    i++;
                    String[] yellowCardOdds=buildOdd(i);
                    try {
                        sharedState.getListTournament().get(indexTournament).getMatches().get(indexMatch).setYellowCard1stTime(yellowCardOdds, getScoreFirstTime(eventsElement.get(i - 1).getText()));
                        i--;
                    } catch (Exception e) {
                        i--;
                    }
                    break;
                }
                case "угловые": {
                    //угловые
                    break;
                }
                case "1-й тайм угловые": {
                    //1-й тайм угловые
                    break;
                }
                case "фолы": {
                    i++;
                    String[] foulOdds=buildOdd(i);
                    try {
                        sharedState.getListTournament().get(indexTournament).getMatches().get(indexMatch).setFoul(foulOdds, getScoreMatch(eventsElement.get(i - 1).getText()));
                        i--;
                        if (isTimeout(eventsElement.get(i).getText(), sharedState.getListTournament().get(indexTournament).getMatches().get(indexMatch)))    //если в этом матче перерыв, то можем посчитать фолы
                        {
                            FonMatchWithMeanFoul matchWithMeanFoul = new FonMatchWithMeanFoul(sharedState.getListTournament().get(indexTournament).getMatches().get(indexMatch), sharedState, mathesWithFoul);
                            if (!mathesWithFoul.contains(matchWithMeanFoul)) {
                                sharedState.incrementActiveWorkWithInfoThreads();
                                new Thread(matchWithMeanFoul).start();

                            }
                        }
                    } catch (Exception e) {
                        i--;
                    }
                    break;
                }
                case "1-й тайм фолы": {
                    i++;
                    String[] foulOdds=buildOdd(i);
                    try {
                        sharedState.getListTournament().get(indexTournament).getMatches().get(indexMatch).setFoul1stTime(foulOdds, getScoreFirstTime(eventsElement.get(i - 1).getText()));
                        i--;
                        FonMatchWithMeanFoulFirstTime fonMatchWithMeanFoulFirstTime = new FonMatchWithMeanFoulFirstTime(sharedState.getListTournament().get(indexTournament).getMatches().get(indexMatch), sharedState, mathesWithFoulFirstTime);
                        if (!mathesWithFoulFirstTime.contains(fonMatchWithMeanFoulFirstTime)) {
                            sharedState.incrementActiveWorkWithInfoThreads();
                            new Thread(fonMatchWithMeanFoulFirstTime).start();
                        }
                    } catch (Exception e) {
                        i--;
                    }
                    break;
                }
                case "вброс аутов": {
                    i++;
                    String[] throwInOdds=buildOdd(i);
                    try {
                        sharedState.getListTournament().get(indexTournament).getMatches().get(indexMatch).setThrowIn(throwInOdds, getScoreMatch(eventsElement.get(i - 1).getText()));
                        i--;
                        if (isTimeout(eventsElement.get(i).getText(), sharedState.getListTournament().get(indexTournament).getMatches().get(indexMatch)))    //если в этом матче перерыв, то можем посчитать фолы
                        {
                            FonMatchWithMeanThrowIn fonMatchWithMeanThrowIn = new FonMatchWithMeanThrowIn(sharedState.getListTournament().get(indexTournament).getMatches().get(indexMatch), sharedState, mathesWithThrowIn);
                            if (!mathesWithThrowIn.contains(fonMatchWithMeanThrowIn)) {
                                sharedState.incrementActiveWorkWithInfoThreads();
                                new Thread(fonMatchWithMeanThrowIn).start();
                            }
                        }
                    } catch (Exception e) {
                        i--;
                    }
                    break;
                }
                case "1-й тайм вброс аутов": {
                    i++;
                    String[] throwInOdds=buildOdd(i);
                    try {
                        sharedState.getListTournament().get(indexTournament).getMatches().get(indexMatch).setThrowIn1stTime(throwInOdds, getScoreFirstTime(eventsElement.get(i - 1).getText()));
                        i--;
                        FonMatchWithMeanThrowInFirstTime fonMatchWithMeanThrowInFirstTime = new FonMatchWithMeanThrowInFirstTime(sharedState.getListTournament().get(indexTournament).getMatches().get(indexMatch), sharedState, mathesWithThrowInFirstTime);
                        if (!mathesWithThrowInFirstTime.contains(fonMatchWithMeanThrowInFirstTime)) {
                            sharedState.incrementActiveWorkWithInfoThreads();
                            new Thread(fonMatchWithMeanThrowInFirstTime).start();
                        }
                    } catch (Exception e) {
                        i--;
                    }
                    break;
                }
                case "удары от ворот": {
                    i++;
                    String[] goalKickOdds=buildOdd(i);
                    try {
                        sharedState.getListTournament().get(indexTournament).getMatches().get(indexMatch).setGoalKick(goalKickOdds, getScoreMatch(eventsElement.get(i - 1).getText()));
                        i--;
                        if (isTimeout(eventsElement.get(i).getText(), sharedState.getListTournament().get(indexTournament).getMatches().get(indexMatch)))    //если в этом матче перерыв, то можем посчитать фолы
                        {
                            FonMatchWithMeanGoalKick fonMatchWithMeanGoalKick = new FonMatchWithMeanGoalKick(sharedState.getListTournament().get(indexTournament).getMatches().get(indexMatch), sharedState, mathesWithGoalKick);
                            if (!mathesWithGoalKick.contains(fonMatchWithMeanGoalKick)) {
                                sharedState.incrementActiveWorkWithInfoThreads();
                                new Thread(fonMatchWithMeanGoalKick).start();
                            }
                        }
                    } catch (Exception e) {
                        i--;
                    }
                    break;
                }
                case "1-й тайм удары от ворот": {
                    i++;
                    String[] goalKickOdds=buildOdd(i);
                    try {
                        sharedState.getListTournament().get(indexTournament).getMatches().get(indexMatch).setGoalKick1stTime(goalKickOdds, getScoreFirstTime(eventsElement.get(i - 1).getText()));
                        i--;
                        FonMatchWithMeanGoalKickFirstTime fonMatchWithMeanGoalKickFirstTime = new FonMatchWithMeanGoalKickFirstTime(sharedState.getListTournament().get(indexTournament).getMatches().get(indexMatch), sharedState, mathesWithGoalKickFirstTime);
                        if (!mathesWithGoalKickFirstTime.contains(fonMatchWithMeanGoalKickFirstTime)) {
                            sharedState.incrementActiveWorkWithInfoThreads();
                            new Thread(fonMatchWithMeanGoalKickFirstTime).start();
                        }

                    } catch (Exception e) {
                        i--;
                    }
                    break;
                }
                default: {
                    break;
                }
            }
        }
        sharedState.decrementActiveWorkWithInfoThreads();
    }
    private String[] buildOdd(int i){
        String[] odds = new String[13];
        for (int j = 0; j < 13; j++) {
            try {
                odds[j] = getName(eventsElement.get(i + j).getText());
            } catch (Exception e) {
                odds[j] = "";
            }
        }
        return odds;
    }
    private boolean isMatchFromTournament(String match, int indexTournament) {
        for (int i = 0; i < sharedState.getListTournament().get(indexTournament).getMatches().size(); i++) {
            if (match.equals(sharedState.getListTournament().get(indexTournament).getMatches().get(i).getName())) {
                return true;
            }
        }
        return false;
    }

    private String getName(String fullText) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < fullText.length() && fullText.charAt(i) != '\r'; i++) {
            builder.append(fullText.charAt(i));
        }
        return builder.toString();
    }

    private String getScoreMatch(String text) {
        StringBuilder score = new StringBuilder();
        int i = 0;
        while (text.charAt(i) != '\n') {
            i++;
        }
        i++;
        while (i < text.length() && (Character.isDigit(text.charAt(i)) || text.charAt(i) == ':')) {
            score.append(text.charAt(i));
            i++;
        }
        return score.toString();
    }

    private String getScoreFirstTime(String text) {
        StringBuilder score = new StringBuilder();
        int i = 0;
        while (text.charAt(i) != '\n') {
            i++;
        }
        i++;
        while (i < text.length()) {
            score.append(text.charAt(i));
            i++;
        }
        return score.toString();
    }

    private boolean isTimeout(String s, FonMatch m) {
        return s.contains("(") && m.getTime().equals("45:00");
    }
}

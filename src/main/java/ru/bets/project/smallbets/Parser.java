package ru.bets.project.smallbets;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.bets.project.smallbets.workwithinfo.*;
import ru.bets.project.smallbets.fonmatch.FonTournament;
import ru.bets.project.telegram.Telegram;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Parser implements Runnable {
    private SharedState sharedState;
    private WebDriver browser;
    private WebDriverWait wait;
    private final Set<String> matheshWithYellowCard;
    private final Set<FonMatchWithMeanFoulFirstTime> mathesWithFoulFirstTime;
    private final Set<FonMatchWithMeanFoul> mathesWithFoul;
    private final Set<FonMatchWithMeanThrowInFirstTime> mathesWithThrowInFirstTime;
    private final Set<FonMatchWithMeanThrowIn> mathesWithThrowIn;
    private final Set<FonMatchWithMeanGoalKickFirstTime> mathesWithGoalKickFirstTime;
    private final Set<FonMatchWithMeanGoalKick> mathesWithGoalKick;
    public Parser(SharedState sharedState) {
        this.sharedState = sharedState;
        this.matheshWithYellowCard = Collections.synchronizedSet(new HashSet<>());
        this.mathesWithFoulFirstTime = Collections.synchronizedSet(new HashSet<>());
        this.mathesWithFoul = Collections.synchronizedSet(new HashSet<>());
        this.mathesWithThrowInFirstTime = Collections.synchronizedSet(new HashSet<>());
        this.mathesWithThrowIn = Collections.synchronizedSet(new HashSet<>());
        this.mathesWithGoalKickFirstTime = Collections.synchronizedSet(new HashSet<>());
        this.mathesWithGoalKick = Collections.synchronizedSet(new HashSet<>());
    }

    @Override
    public void run() {
        startBrowser();
        parsing();
    }

    private void startBrowser() {
        browser = new ChromeDriver();
        browser.manage().window().maximize();
        wait = new WebDriverWait(browser, java.time.Duration.ofSeconds(10));
    }

    public void parsing() {
        List<WebElement> tournamentsElement;
        List<WebElement> namesMatchElement;
        List<WebElement> eventsElement;
        while (!Thread.currentThread().isInterrupted()) {
            try {
                sharedState.waitForAlgorithmThreads();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
            sharedState.setListTournament();
            tournamentsElement = browser.findElements(By.className("table__body")).stream().toList(); //получаем список турниров
            namesMatchElement = browser.findElements(By.className("table__row")).stream().toList(); //получаем строки, из которых нужно достать название матчей
            eventsElement = browser.findElements(By.className("table__col")).stream().toList(); //получаем кучу ставок
            String name;
            for (WebElement tournamentElement : tournamentsElement) {

                try {
                    sharedState.getListTournament().add(new FonTournament(getName(tournamentElement.getText()))); //добавляем турниры в список
                } catch (Exception e) {
                    continue;
                }

            }
            int indexTournament = -1;
            for (WebElement nameMatchElement : namesMatchElement) {
                try {
                    name = getName(nameMatchElement.getText());
                } catch (Exception e) {
                    continue;
                }
                if (isTournament(name)) //если нашли турнир,значит следующие матчи будут принадлежать этому турниру
                {
                    indexTournament++;
                    continue;
                }
                if (isMatch(name))  //если в строке название команд, то этот матч добавляется к турниру
                {
                    sharedState.getListTournament().get(indexTournament).addMatch(name, getTime(nameMatchElement.getText()));
                    continue;
                }
                if (name.equals("жёлтые карты")) {
                    sharedState.getListTournament().get(indexTournament).setHaveSmall(true);
                    continue;
                }
                if (name.equals("фолы")) {

                    sharedState.getListTournament().get(indexTournament).setHaveSmall(true);
                    continue;
                }
                if (name.equals("вброс аутов")) {

                    sharedState.getListTournament().get(indexTournament).setHaveSmall(true);
                    continue;
                }
                if (name.equals("удары от ворот")) {

                    sharedState.getListTournament().get(indexTournament).setHaveSmall(true);
                    continue;
                }
            }
            indexTournament = -1;

            for (int i = 0; i < eventsElement.size(); i += 16) {

                try {
                    name = getName(eventsElement.get(i).getText());
                    if (name.equals("1")) {
                        i--;
                        name = getName(eventsElement.get(i).getText());
                    }
                } catch (Exception e) {
                    continue;
                }
                if (isTournament(name)) //если нашли турнир,значит следующие матчи будут принадлежать этому турниру
                {
                    indexTournament++;
                    if (sharedState.getListTournament().get(indexTournament).isHaveSmall()) {
                        sharedState.incrementActiveWorkWithInfoThreads();
                        new Thread(new BuilderTournament(i, indexTournament, sharedState, eventsElement,
                                matheshWithYellowCard,
                                mathesWithFoulFirstTime, mathesWithFoul,
                                mathesWithThrowInFirstTime, mathesWithThrowIn,
                                mathesWithGoalKickFirstTime, mathesWithGoalKick)).start();

                    }
                }

            }
            try {
                sharedState.waitForWorkWithInfoThreads();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            sharedState.signalAlgorithmThreads();
        }
    }

    private String getName(String fullText) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < fullText.length() && fullText.charAt(i) != '\r'; i++) {
            builder.append(fullText.charAt(i));
        }
        return builder.toString();
    }

    private boolean isMatch(String match) {
        if (match.equals("1-й тайм") ||
                match.equals("2-й тайм") ||
                match.equals("Что раньше") ||
                match.equals("угловые") ||
                match.equals("1-й тайм угловые") ||
                match.equals("жёлтые карты") ||
                match.equals("1-й тайм жёлтые карты") ||
                match.equals("фолы") ||
                match.equals("1-й тайм фолы") ||
                match.equals("удары в створ") ||
                match.equals("1-й тайм удары в створ") ||
                match.equals("офсайды") ||
                match.equals("1-й тайм офсайды") ||
                match.equals("штанги или перекладины") ||
                match.equals("1-й тайм штанги или перекладины") ||
                match.equals("вброс аутов") ||
                match.equals("1-й тайм вброс аутов") ||
                match.equals("удары от ворот") ||
                match.equals("1-й тайм удары от ворот") ||
                match.equals("видеопросмотры") ||
                match.equals("1-й тайм видеопросмотры") ||
                match.equals("Показатели игроков. Расчет по телетрансляции НТВ+ и ФОН-ТВ"))
            return false;
        return true;
    }

    private boolean isTournament(String tournament) {
        for (int i = 0; i < sharedState.getListTournament().size(); i++) {
            if (tournament.equals(sharedState.getListTournament().get(i).getName())) {
                return true;
            }
        }
        return false;
    }

    private String getTime(String s) {
        if (s.contains("Матч не начался")) {
            return "Матч не начался";
        }
        StringBuilder time = new StringBuilder();
        int i = 0;
        try {
            while (s.charAt(i + 1) != ':') {
                i++;
            }
        } catch (StringIndexOutOfBoundsException e) {
            return "";
        }
        while (Character.isDigit(s.charAt(i - 1))) {
            i--;
        }
        while (s.charAt(i) != '\n') {
            time.append(s.charAt(i));
            i++;
        }

        return time.toString();
    }

}

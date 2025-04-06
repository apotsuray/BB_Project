package ru.bets.project.smallbets.workwithinfo;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import ru.bets.project.smallbets.SharedState;
import ru.bets.project.smallbets.algorithms.AlgorithmThrowInUnder;
import ru.bets.project.smallbets.fonmatch.FonMatch;
import ru.bets.project.smallbets.fonmatch.FonYellowCard;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FindFilter implements Runnable {
    private FonMatch match;
    private String nameTournament;
    private String nameMatch;
    private String time;
    private FonYellowCard y;
    private SharedState sharedState;

    private boolean findNameRefereeOnFlashScore;
    private String name1;
    private String name2;
    private double max = 0;
    private double v1 = 0, v2 = 0, vReferee = 0;
    private boolean findTeam1, findTeam2;
    private String nameOnFlashScore;
    private boolean findTeamOnFlashScoreFromFile;
    private String nameReferee;

    public FindFilter(String nameTournament, String nameMatch, String time, FonYellowCard y, FonMatch m, SharedState sharedState) {
        this.nameTournament = nameTournament;
        this.nameMatch = nameMatch;
        this.time = time;
        this.y = y;
        this.match = m;
        this.sharedState = sharedState;
    }

    @Override
    public void run() {

        if (nameMatch.contains("дополнительное время")) {
            return;
        }

        name1 = getName1(nameMatch);
        name2 = getName2(nameMatch);

        StringBuilder finalText = new StringBuilder();
        finalText.append(prepareFinalText());

        readYellowCardFile();
        readFlashScoreFile();

        findRefereeOnFlashScore();

        finalText.append(finishFinalText());
        sharedState.getTelegram().sendMessageToChat(finalText.toString());
        addDataBD();
    }
    private void findRefereeOnFlashScore() {
        WebDriver BrowserFlashScore;
        WebDriverWait wait;

        if (findTeamOnFlashScoreFromFile) {
            boolean findMatchOnFlashScore = false;
            BrowserFlashScore = new ChromeDriver();
            BrowserFlashScore.manage().window().maximize();
            wait = new WebDriverWait(BrowserFlashScore, java.time.Duration.ofSeconds(10));
            BrowserFlashScore.get("http://flashscore.ru/");
            wait.until((WebDriver d) -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
            try {
                WebElement iWebElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), '" + nameOnFlashScore + "')]")));
                JavascriptExecutor jse = (JavascriptExecutor) BrowserFlashScore;
                int x = iWebElement.getLocation().getX();
                int y = iWebElement.getLocation().getY() - 100;
                jse.executeScript("window.scrollBy(" + x + "," + y + ")");
                iWebElement.click();
                findMatchOnFlashScore = true;
            } catch (TimeoutException e) {
                BrowserFlashScore.close();
            }
            if (findMatchOnFlashScore) {

                BrowserFlashScore.switchTo().window(BrowserFlashScore.getWindowHandles().toArray(new String[0])[1]);
                WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("content")));
                nameReferee = getNameReferee(element.getText());
                findNameRefereeOnFlashScore = true;
                BrowserFlashScore.close();
                BrowserFlashScore.switchTo().window(BrowserFlashScore.getWindowHandles().toArray(new String[0])[0]);
                BrowserFlashScore.close();
            }
        }
    }
    private void readFlashScoreFile(){
        String filePath = "TeamFonToTeamFlashscore.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String nameFromFile = getNameFromFile(line);
                if (name1.equals(nameFromFile) || name2.equals(nameFromFile)) {
                    try {
                        nameOnFlashScore = getValueFromFile(line);
                        findTeamOnFlashScoreFromFile = true;
                    } catch (Exception e) {
                    }
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }

    private void readYellowCardFile(){
        String filePath = "YellowCardTeam.txt";
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (name1.equals(getNameFromFile(line))) {
                    try {
                        v1 = Double.parseDouble(getValueFromFile(line));
                        if (v1 > max) {
                            max = v1;
                        }
                        findTeam1 = true;
                    } catch (NumberFormatException e) {
                    }

                }
                if (name2.equals(getNameFromFile(line))) {
                    try {
                        v2 = Double.parseDouble(getValueFromFile(line));
                        if (v2 > max) {
                            max = v2;
                        }
                        findTeam2 = true;
                    } catch (NumberFormatException e) {
                    }
                }
                if (findTeam1 && findTeam2) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Ошибка при чтении файла: " + e.getMessage());
        }
    }
    private String prepareFinalText(){
        StringBuilder finalText = new StringBuilder();
        finalText.append("🔍 Появились жк в матче\n⚽️ ").append(nameTournament).append("\n🤼 ")
                .append(nameMatch)
                .append("\nВремя матча: ").append(time)
                .append("\n").append("Счет жк: ")
                .append(y.getScore1()).append(" : ").append(y.getScore2()).append("\n");
        if (y.getTotal() != null && !y.getTotal().isEmpty()) {
            finalText.append("Тотал: ").append(y.getTotal().get(0).value)
                    .append(" Б-").append(y.getTotal().get(0).odds)
                    .append(" М-").append(y.getTotal().get(1).odds).append("\n");
        }
        return finalText.toString();
    }
    private String finishFinalText(){
        StringBuilder finalText  = new StringBuilder();
        if (findTeam1) {
            finalText.append(name1).append("-").append(v1).append("\n");

        } else {
            finalText.append(name1).append(" - н/д\n");
        }
        if (findTeam2) {
            finalText.append(name2).append("-").append(v2).append("\n");
        } else {
            finalText.append(name2).append(" - н/д\n");
        }
        if (findNameRefereeOnFlashScore) {
            finalText.append(nameReferee).append("-").append(vReferee).append("\n");
        }else {
            finalText.append("Сведений о судье нет\n");
        }
        if (max == 0) {
            finalText.append("😟Нет рекомендуемой ставки. Добавьте показатели\n");
        } else {
            finalText.append("👉Рекомендуемая ставка ").append(max).append(" меньше жк\n");
            finalText.append("C поправкой на показатели:\n");
            if (!findTeam1) {
                finalText.append("👀").append(name1).append("\n");
            }
            if (!findTeam2) {
                finalText.append("👀").append(name2).append("\n");
            }
            if (!findNameRefereeOnFlashScore) {
                finalText.append("👀Судья ");
            }
        }
        return finalText.toString();
    }
    private void addDataBD() {
        /*Создаем объект модели и добавляем его в бд
                new(
                    nameTournament,
                    name1,
                    name2,
                    match.getWin1(),
                    match.getDraw(),
                    match.getWin2(),
                    y.getWin1(),
                    y.getDraw(),
                    y.getWin2(),
                    nameReferee,
                    y.getHandicap().get(0).value,
                    y.getHandicap().get(0).odds,
                    y.getHandicap().get(1).value,
                    y.getHandicap().get(1).odds,
                    y.getTotal().get(0).value,
                    y.getTotal().get(0).odds,
                    y.getTotal().get(1).value,
                    y.getTotal().get(1).odds)
               */
        //добавляем ставку в бд
    }
    private String getName1(String s) {
        try {
            int lenght = s.indexOf("-");
            lenght--; //перед "-" стоит пробел, он нам не нужен в названии команды
            StringBuilder name = new StringBuilder("");
            for (int i = 0; i < lenght; i++) {
                name.append(s.charAt(i));
            }
            return name.toString();
        } catch (Exception e) {
            return "";
        }
    }

    private String getName2(String s) {
        try {
            int lenght = s.indexOf("- ");
            if (lenght == -1) {
                return "";
            } else {
                lenght += 2;
            }
            StringBuilder value = new StringBuilder();
            for (int i = lenght; i < s.length(); i++) {
                value.append(s.charAt(i));
            }
            return value.toString();
        } catch (Exception e) {
            return "";
        }
    }

    private String getNameFromFile(String s) {
        try {
            int lenght = s.indexOf("-");
            lenght--; //перед "-" стоит пробел, он нам не нужен в названии команды
            StringBuilder name = new StringBuilder("");
            for (int i = 0; i < lenght; i++) {
                name.append(s.charAt(i));
            }
            return name.toString();
        } catch (Exception e) {
            return "";
        }
    }

    private String getValueFromFile(String s) {
        try {
            int lenght = s.indexOf("- ");
            if (lenght == -1) {
                return "0";
            } else {
                lenght += 2;
            }
            StringBuilder value = new StringBuilder();
            for (int i = lenght; i < s.length(); i++) {
                value.append(s.charAt(i));
            }
            return value.toString();
        } catch (Exception e) {
            return "0";
        }
    }

    private String getNameReferee(String s) {
        if (s.contains("Судья")) {
            try {
                int lenght = s.indexOf(",");
                StringBuilder name = new StringBuilder("");
                for (int i = 0; i < lenght; i++) {
                    name.append(s.charAt(i));
                }
                return name.toString();
            } catch (Exception e) {
            }
        }
        return "";
    }
}

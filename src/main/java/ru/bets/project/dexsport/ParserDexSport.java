package ru.bets.project.dexsport;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;
import ru.bets.project.dexsport.actions.*;
import ru.bets.project.matches.MatchFootballDEX;
import ru.bets.project.utils.Index;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class ParserDexSport extends Thread {
    private WebDriver browser;
    private WebDriverWait wait;
    @Override
    public void run() {
        parsingDexSport();
    }

    private void parsingDexSport() {
        List<MatchFootballDEX> listMatchDEX = new ArrayList<>();
        WebElement body = preparePage();

        List<String> hrefs = collectMatches(body);

        for (String href : hrefs) {
            browser.get(href);
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("games-stream-nav")));
            List<WebElement> title = browser.findElements(By.className("games-stream-nav")).stream().toList();
            List<WebElement> spans;
            try {
                spans = title.getLast().findElements(By.tagName("span")).stream().toList();
            } catch (Exception e) {
                continue;
            }
            for (WebElement span : spans) {
                if (span.getText().contains("Yellow cards")) {
                    List<WebElement> namesTeam = browser.findElements(By.className("slider-event__team-name")).stream().toList();
                    MatchFootballDEX match = new MatchFootballDEX(namesTeam.get(0).getText(), namesTeam.get(1).getText());
                    Map<String, Action> actionMap = prepareActions(match.getTeamHome(),match.getTeamAway());

                    span.click();
                    List<WebElement> events = browser.findElements(By.className("game-event__market-wrapper")).stream().toList();
                    for (WebElement eventWebElement : events) {
                        List<WebElement> buttonOdds = eventWebElement.findElements(By.className("outcome")).stream().toList();
                        String event = eventWebElement.findElement(By.xpath("div[1]/span")).getText();
                        Action action = actionMap.get(event);
                        if (action != null) {
                            action.execute(buttonOdds,match);
                        }
                    }
                    listMatchDEX.add(match);
                }
            }
            try {
                Thread.sleep(3 * 1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    private List<String> collectMatches(WebElement body){
        List<String> hrefs = new ArrayList<>();
        WebElement grid;
        List<WebElement> days;
        List<WebElement> matchs;
        grid = body.findElement(By.xpath("div[1]/div/div[3]/div"));
        days = grid.findElements(By.className("grid-line")).stream().toList();
        matchs = days.getFirst().findElements(By.xpath("div[2]/*")).stream().toList();
        WebElement btnMatch;
        for (int j = 0; j < matchs.size(); j++) {
            String s;
            try {
                btnMatch = matchs.get(j).findElement(By.xpath("div[2]/a[1]"));
            } catch (Exception e) {
                continue;
            }
            if (j % 8 == 0 && j != 0) {
                btnMatch.sendKeys(Keys.SPACE);
                grid = wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(body, By.xpath("div[1]/div/div[3]/div"))).stream().toList().getFirst();
                days = grid.findElements(By.className("grid-line")).stream().toList();
                matchs = days.getFirst().findElements(By.xpath("div[2]/*")).stream().toList();
            }
            s = btnMatch.getAttribute("href");
            hrefs.add(s);
        }
        return hrefs;
    }
    private WebElement preparePage(){
        browser  = new ChromeDriver();
        browser.manage().window().maximize();
        browser.get("https://mainnet.dexsport.io/sports");
        wait = new WebDriverWait(browser, java.time.Duration.ofSeconds(10));
        wait.until((WebDriver d) -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
        WebElement btnSkip = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("onboarding-modal-step1-block-btns-skip")));
        btnSkip.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("*/*[2]/div[1]/div[1]/div[1]/*[2]/*")));
        List<WebElement> childrenElements = browser.findElements(By.xpath("*/*[2]/div[1]/div[1]/div[1]/*[2]/*")).stream().toList(); //мы получили iframe

        WebDriver file = browser.switchTo().frame(childrenElements.getFirst());
        List<WebElement> temp = file.findElements(By.xpath("*/*")).stream().toList();
        WebElement body = temp.get(1); //внутренний body (то есть самый обычный)
        WebElement btnFootball = body.findElement(By.xpath("div[1]/div/nav/div[4]/a[1]"));
        btnFootball.click();
        WebElement btnPrematch = wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(body, By.xpath("div[1]/div/div[3]/div/div[2]/div/a[3]"))).stream().toList().get(0);
        btnPrematch.click();
        WebElement btnSort = wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(body, By.xpath("div[1]/div/div[3]/div/div[2]/div/div[2]/div[1]/div"))).stream().toList().get(0);
        btnSort.click();
        WebElement btnSortTime = wait.until(ExpectedConditions.visibilityOfNestedElementsLocatedBy(body, By.xpath("div[1]/div/div[3]/div/div[2]/div/div[2]/div[2]/div[1]"))).stream().toList().get(0);
        btnSortTime.click();
        return body;
    }
    private Map<String,Action> prepareActions(String home,String away){
        Map<String,Action> actionMap = new HashMap<>();
        actionMap.put("Yellow cards. Winner", new ActionW());
        actionMap.put("Yellow cards 1-st half. Winner", new ActionFHW());
        actionMap.put("Yellow cards 2-nd half. Winner",new ActionSHW());
        actionMap.put("Yellow cards. Double Chance",new ActionDC());
        actionMap.put("Yellow cards. Double Chance 1-st half",new ActionFHDC());
        actionMap.put("Yellow cards. Double Chance 2-nd half",new ActionSHDC());
        actionMap.put("Yellow cards. Handicap",new ActionH());
        actionMap.put("Yellow cards 1-st half. Handicap",new ActionFHH());
        actionMap.put("Yellow cards 2-nd half. Handicap",new ActionSHH());
        actionMap.put( "Yellow cards. Total",new ActionT());
        actionMap.put("Yellow cards 1-st half total", new ActionFHT());
        actionMap.put("Yellow cards 2-nd half total", new ActionSHT());
        actionMap.put("Yellow cards " + home + " total", new ActionITH());
        actionMap.put("Yellow cards " + home + " total. 1-st half", new ActionFHITH());
        actionMap.put("Yellow cards " + home + " total. 2-nd half", new ActionSHITH());
        actionMap.put("Yellow cards " + away + " total", new ActionITA());
        actionMap.put("Yellow cards " + away + " total. 1-st half", new ActionFHITA());
        actionMap.put("Yellow cards " + away + " total. 2-nd half", new ActionSHITA());


        return actionMap;
    }
}


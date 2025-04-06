package ru.bets.project.fonbet;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.stereotype.Component;
import ru.bets.project.telegram.Telegram;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
public class ParserFonbet  extends Thread{
    private volatile Set<String> trackedMatches;
    private final Telegram telegram;
    private WebDriver browser;
    private WebDriverWait wait;
    private WebElement prematchBtn;
    private WebElement liveBtn;
    public ParserFonbet(Telegram telegram) {
        this.telegram = telegram;
        trackedMatches = new HashSet<>();
    }

    @Override
    public void run() {
        startBrowser();
        switchToLive();
        parsingFonbet();
    }

    private void parsingFonbet() {
        List<String> names;
        while (!Thread.currentThread().isInterrupted()) {
            if (!trackedMatches.isEmpty()) {
                try {
                    names = new ArrayList<>();
                    boolean next = true;
                    int row;
                    for (row = 0; row < 13 && next; row++) {
                        next = false;
                        List<WebElement> hrefs = browser.findElements(By.className("sport-base-event--W4qkO")).stream().toList(); //получаем строки, из которых нужно достать название матчей
                        for (WebElement href : hrefs) {
                            if (href.getText().contains("-й"))
                                continue;
                            String name = href.getText().substring(0, href.getText().indexOf('\r'));
                            if (!names.contains(name)) {
                                names.add(name);
                                next = true;
                            }
                        }
                        ((JavascriptExecutor) browser).executeScript("arguments[0].scrollIntoView(true);", hrefs.size() - 3);
                        Thread.sleep(1000);
                    }
                    prematchBtn.click();
                    Thread.sleep(1000);
                    liveBtn.click();
                    Thread.sleep(1000);
                    for (String name : names) {
                        if (trackedMatches.contains(name)) {
                            telegram.sendMessageToChat(name);
                            trackedMatches.remove(name);
                        }
                    }
                } catch (Exception e) {
                    switchToLive(); }
            } else {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }
    private void switchToLive(){
        browser.get("https://fon.bet/live/tennis");
        wait.until((WebDriver d) -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
        liveBtn = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("/html/body/application/div[2]/div[1]/div/div/div/div[2]/div/div/div[1]/div/aside/div/div[1]/div[2]/div/div[2]/span")));
        prematchBtn = browser.findElement(By.xpath("/html/body/application/div[2]/div[1]/div/div/div/div[2]/div/div/div[1]/div/aside/div/div[1]/div[2]/div/div[3]/span"));

    }
    private void startBrowser() {
        browser = new ChromeDriver();
        browser.manage().window().maximize();
        wait = new WebDriverWait(browser, java.time.Duration.ofSeconds(10));
    }
    public Set<String> getTrackedMatches() {
        return trackedMatches;
    }
}

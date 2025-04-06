package ru.bets.project.secretbk;
import net.lightbody.bmp.BrowserMobProxy;
import net.lightbody.bmp.BrowserMobProxyServer;
import net.lightbody.bmp.proxy.auth.AuthType;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import ru.bets.project.telegram.Telegram;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@PropertySource("classpath:application.properties")
public class ParserSecretBK extends Thread {
    private volatile Set<String> trackedMatches;
    private final Telegram telegram;
    private final String ipPort;
    private final String proxyLogin;
    private final String proxyPassword;
    private final String secretURL;
    private WebDriver browser;
    private WebDriverWait wait;
    @Autowired
    public ParserSecretBK(Telegram telegram, Environment env) {
        this.telegram = telegram;
        this.trackedMatches = new HashSet<>();
        this.ipPort = env.getProperty("IP_PORT");
        this.proxyLogin = env.getProperty("PROXY_LOGIN");
        this.proxyPassword = env.getProperty("PROXY_PASSWORD");
        this.secretURL = env.getProperty("SECRET_URL");
    }

    @Override
    public void run() {
        parsingSecretBK();
    }

    private void parsingSecretBK() {
        startBrowser();
        Set<String> sentHeaders = new HashSet<>();
        List<String> href;
        List<String> matchNames;
        while (!Thread.currentThread().isInterrupted()) {
            href = new ArrayList<>();
            matchNames = new ArrayList<>();
            try {
                browser.switchTo().window(browser.getWindowHandles().toArray(new String[0])[0]);
                WebElement main = browser.findElement(By.xpath("/html/body/div[1]/div/div[2]/div[3]/div[2]"));
                List<WebElement> matchElements = main.findElements(By.tagName("a")).stream().toList();
                for (WebElement matchElement : matchElements) {
                    href.add(matchElement.getAttribute("href"));
                    matchNames.add(matchElement.getText());
                }

                for (int i = 0; i < matchNames.size(); i++) {
                    if (trackedMatches.contains(matchNames.get(i))) {
                        telegram.sendMessageToChat(matchNames.get(i));
                        ((JavascriptExecutor) browser).executeScript("window.open()");
                        browser.switchTo().window(browser.getWindowHandles().stream().toList().getLast());
                        browser.get(href.get(i));
                        trackedMatches.remove(matchNames.get(i));
                    }
                }
            } catch (Exception e) {
                switchToLive();
            }

            for (int i = 1; i < browser.getWindowHandles().size(); i++) {
                boolean closeWindow = false;
                browser.switchTo().window(browser.getWindowHandles().toArray(new String[0])[i]);

                String teamName = "";
                try {
                    String currentSet = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='eventPeriodAndScore']"))).getText();
                    teamName = browser.findElement(By.cssSelector("[data-test='teamName']")).getText();
                    if (currentSet.contains("2nd set")) {
                        closeWindow = true;
                    }

                    WebElement lineButton = browser.findElement(By.className("full-event-markets-categories-module_categoriesInner__qOgZrxvU__platform-common"));
                    List<WebElement> buttons = lineButton.findElements(By.tagName("button")).stream().toList();
                    for (WebElement button : buttons) {
                        button.click();
                        WebElement field = wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("full-event-page-module_marketsWrapper__qzsqiaDq__platform-desktop-ui")));
                        List<WebElement> headers = field.findElements(By.cssSelector("[data-test='sport-event-table-market-header']")).stream().toList();
                        for (WebElement header : headers) {
                            String textHeader = header.getText();
                            if (textHeader.contains("Total games") ||
                                    textHeader.contains("Game handicap")) {
                                String textToSent = teamName + " " + textHeader;
                                telegram.sendMessageToChat(textToSent);
                                closeWindow = true;
                            }
                            if (
                                    textHeader.contains("1st set - total games") ||
                                            textHeader.contains("1st set - game handicap") ||
                                            textHeader.contains("Any set to nil") ||
                                            textHeader.contains("total games") ||
                                            textHeader.contains("Winner & total")) {
                                String textToSent = teamName + " " + textHeader;
                                if (!sentHeaders.contains(textToSent)) {
                                    sentHeaders.add(textToSent);
                                    telegram.sendMessageToChat(textToSent);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    continue;
                }
                if (closeWindow) {
                    String finalTeamName = teamName;
                    sentHeaders.removeIf(item -> item.contains(finalTeamName));
                    browser.close();
                    i--;
                }
            }
            if (browser.getWindowHandles().size() == 1) {
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }
    }

    public Set<String> getTrackedMatches() {
        return trackedMatches;
    }
    private void switchToLive(){
        browser.switchTo().window(browser.getWindowHandles().toArray(new String[0])[0]);
        browser.get(secretURL +"/live/tennis");
        wait.until((WebDriver d) -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("platform")));
    }
    private void startBrowser(){
        BrowserMobProxy proxyServer = new BrowserMobProxyServer();
        proxyServer.start(0);
        proxyServer.autoAuthorization(ipPort, proxyLogin, proxyPassword, AuthType.BASIC);
        String proxyAddress = "localhost:" + proxyServer.getPort();
        Proxy proxy = new Proxy();
        proxy.setHttpProxy(proxyAddress);
        proxy.setSslProxy(proxyAddress);

        ChromeOptions options = new ChromeOptions();
        options.setProxy(proxy);
        browser = new ChromeDriver(options);

        browser.manage().window().maximize();
        browser.get(secretURL +"/live/tennis");

        wait = new WebDriverWait(browser, java.time.Duration.ofSeconds(10));
        wait.until((WebDriver d) -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("platform")));

        browser.findElement(By.xpath("/html/body/div[1]/div/header/div[3]/div/div[2]")).click();
        WebElement engLang = wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("[data-test='userLangSelect']")));
        engLang.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("platform")));
    }
}

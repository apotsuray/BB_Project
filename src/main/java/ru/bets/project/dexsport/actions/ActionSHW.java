package ru.bets.project.dexsport.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.bets.project.matches.MatchFootballDEX;

import java.util.List;

public class ActionSHW extends Action{
    @Override
    public void execute(List<WebElement> buttonOdds, MatchFootballDEX match) {
        String oddHome = buttonOdds.get(0).findElement(By.xpath("span[2]")).getText();
        String oddAway = buttonOdds.get(1).findElement(By.xpath("span[2]")).getText();
        String oddDraw = buttonOdds.get(2).findElement(By.xpath("span[2]")).getText();
        match.getCard2half().setWin1(Double.parseDouble(oddHome));
        match.getCard2half().setWin2(Double.parseDouble(oddAway));
        match.getCard2half().setDraw(Double.parseDouble(oddDraw));
    }
}

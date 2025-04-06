package ru.bets.project.dexsport.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.bets.project.matches.MatchFootballDEX;
import ru.bets.project.utils.Index;

import java.util.List;

public class ActionSHH extends Action{
    @Override
    public void execute(List<WebElement> buttonOdds, MatchFootballDEX match) {
        for (WebElement buttonOdd : buttonOdds) {
            String dirtyValue = buttonOdd.findElement(By.xpath("span[1]")).getText();
            int count = 4;  //нужно взять последние 4 символа
            Index index = buildNode(buttonOdd,count,dirtyValue);
            if (dirtyValue.contains(match.getTeamHome())) {
                match.getCard2half().getHandicap1().add(index);
            } else {
                match.getCard2half().getHandicap2().add(index);
            }
        }
    }
}

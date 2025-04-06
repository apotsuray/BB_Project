package ru.bets.project.dexsport.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.bets.project.matches.MatchFootballDEX;
import ru.bets.project.utils.Index;

import java.util.List;

public class ActionFHT extends Action{
    @Override
    public void execute(List<WebElement> buttonOdds, MatchFootballDEX match) {
        for (WebElement buttonOdd : buttonOdds) {
            String dirtyValue = buttonOdd.findElement(By.xpath("span[1]")).getText();
            int count = 3;  //нужно взять последние 3 символа
            Index index = buildNode(buttonOdd,count,dirtyValue);
            if (dirtyValue.contains("Over")) {
                match.getCard1half().getTotalOver().add(index);
            } else {
                match.getCard1half().getTotalUnder().add(index);
            }
        }
    }
}

package ru.bets.project.dexsport.actions;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import ru.bets.project.matches.MatchFootballDEX;
import ru.bets.project.utils.Index;

import java.util.List;

public abstract class Action {
   public abstract void execute(List<WebElement> buttonOdds, MatchFootballDEX match);

   protected Index buildNode(WebElement buttonOdd, int count, String dirtyValue) {
      String strOdd = buttonOdd.findElement(By.xpath("span[2]")).getText();
      int lenght = dirtyValue.length();
      StringBuilder strValue = new StringBuilder();
      while (count != 0) {
         strValue.append(dirtyValue.charAt(lenght - count));
         count--;
      }
      double value = Double.parseDouble(strValue.toString());
      double odd = Double.parseDouble(strOdd);
      return new Index(value, odd);
   }
}

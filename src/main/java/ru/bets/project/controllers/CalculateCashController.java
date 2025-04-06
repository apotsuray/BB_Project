package ru.bets.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bets.project.utils.LuckyDays;

@Controller

public class CalculateCashController {
    @PostMapping("/calculateLucky")
    public String calculateLucky(@ModelAttribute("luckyDays")LuckyDays luckyDays) {
        int step = 50;
        int result1,result2,result3,result4;
        double max = 100000;
        double min = -100000;
        for(int first = 0; first < luckyDays.getPrize()/4; first+=step){
            for(int second = first; second < luckyDays.getPrize()/2; second+=step){
                for(int third = second; third < luckyDays.getPrize(); third+=step){
                    result1 = (int)(first * (luckyDays.getOdd1()-1));
                    result2 = (int)(second * (luckyDays.getOdd2()-1)) - first;
                    result3 = (int)(third * (luckyDays.getOdd3()-1)) - second - first;
                    result4 = luckyDays.getPrize()-first-second-third;
                    if((max-min) > (getMax(result1,result2,result3,result4)-getMin(result1,result2,result3,result4))) {
                        max = getMax(result1,result2,result3,result4);
                        min = getMin(result1,result2,result3,result4);
                        luckyDays.setSum1(first);
                        luckyDays.setSum2(second);
                        luckyDays.setSum3(third);
                        luckyDays.setResult1(result1);
                        luckyDays.setResult2(result2);
                        luckyDays.setResult3(result3);
                        luckyDays.setResult4(result4);
                    }
                }
            }
        }
        return "/calculateFreebet";
    }
    private int getMax(int result1, int result2, int result3, int result4) {
        int max = Math.max(Math.max(Math.max(result1, result2), result3), result4);
        return max;
    }
    private int getMin(int result1, int result2, int result3, int result4) {
        int min = Math.min(Math.min(Math.min(result1, result2), result3), result4);
        return min;
    }
}

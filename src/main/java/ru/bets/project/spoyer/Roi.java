package ru.bets.project.spoyer;

import ru.bets.project.matches.MatchObserving;

import java.util.ArrayList;
import java.util.List;

public class Roi {
    private double roih1Minus25;
    private double roih1Plus25;
    private double roih2Minus25;
    private double roih2Plus25;
    private double roiOv95;
    private double roiUnd95;
    private double roiChet;
    private double roiNechet;
    private List<Double> roys;
    private final double constRoi = 1.049;
    public Roi(MatchObserving matchObservingBet365,MatchObserving matchObserving1x) {
        roih1Minus25 = matchObservingBet365.getH1Minus25() * matchObserving1x.getH1Minus25();
        roih1Plus25 = matchObservingBet365.getH1Plus25() * matchObserving1x.getH1Plus25();
        roih2Minus25 = matchObservingBet365.getH2Minus25() * matchObserving1x.getH2Minus25();
        roih2Plus25 = matchObservingBet365.getH2Plus25() * matchObserving1x.getH2Plus25();
        roiOv95 = matchObservingBet365.getOver95() * matchObserving1x.getOver95();
        roiUnd95 = matchObservingBet365.getUnder95() * matchObserving1x.getUnder95();
        roiChet = matchObservingBet365.getChet() * matchObserving1x.getChet();
        roiNechet = matchObservingBet365.getNechet() * matchObserving1x.getNechet();
        round();
        roys = new ArrayList<>();
        roys.add(roih1Minus25);
        roys.add(roih1Plus25);
        roys.add(roih2Minus25);
        roys.add(roih2Plus25);
        roys.add(roiOv95);
        roys.add(roiUnd95);
        roys.add(roiChet);
        roys.add(roiNechet);
    }
    private void round() {
        roih1Minus25 = Math.round(roih1Minus25 * 1000.0) / 1000.0;
        roih1Plus25 = Math.round(roih1Plus25 * 1000.0) / 1000.0;
        roih2Minus25 = Math.round(roih2Minus25 * 1000.0) / 1000.0;
        roih2Plus25 = Math.round(roih2Plus25 * 1000.0) / 1000.0;
        roiOv95 = Math.round(roiOv95 * 1000.0) / 1000.0;
        roiUnd95 = Math.round(roiUnd95 * 1000.0) / 1000.0;
        roiChet = Math.round(roiChet * 1000.0) / 1000.0;
        roiNechet = Math.round(roiNechet * 1000.0) / 1000.0;
    }
    public boolean haveGoodRoi(){
        for(Double roi : roys){
            if(roi > constRoi)
                return true;
        }
        return false;
    }
    public boolean isGoodRoi(double roi){
        return roi > constRoi;
    }

    public double getRoih1Minus25() {
        return roih1Minus25;
    }

    public double getRoih1Plus25() {
        return roih1Plus25;
    }

    public double getRoih2Minus25() {
        return roih2Minus25;
    }

    public double getRoih2Plus25() {
        return roih2Plus25;
    }

    public double getRoiOv95() {
        return roiOv95;
    }

    public double getRoiUnd95() {
        return roiUnd95;
    }

    public double getRoiChet() {
        return roiChet;
    }

    public double getRoiNechet() {
        return roiNechet;
    }
}

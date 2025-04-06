package ru.bets.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.bets.project.dexsport.ParserDexSport;
import ru.bets.project.fonbet.ParserFonbet;
import ru.bets.project.fonbet.SmallBet;
import ru.bets.project.secretbk.ParserSecretBK;

@Controller
public class SeleniumController {
    private final ParserFonbet parserFonbet;
    private final ParserSecretBK parserSecretBK;
    private final ParserDexSport parserDexSport;
    private final SmallBet smallBet;
    @Autowired
    public SeleniumController(ParserFonbet parserFonbet, ParserSecretBK parserSecretBK, ParserDexSport parserDexSport, SmallBet smallBet) {
        this.parserFonbet = parserFonbet;
        this.parserSecretBK = parserSecretBK;
        this.parserDexSport = parserDexSport;
        this.smallBet = smallBet;
    }

    @GetMapping("/parsingFon")
    public String parsingFon(){
        parserFonbet.start();
        return "redirect:/index";
    }
    @GetMapping("/parsingSecretBK")
    public String parsingSecretBK(){
        parserSecretBK.start();
        return "redirect:/index";
    }
    @GetMapping("/parsingDexSport")
    public String parsingDexSport(){
        parserDexSport.start();
        return "redirect:/index";
    }
    @GetMapping("/findSmallBets")
    public String findSmallBets(){
        smallBet.execute();
        return "redirect:/index";
    }
}

package ru.bets.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import ru.bets.project.spoyer.ApiSpoyer;
import ru.bets.project.spoyer.dataupdaters.UpdaterTennisPlayer;

@Controller
public class SpoyerController {
    private final ApiSpoyer apiSpoyer;
    private final UpdaterTennisPlayer updaterTennisPlayer;

    @Autowired
    public SpoyerController(ApiSpoyer apiSpoyer, UpdaterTennisPlayer updaterTennisPlayer) {

        this.apiSpoyer = apiSpoyer;
        this.updaterTennisPlayer = updaterTennisPlayer;
    }

    @GetMapping("/spoyer")
    public String findBet() {
        apiSpoyer.start();
        return "redirect:/index";
    }
    @GetMapping("/update")
    public String update() {
        updaterTennisPlayer.start();
        return "redirect:/index";
    }
}

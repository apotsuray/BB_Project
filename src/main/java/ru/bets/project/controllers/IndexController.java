package ru.bets.project.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.bets.project.utils.LuckyDays;

@Controller
public class IndexController {

    @GetMapping("/index")
    public String hello(){
        return "/index";
    }
    @GetMapping("/calculateFreebet")
    public String calculateFreebet(@ModelAttribute("luckyDays") LuckyDays luckyDays){
        return "/calculateFreebet";
    }

}

package ru.bets.project.services.spoyerservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bets.project.models.spoyermodels.BetsTennis;
import ru.bets.project.repositories.spoyerrepositories.BetsTennisRepository;

@Service
@Transactional(readOnly = true)
public class BetsTennisService {
    private final BetsTennisRepository betsTennisRepository;
    @Autowired
    public BetsTennisService(BetsTennisRepository betsTennisRepository) {
        this.betsTennisRepository = betsTennisRepository;
    }
    @Transactional
    public void save(BetsTennis betsTennis)
    {
        betsTennisRepository.save(betsTennis);
    }
}

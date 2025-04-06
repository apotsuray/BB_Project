package ru.bets.project.services.spoyerservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bets.project.models.spoyermodels.Leagues;
import ru.bets.project.repositories.spoyerrepositories.LeaguesRepository;

@Service
@Transactional(readOnly = true)
public class LeaguesService {
    private final LeaguesRepository leaguesRepository;
    @Autowired
    public LeaguesService(LeaguesRepository leaguesRepository) {
        this.leaguesRepository = leaguesRepository;
    }
    public String getNameOnSpoyerByNameOnBet365(String nameOnBet365){
        return leaguesRepository.findNameOnSpoyerByNameOnBet365(nameOnBet365);
    }
    public Leagues getByNameOnSpoyer(String nameOnSpoyer){
        return leaguesRepository.findByNameOnSpoyer(nameOnSpoyer);
    }
    @Transactional
    public void save(Leagues leagues)
    {
        leaguesRepository.save(leagues);
    }
    @Transactional
    public void updateNameOnBet365(Leagues updatedLeague, String  nameOnBet365)
    {
        updatedLeague.setNameOnBet365(nameOnBet365);
        leaguesRepository.save(updatedLeague);
    }
}

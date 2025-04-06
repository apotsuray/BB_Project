package ru.bets.project.services.spoyerservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bets.project.models.spoyermodels.TennisPlayer;
import ru.bets.project.repositories.spoyerrepositories.TennisPlayerRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class TennisPlayerService {
    private final TennisPlayerRepository tennisPlayerRepository;

    @Autowired
    public TennisPlayerService(TennisPlayerRepository tennisPlayerRepository) {
        this.tennisPlayerRepository = tennisPlayerRepository;
    }

    public List<String> getListNames365() {
        return tennisPlayerRepository.findALlNameOnBet365();
    }

    public List<String> getListNames1xbet() {
        return tennisPlayerRepository.findALlNameOn1xbet();
    }
    public TennisPlayer getByIdOnSpoyer(int idOnSpoyer){
        Optional<TennisPlayer> tennisPlayer = tennisPlayerRepository.findByIdOnSpoyer(idOnSpoyer);
        if (tennisPlayer.isPresent()) {
            return tennisPlayer.get();
        }else {
            return null;
        }
    }
    public TennisPlayer getByNameOnBet365(String name365)
    {
        Optional<TennisPlayer> tennisPlayer = tennisPlayerRepository.findByNameOnBet365(name365);
        if (tennisPlayer.isPresent()) {
            return tennisPlayer.get();
        }else {
            return null;
        }
    }
    public List<TennisPlayer> findALl()
    {
        return tennisPlayerRepository.findAll();
    }
    @Transactional
    public void updateLastUpdate(TennisPlayer updatedTennisPlayer, LocalDate newLastUpdate)
    {
        updatedTennisPlayer.setLastUpdate(newLastUpdate);
        tennisPlayerRepository.save(updatedTennisPlayer);
    }
    @Transactional
    public void updateNameOn1xBet(TennisPlayer updatedTennisPlayer, String nameOn1xBet)
    {
        updatedTennisPlayer.setNameOn1xbet(nameOn1xBet);
        tennisPlayerRepository.save(updatedTennisPlayer);
    }
    @Transactional
    public void updateIdOnSpoyer(TennisPlayer updatedTennisPlayer, String nameOnSpoyer,int idOnSpoyer)
    {
        updatedTennisPlayer.setNameOnSpoyer(nameOnSpoyer);
        updatedTennisPlayer.setIdOnSpoyer(idOnSpoyer);
        tennisPlayerRepository.save(updatedTennisPlayer);
    }
    @Transactional
    public void save(TennisPlayer tennisPlayer)
    {
        tennisPlayerRepository.save(tennisPlayer);
    }
}

package ru.bets.project.repositories.spoyerrepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.bets.project.models.spoyermodels.TennisPlayer;

import java.util.List;
import java.util.Optional;

@Repository
public interface TennisPlayerRepository extends JpaRepository<TennisPlayer,Integer> {

    @Query(value = "select neme_on_bet365 from tennisplayer",nativeQuery = true)
    List<String> findALlNameOnBet365();
    @Query(value = "select neme_on_1xbet from tennisplayer",nativeQuery = true)
    List<String> findALlNameOn1xbet();
    Optional<TennisPlayer> findByIdOnSpoyer(int id);
    Optional<TennisPlayer> findByNameOnBet365(String nameOnBet365);
}

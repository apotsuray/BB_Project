package ru.bets.project.repositories.spoyerrepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.bets.project.models.spoyermodels.Leagues;
@Repository
public interface LeaguesRepository extends JpaRepository<Leagues,Integer> {
    @Query("SELECT l.nameOnSpoyer FROM Leagues l WHERE l.nameOnBet365 = :name365")
    String findNameOnSpoyerByNameOnBet365(@Param("name365") String name365);
    Leagues findByNameOnSpoyer(String nameOnSpoyer);
}

package ru.bets.project.repositories.spoyerrepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bets.project.models.spoyermodels.WhoServeFirst;
@Repository
public interface WhoServeFirstRepository extends JpaRepository<WhoServeFirst,Integer> {
}

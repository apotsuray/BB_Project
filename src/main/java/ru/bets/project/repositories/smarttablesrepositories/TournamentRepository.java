package ru.bets.project.repositories.smarttablesrepositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bets.project.models.smarttablesmodels.Tournament;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament,Integer> {
}

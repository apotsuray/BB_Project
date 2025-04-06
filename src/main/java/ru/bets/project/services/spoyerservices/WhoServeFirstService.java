package ru.bets.project.services.spoyerservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bets.project.models.spoyermodels.WhoServeFirst;
import ru.bets.project.repositories.spoyerrepositories.WhoServeFirstRepository;

@Service
@Transactional(readOnly = true)
public class WhoServeFirstService {
    private final WhoServeFirstRepository whoServeFirstRepository;
    @Autowired
    public WhoServeFirstService(WhoServeFirstRepository whoServeFirstRepository) {
        this.whoServeFirstRepository = whoServeFirstRepository;
    }
    @Transactional
    public void save(WhoServeFirst whoServeFirst)
    {
        whoServeFirstRepository.save(whoServeFirst);
    }
}

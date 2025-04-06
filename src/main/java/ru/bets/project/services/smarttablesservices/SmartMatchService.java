package ru.bets.project.services.smarttablesservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bets.project.repositories.smarttablesrepositories.SmartMatchRepository;

@Service
@Transactional(readOnly = true)
public class SmartMatchService {
    private final SmartMatchRepository smartMatchRepository;
    @Autowired
    public SmartMatchService(SmartMatchRepository smartMatchRepository) {
        this.smartMatchRepository = smartMatchRepository;
    }
}

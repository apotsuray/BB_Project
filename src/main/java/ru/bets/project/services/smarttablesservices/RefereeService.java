package ru.bets.project.services.smarttablesservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.bets.project.repositories.smarttablesrepositories.RefereeRepository;

@Service
@Transactional(readOnly = true)
public class RefereeService {
    private final RefereeRepository refereeRepository;

    @Autowired
    public RefereeService(RefereeRepository refereeRepository) {
        this.refereeRepository = refereeRepository;
    }
}

package ru.bets.project.services.spoyerservices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.bets.project.models.spoyermodels.Person;
import ru.bets.project.repositories.spoyerrepositories.PeopleRepository;
import ru.bets.project.security.PersonDetails;

import java.util.Optional;

@Service
public class PersonService implements UserDetailsService {
    private final PeopleRepository peopleRepository;

    @Autowired
    public PersonService(PeopleRepository peopleRepository) {
        this.peopleRepository = peopleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Person> user = peopleRepository.findByUsername(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username + " not found");
        }
        return new PersonDetails(user.get());
    }
}

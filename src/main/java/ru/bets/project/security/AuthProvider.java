package ru.bets.project.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import ru.bets.project.services.spoyerservices.PersonService;

import java.util.Collections;

@Component
public class AuthProvider implements AuthenticationProvider {
    private final PersonService personService;
    @Autowired
    public AuthProvider(PersonService personService) {
        this.personService = personService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        UserDetails personDetails =  personService.loadUserByUsername(username);
        String passwordForm = authentication.getCredentials().toString();
        String passwordBD = personDetails.getPassword().trim();
        if(!passwordForm.equals(passwordBD))
            throw new BadCredentialsException("Bad credentials");
    return new UsernamePasswordAuthenticationToken(personDetails, passwordForm, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return true;
    }
}

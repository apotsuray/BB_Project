package ru.bets.project.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.stereotype.Component;
import ru.bets.project.security.AuthProvider;

@EnableWebSecurity
public class SecurityConfig implements WebSecurityConfigurer {



    @Override
    public void init(SecurityBuilder builder) throws Exception {

    }

    @Override
    public void configure(SecurityBuilder builder) throws Exception {

    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers("/login").permitAll()       // Разрешаем доступ к странице входа всем
                        .anyRequest().authenticated())              // Все остальные запросы требуют аутентификации
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/", true));      // Адрес после выхода

        return http.build();
    }
}


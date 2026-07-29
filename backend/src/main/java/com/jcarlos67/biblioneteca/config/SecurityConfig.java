package com.jcarlos67.biblioneteca.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
            // Desabilita o CSRF (necessário para APIs RESTful que usam o Bruno/Postman)
            .csrf(csrf -> csrf.disable())
            // Define as regras de autorização de rotas
            .authorizeHttpRequests(auth -> auth
                    .anyRequest().permitAll()
            )
            // Desabilita o redirecionamento automático para a tela de login web do Spring
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable());

    return http.build();
  }
}
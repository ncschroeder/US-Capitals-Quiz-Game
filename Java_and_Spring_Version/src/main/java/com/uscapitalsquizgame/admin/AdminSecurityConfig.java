package com.uscapitalsquizgame.admin;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class AdminSecurityConfig {
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
        .authorizeHttpRequests(requests ->
            requests
            .requestMatchers("/admin.html", "/admin/**").hasRole("ADMIN")
            .anyRequest().permitAll()
        )
        // If i have the login page be "/login.html", the whitelabel error page gets shown after trying to log in.
        .formLogin(form -> form.loginPage("/login").permitAll())
        .logout(logout -> logout.permitAll())
        .csrf(csrf -> csrf.disable());
        
        return http.build();
    }
    
    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user =
            User.withDefaultPasswordEncoder()
            .username("admin")
            .password("password")
            .roles("ADMIN")
            .build();
        
        return new InMemoryUserDetailsManager(user);
    }
}

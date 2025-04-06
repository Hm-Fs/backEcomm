package com.BackEcom.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.BackEcom.filter.JwtAuthenticationFilter;
import com.BackEcom.service.UserDetailsServiceImpl;
import com.BackEcom.util.JwtUtil;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

	private final UserDetailsServiceImpl userDetailsServiceImpl;
	private final JwtUtil jwtUtil;

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
       return http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                  //  .requestMatchers("/admin/**").hasAuthority("ADMIN")
                  //  .requestMatchers("/users/**").hasAnyAuthority("ROLE_ADMIN", "ROLE_USER")
                .requestMatchers("/api/auth/**").permitAll() // Autoriser l'accès aux endpoints publics
                //.requestMatchers("/users/**").denyAll() 
               .requestMatchers("/**").permitAll()
                .anyRequest().authenticated()) // Toutes les autres requêtes nécessitent une authentification
                .addFilterBefore(new JwtAuthenticationFilter(jwtUtil, userDetailsServiceImpl), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

	@Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
    	AuthenticationManagerBuilder authenticationManagerBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
    	authenticationManagerBuilder.userDetailsService(userDetailsServiceImpl).passwordEncoder(passwordEncoder);
    	return authenticationManagerBuilder.build();
    }

}

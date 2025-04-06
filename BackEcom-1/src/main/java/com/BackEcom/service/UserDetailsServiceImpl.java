package com.BackEcom.service;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.BackEcom.model.User;
import com.BackEcom.repository.UserRepo;
import com.BackEcom.util.Role;

import lombok.RequiredArgsConstructor;
import java.util.Collections;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService{

	private final UserRepo userRepository;

   // public UserDetailsServiceImpl(UserRepo userRepository) {
     //   this.userRepository = userRepository;
    //}

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Charger l'utilisateur à partir de la base de données
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé : " + username));
        
        System.out.println("Utilisateur trouvé : " + user.getUsername());
        System.out.println("Rôle chargé : " + user.getRole());

        // Retourner un objet UserDetails (ici, l'utilisateur lui-même)
        // Assurer que le rôle a bien le préfixe "ROLE_"
     //   String role = user.getRole().startsWith("ROLE_") ? user.getRole() : "ROLE_" + user.getRole();

      //  System.out.println("Rôle corrigé pour Spring Security : " + role);
        
        String role = user.getRole().name();
        if (!role.startsWith("ROLE_")) {
            role = "ROLE_" + role;
        }
        System.out.println("Rôle after format : " + role);


        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority(role))
        );    
      //  return user;
    }
}

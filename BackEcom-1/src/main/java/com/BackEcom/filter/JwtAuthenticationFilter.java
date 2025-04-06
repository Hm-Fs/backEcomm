package com.BackEcom.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.BackEcom.service.UserDetailsServiceImpl;
import com.BackEcom.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsService;

//    public JwtAuthenticationFilter(JwtUtil jwtUtil, UserDetailsServiceImpl userDetailsService) {
  //      this.jwtUtil = jwtUtil;
    //    this.userDetailsService = userDetailsService;
    //}
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
    	//recuperer Header
        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;
//verifier si le header n'est pas null et commence par Bearer
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
        	//recuperer token
            jwt = authorizationHeader.substring(7);
          //recuperer username
            username = jwtUtil.extractUsername(jwt);
        }
//controle si user n est pas null et n est pas encore authentifie
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            if (jwtUtil.validateToken(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                		userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                System.out.println("Avant authentification : " + SecurityContextHolder.getContext().getAuthentication());
                System.out.println("Utilisateur authentifié : " + username);
                System.out.println("Rôles attribués : " + userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                System.out.println("Après authentification : " + SecurityContextHolder.getContext().getAuthentication());
                System.out.println("Utilisateur authentifié : " + username);
                System.out.println("Rôles : " + userDetails.getAuthorities());
            }
        }

        filterChain.doFilter(request, response);
    }
}

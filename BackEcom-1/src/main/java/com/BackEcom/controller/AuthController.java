package com.BackEcom.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.BackEcom.model.User;
import com.BackEcom.repository.UserRepo;
import com.BackEcom.util.JwtUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {


    private final AuthenticationManager authenticationManager;

    private final  JwtUtil jwtUtil;
    
    private final  PasswordEncoder passwordEncoder;

    private final UserRepo userRepository;


    @PostMapping("/register")

    public ResponseEntity<?> register(@RequestBody User user){
    	//if(userRepository.findByUsername(user.getUsername())!= null)
    	//if(userRepository.findByUsername(user.getUsername()) !=null){
    	//return 	ResponseEntity.badRequest().body("username is already in use");
    	//}

    	user.setPassword(passwordEncoder.encode(user.getPassword()));
    	return ResponseEntity.ok(userRepository.save(user));

    }


    @PostMapping("/login")
   // public String login(@RequestBody AuthRequest authRequest) {
    	 public ResponseEntity<?> login(@RequestBody User user) {
    	try {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

    				//if (authentication.isAuthenticated()) {
    					Map<String, Object> authData = new HashMap<>();
    					authData.put("token", jwtUtil.generateToken(user.getUsername()));
    					authData.put("type","Bearer");
    					return ResponseEntity.ok(authData);
    				//}
    		//return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid username or password1");


		} catch (AuthenticationException e) {
			log.error(e.getMessage());
			return  ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("invalid username or password2");
		}

    }

}

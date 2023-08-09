package com.aptech.sem4eprojectbe.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.aptech.sem4eprojectbe.entity.AuthenticationRequest;
import com.aptech.sem4eprojectbe.entity.AuthenticationResponse;
import com.aptech.sem4eprojectbe.entity.UserEntity;
import com.aptech.sem4eprojectbe.repository.UserRepository;
import com.aptech.sem4eprojectbe.service.JwtTokenService;
import com.aptech.sem4eprojectbe.service.JwtUserDetailsService;
import com.aptech.sem4eprojectbe.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class AuthenticationResource {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    @Autowired
    private JwtTokenService jwtTokenService;

    @PostMapping("/authenticate")
    public AuthenticationResponse authenticate(@RequestBody AuthenticationRequest authenticationRequest) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    authenticationRequest.getLogin(), authenticationRequest.getPassword()));
        } catch (final BadCredentialsException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(authenticationRequest.getLogin());
        final AuthenticationResponse authenticationResponse = new AuthenticationResponse();
        authenticationResponse.setToken(jwtTokenService.generateToken(userDetails));

        UserEntity foundUser = userRepository.findByEmail(authenticationRequest.getLogin()).get();
        authenticationResponse.setAddress(foundUser.getAddress());
        authenticationResponse.setCity(foundUser.getCity());
        authenticationResponse.setDeleted(foundUser.getDeleted());
        authenticationResponse.setDistrict(foundUser.getDistrict());
        authenticationResponse.setEmail(foundUser.getEmail());
        authenticationResponse.setFirstname(foundUser.getFirstname());
        authenticationResponse.setLastname(foundUser.getLastname());
        authenticationResponse.setId(foundUser.getId());
        authenticationResponse.setRole(foundUser.getRole());

        return authenticationResponse;
    }

}

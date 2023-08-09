package com.aptech.sem4eprojectbe.service;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.aptech.sem4eprojectbe.entity.JwtUserDetails;
import com.aptech.sem4eprojectbe.entity.UserEntity;
import com.aptech.sem4eprojectbe.repository.UserRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";

    public static final String ROLE_USER = "ROLE_" + USER;
    public static final String ROLE_ADMIN = "ROLE_" + ADMIN;

    @Autowired
    private UserRepository clientRepository;

    @Override
    public UserDetails loadUserByUsername(final String username) {
        final UserEntity client = clientRepository.findByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("Email " + username + " not found"));
        if (client.getRole().equals("user")) {
            return new JwtUserDetails(client.getId(), username, client.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(ROLE_USER)));
        } else {
            return new JwtUserDetails(client.getId(), username, client.getPassword(),
                    Collections.singletonList(new SimpleGrantedAuthority(ROLE_ADMIN)));
        }

    }

}

package com.aptech.sem4eprojectbe.entity;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class JwtUserDetails extends User {
    public final String id;

    public JwtUserDetails(final String id, final String username, final String hash,
            final Collection<? extends GrantedAuthority> authorities) {
        super(username, hash, authorities);
        this.id = id;
    }

}

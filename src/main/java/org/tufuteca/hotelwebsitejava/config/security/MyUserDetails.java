package org.tufuteca.hotelwebsitejava.config.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.tufuteca.hotelwebsitejava.model.User;
import org.tufuteca.hotelwebsitejava.model.Role;

import java.util.Collection;
import java.util.Collections;

public class MyUserDetails implements UserDetails {

    private Role role;

    private User user;
    private BCryptPasswordEncoder passwordEncoder;

    public MyUserDetails(User user, BCryptPasswordEncoder passwordEncoder) {
        this.user = user;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singletonList(new SimpleGrantedAuthority(user.getRole().getTitle()));
    }


    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
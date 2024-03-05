package org.tufuteca.hotelwebsitejava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.tufuteca.hotelwebsitejava.config.security.MyUserDetails;
import org.tufuteca.hotelwebsitejava.model.User;
import org.tufuteca.hotelwebsitejava.repository.UserRepository;

import java.util.Optional;


@Service
public class MyUserDetailsService implements UserDetailsService {

    private UserRepository repository;

    @Autowired
    public MyUserDetailsService(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = repository.findByUsername(username);
        return user.map(u -> new MyUserDetails(u, passwordEncoder())).orElseThrow(() -> new UsernameNotFoundException(username + " не найден"));
    }

    private BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

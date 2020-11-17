package com.thread3r.thread3rbackend.security;

import com.thread3r.thread3rbackend.model.Thread3rUser;
import com.thread3r.thread3rbackend.repository.Thread3rUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final Thread3rUserRepository repository;

    @Autowired
    public UserDetailsServiceImpl(Thread3rUserRepository repository) {
        this.repository = repository;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Thread3rUser user = repository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username)
        );
        return UserDetailsImpl.build(user);
    }

}

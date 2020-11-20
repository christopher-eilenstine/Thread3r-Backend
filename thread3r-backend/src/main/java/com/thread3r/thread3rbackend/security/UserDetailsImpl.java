package com.thread3r.thread3rbackend.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.thread3r.thread3rbackend.model.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;

@AllArgsConstructor
@Getter
@Setter
public class UserDetailsImpl implements UserDetails {

    private static final long serialVersionUID = 1L;

    private Long id;
    private String email;
    private String username;
    @JsonIgnore
    private String password;

    public static UserDetailsImpl build(UserEntity user) {
        return new UserDetailsImpl(
                user.getId(),
                user.getEmail(),
                user.getUsername(),
                user.getPassword()
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new HashSet<>();
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

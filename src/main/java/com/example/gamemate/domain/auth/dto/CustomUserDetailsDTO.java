package com.example.gamemate.domain.auth.dto;

import com.example.gamemate.domain.user.entity.User;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetailsDTO implements UserDetails {

    private final User user;

    public CustomUserDetailsDTO(User user) {

        this.user = user;

    }

    public User getUser() {
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        Collection<GrantedAuthority> collection = new ArrayList<>();

        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {

                return user.getRole().toString();

            }
        });

        return collection;
    }

    @Override
    public String getPassword() {

        return user.getPassword();

    }

    @Override
    public String getUsername() {

        return user.getUsername();

    }

    public Long getId() {

        return user.getId();

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
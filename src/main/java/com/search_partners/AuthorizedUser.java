package com.search_partners;

import com.search_partners.model.Provider;
import com.search_partners.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Objects;

public class AuthorizedUser implements UserDetails {

    private final User user;

    public AuthorizedUser(User user) {
        this.user = user;
    }

    public long getId() {
        return user.getId();
    }

    public String getName() {
        return user.getName();
    }

    public String getInitial() {
        return user.getInitial();
    }

    public String getEmail() {
        return user.getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        if (Provider.LOCAL.getName().equals(user.getProvider()))
            return user.getEmail();
        return user.getUserId();
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
        return user.isEnabled();
    }
}

package com.nobel.rock_paper_scissors.security.jwt;

import com.nobel.rock_paper_scissors.model.PlayerRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JwtUser implements UserDetails {

    @Getter
    private final Long id;
    private final String name;
    private final String password;
    private Collection<? extends GrantedAuthority> authorities;

    public JwtUser(Long id, String name, String password, String... roles) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.setAuthorities(roles);
    }

    public JwtUser(Long id, String name, String password, Set<PlayerRole> roles) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.setAuthorities(roles);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    private void setAuthorities(String... roles) {
        Set<GrantedAuthority> roleSet = new HashSet<>();
        for (String role : roles) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(role);
            roleSet.add(simpleGrantedAuthority);
        }
        this.authorities = roleSet;
    }

    private void setAuthorities(Set<PlayerRole> roles) {
        this.authorities = Stream.of(roles).map(role -> new SimpleGrantedAuthority(role.toString()))
                .collect(Collectors.toSet());
    }


    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
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

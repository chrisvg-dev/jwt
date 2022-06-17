package com.spring.crud.jwt_security.entity;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserSecurity implements UserDetails {
    private String nombre;
    private String lastName;
    private String email;
    private String pwd;
    private Collection<? extends GrantedAuthority> authorities;

    public UserSecurity(String nombre, String lastName, String email, String pwd, Collection<? extends GrantedAuthority> authorities) {
        this.nombre = nombre;
        this.lastName = lastName;
        this.email = email;
        this.pwd = pwd;
        this.authorities = authorities;
    }

    public static UserSecurity build(User usuario) {
        List<GrantedAuthority> authorities = usuario.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol.getRolName().name())).collect(Collectors.toList());
        return new UserSecurity(usuario.getNombre(), usuario.getLastName(), usuario.getEmail(), usuario.getPwd(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.pwd;
    }

    @Override
    public String getUsername() {
        return this.email;
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

    public String getNombre() {
        return nombre;
    }

    public String getEmail() {
        return email;
    }
}

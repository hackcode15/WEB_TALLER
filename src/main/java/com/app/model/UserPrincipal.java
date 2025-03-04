package com.app.model;


import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrincipal implements UserDetails {

    private User user;

    public UserPrincipal(User user){
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
 
        Set<GrantedAuthority> authorities = user.getRoles().stream()
            .map(role -> new SimpleGrantedAuthority("ROLE_" + role.getRoleEnum().name()))
            .collect(Collectors.toSet());

        // permisos asociados a los roles
        // De momento no lo necesito
        /* user.getRoles().stream()
            .flatMap(role -> role.getPermissions().stream())
            .map(permission -> new SimpleGrantedAuthority(permission.getPermissionEnum().name()))
            .forEach(authorities::add); */

        return authorities;

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

    public Integer getId(){
        return user.getId();
    }

    public String getFirstName() {
        return user.getFirstName();
    }

    public String getLastName() {
        return user.getLastName();
    }

    public String getFullName() {
        return user.getFirstName() + " " + user.getLastName();
    }

    public String getTypeUser() {
        if(user instanceof Customer){
            return "CUSTOMER";
        } else if(user instanceof Mechanic) {
            return "MECHANIC";
        }
        return "OTRO";
    }

    public boolean isCustomer(){
        return user instanceof Customer;
    }

    public boolean isMechanic(){
        return user instanceof Mechanic;
    }

    public Customer getCustomer() {
        if (isCustomer()) {
            return (Customer) user;
        }
        return null;
    }

    public Mechanic getMechanic() {
        if (isMechanic()) {
            return (Mechanic) user;
        }
        return null;
    }
    
}

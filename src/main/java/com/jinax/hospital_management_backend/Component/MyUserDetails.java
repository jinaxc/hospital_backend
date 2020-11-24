package com.jinax.hospital_management_backend.Component;

import com.jinax.hospital_management_backend.Entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * @author : chara
 */
public class MyUserDetails implements UserDetails {
    private User user;
    private String role;

    public MyUserDetails(User user, String role) {
        this.user = user;
        this.role = role;
    }

    public String getRealUsername(){
        return user.getName();
    }

    public long getDistrictId(){
        return user.getDistrictId();
    }

    public long getId(){
        return user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    /**
     *
     * @return this in fact returns identification
     */
    @Override
    public String getUsername() {
        return user.getIdentification();
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

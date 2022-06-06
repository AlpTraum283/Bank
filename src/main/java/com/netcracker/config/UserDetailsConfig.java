package com.netcracker.config;

import com.netcracker.model.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
public class UserDetailsConfig implements UserDetails {

    private String password;
    private String userName;
    private String userId;
    private Collection<? extends GrantedAuthority> grantedAuthorities;

    public static UserDetailsConfig convertUserEntityToUserDetailsConfig(UserEntity userEntity) {
        UserDetailsConfig userDetailsConfig = new UserDetailsConfig();
        userDetailsConfig.password = userEntity.getPassword();
        userDetailsConfig.userName = userEntity.getName();
        userDetailsConfig.userId = String.valueOf(userEntity.getObjId());
        userDetailsConfig.grantedAuthorities = Collections.singletonList(
                new SimpleGrantedAuthority("USER")
        );
        return userDetailsConfig;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return grantedAuthorities;
    }

    public String getUserId() {
        return userId;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return userName;
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

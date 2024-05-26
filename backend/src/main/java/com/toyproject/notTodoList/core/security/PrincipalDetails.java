package com.toyproject.notTodoList.core.security;

import com.toyproject.notTodoList.domain.auth.application.dto.res.LoginResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.Collection;
import java.util.Map;

@AllArgsConstructor
public class PrincipalDetails implements OAuth2User, UserDetails {

    private LoginResponse loginResponse;
    private Map<String, Object> attributes;
    private String attributeKey;


    @Override
    public String getPassword() {
        return null;
    }

    public LoginResponse getLoginResponse() {return this.loginResponse;}

    public Long getMemberId() {return loginResponse.getId();}

    public Long getAuthId() {return loginResponse.getAuths().get(0).getId();}

    @Override
    public String getUsername() {
        return loginResponse.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return loginResponse.getAuthorities();
    }

    @Override
    public String getName() {
        return attributes.get(attributeKey).toString();
    }
}

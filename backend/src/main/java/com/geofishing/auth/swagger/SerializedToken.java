package com.geofishing.auth.swagger;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class SerializedToken {
    String access_token;
    String token_type;
    String refresh_token;
    String expires_in;
    String scope;
    List<SimpleGrantedAuthority> authorities;

    public String getAccess_token() {
        return access_token;
    }

    public String getToken_type() {return token_type;}

    public String getRefresh_token() {return refresh_token;}

    public String getExpires_in() {return expires_in;}

    public String getScope() {return scope;}

    public List<SimpleGrantedAuthority> getAuthorities() {return authorities;}
}
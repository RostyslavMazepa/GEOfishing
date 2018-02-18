package com.geofishing.auth.json;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

public class SerializedToken {
    private String access_token;
    private String token_type;
    private String refresh_token;
    private String expires_in;
    private String scope;
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
package com.geofishing.dto;

import io.swagger.annotations.ApiModelProperty;

public class SocAuthDTO {
    private String socialNetwork;
    private String accessToken;
    private Integer tokenExpiresIn;
    private String refreshToken;
    private String idToken;

    public SocAuthDTO() {
    }

    public SocAuthDTO(String socialNetwork, String accessToken, String idToken, Integer tokenExpiresIn, String refreshToken) {
        this.socialNetwork = socialNetwork;
        this.accessToken = accessToken;
        this.idToken = idToken;
        this.tokenExpiresIn = tokenExpiresIn;
        this.refreshToken = refreshToken;
    }

    // @formatter:off
    public String getSocialNetwork() {
        return socialNetwork;
    }
    @ApiModelProperty(value="social network id", allowableValues="facebook,google", required=true)
    public void setSocialNetwork(String socialNetwork) {
        this.socialNetwork = socialNetwork;
    }
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public Integer getTokenExpiresIn() {
        return tokenExpiresIn;
    }
    public void setTokenExpiresIn(Integer tokenExpiresIn) {
        this.tokenExpiresIn = tokenExpiresIn;
    }
    public String getRefreshToken() {return refreshToken;}
    public void setRefreshToken(String refreshToken) {this.refreshToken = refreshToken;}
    public String getIdToken() {return idToken;}
    public void setIdToken(String idToken) {this.idToken = idToken;}
// @formatter:on

}

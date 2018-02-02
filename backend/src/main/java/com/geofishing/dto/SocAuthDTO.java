package com.geofishing.dto;

import com.geofishing.model.ISocialAccount;

public class SocAuthDTO implements ISocialAccount {
    private Long userId;
    private String socialNetwork;
    private String username;
    private String email;
    private String accessToken;
    private String userPictureURL;
    private Integer tokenExpiresIn;
    private String signedRequest;

    public SocAuthDTO() {
    }

    public SocAuthDTO(String userId, String socialNetwork, String username, String userEmail, String accessToken, String userImageURL, String tokenExpiresIn, String signedRequest) {
        this.socialNetwork = socialNetwork;
        this.username = username;
        this.email = userEmail;
        this.accessToken = accessToken;
        this.userId = Long.getLong(userId);
        this.userPictureURL = userImageURL;
        this.tokenExpiresIn = Integer.getInteger(tokenExpiresIn);
        this.signedRequest = signedRequest;
    }

    public String getSocialNetwork() {
        return socialNetwork;
    }

    public void setSocialNetwork(String socialNetwork) {
        this.socialNetwork = socialNetwork;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserPictureURL() {
        return userPictureURL;
    }

    public void setUserPictureURL(String userPictureURL) {
        this.userPictureURL = userPictureURL;
    }

    public Integer getTokenExpiresIn() {
        return tokenExpiresIn;
    }

    public void setTokenExpiresIn(Integer tokenExpiresIn) {
        this.tokenExpiresIn = tokenExpiresIn;
    }

    public String getSignedRequest() {
        return signedRequest;
    }

    public void setSignedRequest(String signedRequest) {
        this.signedRequest = signedRequest;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}

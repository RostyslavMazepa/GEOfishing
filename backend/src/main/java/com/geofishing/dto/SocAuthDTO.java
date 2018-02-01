package com.geofishing.dto;

public class SocAuthDTO {

    private String socialNetwork;
    private String username;
    private String userEmail;
    private String userToken;
    private String userId;
    private String userImageURL;
    private String expiresIn;
    private String signedRequest;

    public SocAuthDTO() {
    }

    public SocAuthDTO(String socialNetwork, String username,String userEmail, String userToken, String userId, String userImageURL, String expiresIn, String signedRequest) {
        this.socialNetwork = socialNetwork;
        this.username = username;
        this.userEmail = userEmail;
        this.userToken = userToken;
        this.userId = userId;
        this.userImageURL = userImageURL;
        this.expiresIn = expiresIn;
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

    public String getUserToken() {
        return userToken;
    }

    public void setUserToken(String userToken) {
        this.userToken = userToken;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserImageURL() {
        return userImageURL;
    }

    public void setUserImageURL(String userImageURL) {
        this.userImageURL = userImageURL;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getSignedRequest() {
        return signedRequest;
    }

    public void setSignedRequest(String signedRequest) {
        this.signedRequest = signedRequest;
    }

    public String getUserEmail() {return userEmail;}

    public void setUserEmail(String userEmail) {this.userEmail = userEmail;}
}

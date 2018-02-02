package com.geofishing.model;

import javax.persistence.*;

@Entity
@Table(name = "soc_network_account")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "soc_network")
public class SocialAccount implements ISocialAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private String userPictureURL;
    private String accessToken;
    private String email;
    private Integer tokenExpiresIn;


    SocialAccount() {
    }

    SocialAccount(ISocialAccount acc) {
        this.userId = acc.getUserId();
        this.email = acc.getEmail();
        this.userPictureURL = acc.getUserPictureURL();
        this.accessToken = acc.getAccessToken();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
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

    @Override
    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Integer getTokenExpiresIn() {
        return tokenExpiresIn;
    }

    public void setTokenExpiresIn(Integer tokenExpiresIn) {
        this.tokenExpiresIn = tokenExpiresIn;
    }

    @Override
    public String toString() {
        return "SocialNetworkAccount{" +
                "id=" + id +
                ", userId=" + userId +
                ", userPictureURL='" + userPictureURL + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", email='" + email + '\'' +
                ", tokenExpiresIn=" + tokenExpiresIn +
                '}';
    }
}

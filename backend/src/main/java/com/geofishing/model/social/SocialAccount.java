package com.geofishing.model.social;

import javax.persistence.*;

@Entity
@Table(name = "soc_network_account")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "soc_network")
public class SocialAccount implements ISocialAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String userId;
    String userPictureURL;
    String refreshToken;
    String email;


    SocialAccount() {
    }

    SocialAccount(ISocialAccount acc) {
        this.userId = acc.getUserId();
        this.email = acc.getEmail();
        this.userPictureURL = acc.getUserPictureURL();
        this.refreshToken = acc.getRefreshToken();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserPictureURL() {
        return userPictureURL;
    }

    public void setUserPictureURL(String userPictureURL) {
        this.userPictureURL = userPictureURL;
    }

    @Override
    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String accessToken) {
        this.refreshToken = accessToken;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Override
    public String toString() {
        return "SocialNetworkAccount{" +
                "id=" + id +
                ", userId=" + userId +
                ", userPictureURL='" + userPictureURL + '\'' +
                ", accessToken='" + refreshToken + '\'' +
                ", email='" + email + '\'' +
                '}';
    }
}

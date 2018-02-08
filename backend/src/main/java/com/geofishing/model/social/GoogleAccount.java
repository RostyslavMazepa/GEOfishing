package com.geofishing.model.social;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("google")
public class GoogleAccount extends SocialAccount {

    public GoogleAccount() {
    }

    public GoogleAccount(String email, String userId, String picURL, String refreshToken) {
        this.refreshToken = refreshToken;
        this.email = email;
        this.userId = userId;
        this.userPictureURL = picURL;

    }

    @Override
    public String toString() {
        return "GoogleAccount{}" + super.toString();
    }
}

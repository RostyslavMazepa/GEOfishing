package com.geofishing.model.social;

import com.geofishing.dto.FacebookUser;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("facebook")
public class FacebookAccount extends SocialAccount {


    public FacebookAccount() {
    }


    public FacebookAccount(FacebookUser dto, String refreshToken) {
        this.refreshToken = refreshToken;
        this.email = dto.getEmail();
        this.userId = dto.getId();
        this.userPictureURL = dto.getPictureURL();

    }


}

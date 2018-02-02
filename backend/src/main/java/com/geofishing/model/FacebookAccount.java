package com.geofishing.model;

import com.geofishing.dto.SocAuthDTO;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("facebook")
public class FacebookAccount extends SocialAccount {

    private String signedRequest;

    public FacebookAccount() {
    }

    public FacebookAccount(ISocialAccount acc) {
        super(acc);
    }

    public FacebookAccount(SocAuthDTO dto) {
        super(dto);
        this.signedRequest = dto.getSignedRequest();
    }

    public String getSignedRequest() {
        return signedRequest;
    }

    public void setSignedRequest(String signedRequest) {
        this.signedRequest = signedRequest;
    }

    @Override
    public String toString() {
        return "FacebookAccount{" + super.toString() +
                "signedRequest='" + signedRequest + '\'' +
                '}';
    }
}

package com.geofishing.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("google")
public class GoogleAccount extends SocialAccount {

    public GoogleAccount() {
    }

    @Override
    public String toString() {
        return "GoogleAccount{}" + super.toString();
    }
}

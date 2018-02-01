package com.geofishing.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "facebook_account")
public class FacebookAccount {
    @Id
    @Column(name = "id")
   private String facebookID;

    @Column
    private String imageURL;

    @Column(name = "token")
    private String userToken;

    @Column(name = "token_recieved")
    private LocalDate tokenRecieved;

    @Column(name = "token_expires_in")
    private Integer tokenExpiresIn;


    public FacebookAccount() {
    }

    public FacebookAccount(String facebookID, String imageURL, String userToken, Integer tokenExpiresIn) {
        this.facebookID = facebookID;
        this.imageURL = imageURL;
        this.userToken = userToken;
        this.tokenExpiresIn = tokenExpiresIn;
    }
}

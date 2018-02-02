package com.geofishing.model;

public interface ISocialAccount {
    Long getUserId();

    String getUserPictureURL();

    String getEmail();

    String getAccessToken();

    Integer getTokenExpiresIn();

}

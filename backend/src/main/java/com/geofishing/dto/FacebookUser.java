package com.geofishing.dto;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.restfb.types.ProfilePictureSource;
import com.restfb.types.User;

@JsonRootName(value = "FacebookUser")
@JsonInclude(value = JsonInclude.Include.NON_EMPTY, content = JsonInclude.Include.NON_NULL)
public class FacebookUser extends User {


    public FacebookUser() {
        super();
    }

    @JsonIgnore
    public ProfilePictureSource getPicture() {
        return super.getPicture();
    }

    @JsonProperty
    public String getPictureURL() {
        return super.getPicture() == null ? null : super.getPicture().getUrl();
    }

//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
//    public Date getBirthdayAsDate() {
//        return super.getBirthdayAsDate();
//    }

}

package com.geofishing.dto;

public class UserDTO {
    private String firstName;
    private String lastName;
    private String password;
    private String email;
    private String facebookId;
    private String facebookImageURL;
    private String facebookName;
    private String facebookUserName;

    public UserDTO() {
    }

    public UserDTO(SocAuthDTO dto) {
        this.email = dto.getUserId();
        this.facebookId = dto.getUserId();
        this.facebookImageURL = dto.getUserImageURL();
        this.facebookUserName = dto.getUsername();
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(String facebookId) {
        this.facebookId = facebookId;
    }

    public String getFacebookImageURL() {
        return facebookImageURL;
    }

    public void setFacebookImageURL(String facebookImageURL) {
        this.facebookImageURL = facebookImageURL;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFacebookName() {
        return facebookName;
    }

    public void setFacebookName(String facebookName) {
        this.facebookName = facebookName;
    }

    public String getFacebookUserName() {
        return facebookUserName;
    }

    public void setFacebookUserName(String facebookUserName) {
        this.facebookUserName = facebookUserName;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", facebookId='" + facebookId + '\'' +
                ", facebookImageURL='" + facebookImageURL + '\'' +
                ", facebookName='" + facebookName + '\'' +
                ", facebookUserName='" + facebookUserName + '\'' +
                '}';
    }
}

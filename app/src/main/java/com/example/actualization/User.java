package com.example.actualization;


public class User {
    public String realName;
    public String username;
    public String email;
    public String isBuisness;
    public String userID;

    public User(){

    }

    public User(String realName, String username, String email, String isBuisness){
        this.realName = realName;
        this.username = username;
        this.email = email;
        this.isBuisness = isBuisness;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserID() { return userID; }

    public void setUserID(String userID) { this.userID = userID; }

    public String getIsBuisness() { return isBuisness; }

    public void setIsBuisness(String isBuisness) { this.isBuisness = isBuisness; }

}

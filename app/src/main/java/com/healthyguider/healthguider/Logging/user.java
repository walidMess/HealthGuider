package com.healthyguider.healthguider.Logging;

public class user {

    private String username,Ismember,privileges,email,password;

    public user(String Ismember ,String username , String email , String privileges,String password) {

        this.username = username;
        this.Ismember = Ismember;
        this.privileges = privileges;
        this.email = email;
        this.password=password;
    }

 public user(String username , String email , String privileges,String password) {

        this.username = username;
        this.Ismember = Ismember;
        this.privileges = privileges;
        this.email = email;
        this.password=password;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIsmember() {
        return Ismember;
    }

    public void setIsmember(String password) {
        this.Ismember = password;
    }

    public String getPrivileges() {
        return privileges;
    }

    public void setPrivileges(String type) {
        this.privileges = type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

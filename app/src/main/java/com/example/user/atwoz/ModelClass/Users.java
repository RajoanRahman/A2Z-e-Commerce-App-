package com.example.user.atwoz.ModelClass;

public class Users {

    private String name,password,phoneNumber;


    public Users() {

    }

    public Users(String name, String password, String phoneNumber) {
        this.name = name;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}

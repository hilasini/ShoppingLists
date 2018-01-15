package com.example.shoppinglists;

import java.util.ArrayList;
import java.util.List;

public class User {
//STOPSHIP

    private String password;
    private String firstname;
    private String lastname;
    private String Email;
    private String city;
    private String street;
    private String photo;
    private String status;
    private List<ShopList> MyLists;

    public User(String password, String firstname, String lastname, String email, String city, String street, String photo, String status, List<ShopList> myLists) {
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        Email = email;
        this.city = city;
        this.street = street;
        this.photo = photo;
        this.status = status;
        MyLists = myLists;
    }

    public User()
    {
        this.password = "";
        this.firstname = "";
        this.lastname = "";
        Email = "";
        this.city = "";
        this.street = "";
        this.photo = "";
        this.status = "user";
        MyLists = new ArrayList<>();
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<ShopList> getMyLists() {
        return MyLists;
    }

    public void setMyLists(List<ShopList> myLists) {
        MyLists = myLists;
    }


}

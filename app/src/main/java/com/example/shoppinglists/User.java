package com.example.shoppinglists;

public class User {

    private String username;
    private String password;
    private String firstname;
    private String lastname;
    private String Email;
    private String city;
    private String street;


    public User() {
        this.username = "";
        this.password = "";
        this.firstname = "";
        this.lastname = "";
        Email = "";
        this.city = "";
        this.street = "";
    }


    public User(String username, String password, String firstname, String lastname, String email, String city, String street) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        Email = email;
        this.city = city;
        this.street = street;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
}

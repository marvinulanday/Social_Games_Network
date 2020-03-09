package com.stucom.socialgamesnetwork.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class User {

    private String username;
    private String password;
    private String email;
    private String name;
    private String surname;
    private String token;

    public User(String username) {
        this(username, "");
    }

    public User(String username, String password) {
        this(username, password, "", "", "", "");
    }

    public User(String username, String password, String email) {
        this(username, password, email, "", "", "");
    }

    public User(String username, String password, String email, String name, String surname, String token) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}

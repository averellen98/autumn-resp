package com.project.group.group_project;

public abstract class User {

    private String firstName;
    private String lastName;
    private UserRole role;
    private String password;
    private String username;
    private String id;

    public User(String id, String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public UserRole getRole() {
        return role;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getId() {
        return id;
    }
}
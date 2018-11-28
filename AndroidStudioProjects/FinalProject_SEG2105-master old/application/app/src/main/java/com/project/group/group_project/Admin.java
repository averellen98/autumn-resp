package com.project.group.group_project;

public class Admin extends User {

    public Admin(String id, String username, String password, UserRole role) {
        super(id, username, password, role);
    }

    public boolean canBeAdmin(String username, String password, UserRole role){
        if (username.equals("admin") && password.equals("admin") && role == UserRole.ADMIN){
            return true;
        } else {
            return false;
        }
    }
}

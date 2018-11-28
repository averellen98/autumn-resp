package com.project.group.group_project;

import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserDatabase {

    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final DatabaseReference databaseUsers = database.getReference("users");

    private static List<User> users = new ArrayList<User>();

    static {

        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                users.clear();

                for (DataSnapshot ds: dataSnapshot.getChildren()) {

                    String role = ds.child("role").getValue(String.class);
                    String id = ds.child("id").getValue(String.class);
                    String username = ds.child("username").getValue(String.class);
                    String password = ds.child("password").getValue(String.class);
                    String firstName = ds.child("firstName").getValue(String.class);
                    String lastName = ds.child("lastName").getValue(String.class);

                    User user = createUserFromFirebase(id, username, password, role, firstName, lastName);

                    users.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public boolean isUserAlreadyPresentInDatabase(String username) {

        for (User user: users) {

            if (user.getUsername().equals(username)) {
                return true;
            }
        }

        return false;
    }

    public boolean isUserInDatabase(String username, String password) {

        for (int i = 0; i < users.size(); i++) {

            User user = users.get(i);

            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return true;
            }
        }

        return false;
    }

    public User getUser(String username, String password) {

        for (User user: users) {

            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user;
            }
        }

        return null;
    }

    public User addUser(String username, String password, String role, String firstName, String lastName) {

        String id = databaseUsers.push().getKey();

        User user = createUser(id, username, password, role, firstName, lastName);

        databaseUsers.child(id).setValue(user);

        return user;
    }

    public List<User> getUsers() {
        return users;
    }

    private static User createUser(String id, String username, String password, String role, String firstName, String lastName) {

        UserRole userRole = UserRole.getRoleByName(role);

        User user = null;

        switch (userRole) {
            case ADMIN:
                user = new Admin(id, username, password, userRole);
                break;
            case HOME_OWNER:
                user = new HomeOwner(id, username, password, userRole);
                break;
            case SERVICE_PROVIDER:
                user = new ServiceProvider(id, username, password, userRole);
                break;
        }

        user.setFirstName(firstName);
        user.setLastName(lastName);

        return user;
    }

    private static User createUserFromFirebase(String id, String username, String password, String role, String firstName, String lastName) {

        UserRole userRole = UserRole.getRoleByNameForFirebase(role);

        User user = null;

        switch (userRole) {
            case ADMIN:
                user = new Admin(id, username, password, userRole);
                break;
            case HOME_OWNER:
                user = new HomeOwner(id, username, password, userRole);
                break;
            case SERVICE_PROVIDER:
                user = new ServiceProvider(id, username, password, userRole);
                break;
        }

        user.setFirstName(firstName);
        user.setLastName(lastName);

        return user;
    }
}
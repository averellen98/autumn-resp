package com.project.group.group_project;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UserTestDatabase {

    private static List<User> users = new ArrayList<User>();

    public UserTestDatabase() {
        addUser("admin", "admin", UserRole.ADMIN, "admin", "admin");
        addUser("sp", "sp", UserRole.SERVICE_PROVIDER, "sp", "sp");
        addUser("ho", "ho", UserRole.HOME_OWNER, "ho", "ho");
        addUser("servicep", "servicep", UserRole.SERVICE_PROVIDER, "service", "p");
        addUser("homeo", "homeo", UserRole.HOME_OWNER, "home", "o");
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

    public User addUser(String username, String password, UserRole role, String firstName, String lastName) {

        String id = "userTestID";

        User user = createUser(id, username, password, role, firstName, lastName);

        users.add(user);

        return user;
    }

    public User addServiceProvider(String username, String password, String firstName, String lastName, String street, String city, String province, String postalCode, int phoneNumber, String companyName, String generalDescription, boolean isLicensed) {

        String id = "spTestID";

        User user = createUser(id, username, password, UserRole.SERVICE_PROVIDER, firstName, lastName);

        users.add(user);

        return user;
    }

    public List<User> getUsers() {
        return users;
    }

    public User getUserById(String id) {

        for (User user: users) {

            if (user.getId().equals(id)) {
                return user;
            }
        }

        return null;
    }

    private static User createUser(String id, String username, String password, UserRole role, String firstName, String lastName) {

        User user = null;

        switch (role) {
            case ADMIN:
                user = new Admin(id, username, password, role);
                break;
            case HOME_OWNER:
                user = new HomeOwner(id, username, password, role);
                break;
            case SERVICE_PROVIDER:
                user = new ServiceProvider(id, username, password, role);
                break;
        }

        user.setFirstName(firstName);
        user.setLastName(lastName);

        return user;
    }


}

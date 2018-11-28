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

    private static final UserDatabase instance = new UserDatabase();

    private static List<User> users = new ArrayList<User>();

    static {

        databaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                users.clear();

                for (DataSnapshot ds: dataSnapshot.getChildren()) {

                    String roleString = ds.child("role").getValue(String.class);
                    String id = ds.child("id").getValue(String.class);
                    String username = ds.child("username").getValue(String.class);
                    String password = ds.child("password").getValue(String.class);
                    String firstName = ds.child("firstName").getValue(String.class);
                    String lastName = ds.child("lastName").getValue(String.class);

                    UserRole role = UserRole.getRoleByNameForFirebase(roleString);

                    User user = createUser(id, username, password, role, firstName, lastName);

                    if (role.equals(UserRole.SERVICE_PROVIDER)) {

                        String companyName = ds.child("companyName").getValue(String.class);
                        String generalDescription = ds.child("generalDescription").getValue(String.class);
                        int phoneNumber = ds.child("phoneNumber").getValue(Integer.class);
                        boolean isLicensed = ds.child("isLicensed").getValue(Boolean.class);

                        AddressDatabase addressDatabase = AddressDatabase.getInstance();
                        Address address = addressDatabase.getAddress(id);

                        AvailabilityDatabase availabilityDatabase = AvailabilityDatabase.getInstance();
                        List<Availability> availabilities = availabilityDatabase.getAvailabilitiesByServiceProvider(id);

                        ServiceDatabase serviceDatabase = ServiceDatabase.getInstance();
                        List<Service> services = serviceDatabase.getServiceForProvider(id);

                        ((ServiceProvider) user).setAddress(address);
                        ((ServiceProvider) user).setAvailabilities(availabilities);
                        ((ServiceProvider) user).setCompanyName(companyName);
                        ((ServiceProvider) user).setGeneralDescription(generalDescription);
                        ((ServiceProvider) user).setLicensed(isLicensed);
                        ((ServiceProvider) user).setPhoneNumber(phoneNumber);
                        ((ServiceProvider) user).setServices(services);
                    }

                    users.add(user);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private UserDatabase() {

    }

    public static UserDatabase getInstance() {
        return instance;
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

        String id = databaseUsers.push().getKey();

        User user = createUser(id, username, password, role, firstName, lastName);

        databaseUsers.child(id).setValue(user);

        return user;
    }

    public User addServiceProvider(String username, String password, String firstName, String lastName, String street, String city, String province, String postalCode, int phoneNumber, String companyName, String generalDescription, boolean isLicensed) {

        String id = databaseUsers.push().getKey();

        User user = createUser(id, username, password, UserRole.SERVICE_PROVIDER, firstName, lastName);

        databaseUsers.child(id).setValue(user);
        databaseUsers.child(id).child("phoneNumber").setValue(phoneNumber);
        databaseUsers.child(id).child("companyName").setValue(companyName);
        databaseUsers.child(id).child("generalDescription").setValue(generalDescription);
        databaseUsers.child(id).child("isLicensed").setValue(isLicensed);

        AddressDatabase addressDatabase = AddressDatabase.getInstance();
        addressDatabase.addAddress(id, street, city, province, postalCode);

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
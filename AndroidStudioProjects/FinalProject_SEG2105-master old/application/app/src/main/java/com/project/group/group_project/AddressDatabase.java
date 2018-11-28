package com.project.group.group_project;

import android.provider.Telephony;
import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddressDatabase {

    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final DatabaseReference databaseAddresses = database.getReference("address");

    private static final AddressDatabase instance = new AddressDatabase();

    private static List<Address> addresses = new ArrayList<Address>();

    static {

        databaseAddresses.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                addresses.clear();

                for (DataSnapshot ds: dataSnapshot.getChildren()) {

                    String street = ds.child("street").getValue(String.class);
                    String id = ds.child("id").getValue(String.class);
                    String serviceProviderId = ds.child("serviceProviderId").getValue(String.class);
                    String city = ds.child("city").getValue(String.class);
                    String province = ds.child("province").getValue(String.class);
                    String postalCode = ds.child("postalCode").getValue(String.class);

                    Address address = new Address(id, serviceProviderId, street, city, province, postalCode);

                    addresses.add(address);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private AddressDatabase() {}

    public static AddressDatabase getInstance() {
        return instance;
    }

    public Address addAddress(String serviceProviderId, String street, String city, String province, String postalCode) {

        String id = databaseAddresses.push().getKey();

        Address address = new Address(id, serviceProviderId, street, city, province, postalCode);

        databaseAddresses.child(id).setValue(address);

        return address;
    }

    public Address getAddress(String serviceProviderId) {

        for (Address address: addresses) {
            if (address.getServiceProviderId().equals(serviceProviderId)) {
                return address;
            }
        }

        return null;
    }

    public List<Address> getAddresses() {
        return addresses;
    }
}
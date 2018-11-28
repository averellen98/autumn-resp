package com.project.group.group_project;

import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ServiceDatabase {

    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final DatabaseReference databaseServices = database.getReference("services");

    private static List<Service> services = new ArrayList<Service>();

    static {

        databaseServices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                services.clear();

                for (DataSnapshot ds: dataSnapshot.getChildren()) {

                    String name = ds.child("name").getValue(String.class);
                    String description = ds.child("description").getValue(String.class);
                    int ratePerHour = ds.child("ratePerHour").getValue(Integer.class);
                    String id = ds.child("id").getValue(String.class);

                    Service service = new Service(id, name, description, ratePerHour);

                    services.add(service);

                    //Log.i("INFO", name);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public boolean isServiceAlreadyInDatabase(String name) {

        for (Service service: services) {

            if (service.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    public Service addService(String name, String description, int ratePerHour) {

        String id = databaseServices.push().getKey();

        Service service = new Service(id, name, description, ratePerHour);

        databaseServices.child(id).setValue(service);

        return service;
    }

    public void updateService(String originalName, String name, String description, int ratePerHour) {

        Service service = getService(originalName);

        DatabaseReference tmpRef = databaseServices.child(service.getId());

        tmpRef.child("name").setValue(name);
        tmpRef.child("description").setValue(description);
        tmpRef.child("ratePerHour").setValue(ratePerHour);
    }

    public Service getService(String name) {

        for (Service service: services) {
            if (service.getName().equals(name)) {
                return service;
            }
        }

        return null;
    }

    public boolean deleteService(Service service) {

        DatabaseReference tmpRef = databaseServices.child(service.getId());

        tmpRef.removeValue();

        return true;
    }

    public List<Service> getServices() {

        return services;
    }

}
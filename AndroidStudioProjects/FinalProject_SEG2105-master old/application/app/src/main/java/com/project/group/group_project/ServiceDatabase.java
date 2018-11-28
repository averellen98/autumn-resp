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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceDatabase {

    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final DatabaseReference databaseServices = database.getReference("services");

    private static final DatabaseReference databaseServiceAndProvider = database.getReference("serviceAndProvider");

    private static final ServiceDatabase instance = new ServiceDatabase();

    private static List<Service> services = new ArrayList<Service>();

    private static List<ServiceAndProviderTuple> serviceAndProviderTuples = new ArrayList<>();

    private static Map<String, List<Service>> providerServicesMap = new HashMap<>();

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
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseServiceAndProvider.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                serviceAndProviderTuples.clear();

                for (DataSnapshot ds: dataSnapshot.getChildren()) {

                    String id = ds.child("id").getValue(String.class);
                    String serviceProviderId = ds.child("serviceProviderId").getValue(String.class);
                    String serviceId = ds.child("serviceId").getValue(String.class);

                    ServiceAndProviderTuple serviceAndProviderTuple = new ServiceAndProviderTuple(id, serviceProviderId, serviceId);

                    serviceAndProviderTuples.add(serviceAndProviderTuple);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        providerServicesMap.clear();

        for (ServiceAndProviderTuple serviceAndProviderTuple: serviceAndProviderTuples) {

            for (Service service: services) {

                if (service.getId().equals(serviceAndProviderTuple.serviceId)) {

                    if (providerServicesMap.get(serviceAndProviderTuple.serviceProviderId) != null) {

                        providerServicesMap.get(serviceAndProviderTuple.serviceProviderId).add(service);

                    } else {

                        providerServicesMap.put(serviceAndProviderTuple.serviceProviderId, Arrays.asList(service));
                    }
                    continue;
                }
            }
        }
    }

    private ServiceDatabase() {

    }

    public static ServiceDatabase getInstance() {
        return instance;
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

    public ServiceAndProviderTuple addServiceToServiceProvider(String serviceProviderId, String serviceId) {

        String id = databaseServiceAndProvider.push().getKey();

        ServiceAndProviderTuple serviceAndProviderTuple = new ServiceAndProviderTuple(id, serviceProviderId, serviceId);

        databaseServiceAndProvider.child(id).setValue(serviceAndProviderTuple);

        return serviceAndProviderTuple;
    }

    public boolean deleteServiceFromProvider(String serviceProviderId, String serviceId){
        DatabaseReference tmpRef = databaseServiceAndProvider.child(serviceId);
        tmpRef.removeValue();
        return true;
    }

    public void updateService(String originalName, String name, String description, int ratePerHour) {

        Service service = getService(originalName);

        if (service != null) {

            DatabaseReference tmpRef = databaseServices.child(service.getId());

            tmpRef.child("name").setValue(name);
            tmpRef.child("description").setValue(description);
            tmpRef.child("ratePerHour").setValue(ratePerHour);
        }
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

    public List<Service> getServiceForProvider(String serviceProviderId) {

        List<Service> servicesToReturn = providerServicesMap.get(serviceProviderId);

        return servicesToReturn;
    }

    public List<Service> getServices() {

        return services;
    }

    private static class ServiceAndProviderTuple {

        String serviceProviderId;
        String serviceId;
        String id;

        ServiceAndProviderTuple(String id, String serviceProviderId, String serviceId) {

            this.id = id;
            this.serviceId = serviceId;
            this.serviceProviderId = serviceProviderId;
        }
    }
}

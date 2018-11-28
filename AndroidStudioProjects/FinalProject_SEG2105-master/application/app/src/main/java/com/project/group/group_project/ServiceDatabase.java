package com.project.group.group_project;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServiceDatabase {

    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final DatabaseReference databaseServices = database.getReference("services");

    private static final DatabaseReference databaseServiceAndProvider = database.getReference("serviceAndProvider");

    private static final RatingDatabase ratingDatabase = RatingDatabase.getInstance();

    private static final ServiceDatabase instance = new ServiceDatabase();

    private static List<Service> serviceList = new ArrayList<Service>();

    private static List<ServiceAndProviderTuple> serviceAndProviderTuples = new ArrayList<>();

    static {

        databaseServices.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                serviceList.clear();

                for (DataSnapshot ds: dataSnapshot.getChildren()) {

                    String name = ds.child("name").getValue(String.class);
                    String description = ds.child("description").getValue(String.class);
                    int ratePerHour = ds.child("ratePerHour").getValue(Integer.class);
                    String id = ds.child("id").getValue(String.class);

                    int rating = ratingDatabase.getRatingForService(id);

                    databaseServices.child(id).child("rating").setValue(rating);

                    Service service = new Service(id, name, description, ratePerHour, rating);

                    serviceList.add(service);
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
    }

    private ServiceDatabase() {

    }

    public static ServiceDatabase getInstance() {
        return instance;
    }

    public boolean isServiceAlreadyInDatabase(String name) {

        for (Service service: serviceList) {

            if (service.getName().equals(name)) {
                return true;
            }
        }

        return false;
    }

    public Service addService(String name, String description, int ratePerHour) {

        String id = databaseServices.push().getKey();

        Service service = new Service(id, name, description, ratePerHour, 0);

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

        ServiceAndProviderTuple tupleToDelete = getServiceAndProviderTuple(serviceProviderId, serviceId);

        DatabaseReference tmpRef = databaseServiceAndProvider.child(tupleToDelete.id);
        tmpRef.removeValue();

        return true;
    }

    public void updateServiceRatings() {

        for (Service service: serviceList) {

            int rating = RatingDatabase.getInstance().getRatingForService(service.getId());

            service.setRating(rating);

            databaseServices.child(service.getId()).child("rating").setValue(rating);
        }
    }

    private ServiceAndProviderTuple getServiceAndProviderTuple(String serviceProviderId, String serviceId) {

        ServiceAndProviderTuple tupleToReturn = null;

        for (ServiceAndProviderTuple serviceAndProviderTuple: serviceAndProviderTuples) {

            if (serviceAndProviderTuple.serviceId.equals(serviceId) && serviceAndProviderTuple.serviceProviderId.equals(serviceProviderId)) {
                tupleToReturn = serviceAndProviderTuple;
            }
        }

        return tupleToReturn;
    }

    public void updateService(String originalName, String name, String description, int ratePerHour) {

        Service service = getServiceByName(originalName);

        if (service != null) {

            DatabaseReference tmpRef = databaseServices.child(service.getId());

            tmpRef.child("name").setValue(name);
            tmpRef.child("description").setValue(description);
            tmpRef.child("ratePerHour").setValue(ratePerHour);

            int rating = RatingDatabase.getInstance().getRatingForService(service.getId());

            tmpRef.child("rating").setValue(rating);
        }
    }

    public Service getServiceByName(String name) {

        for (Service service: serviceList) {

            if (service.getName().equals(name)) {
                return service;
            }
        }

        return null;
    }

    public Service getServiceById(String id) {

        for (Service service: serviceList) {

            if (service.getId().equals(id)) {
                return service;
            }
        }

        return null;
    }

    public List<Availability> getAvailabilitiesForService(String serviceId) {

        List<Availability> availabilities = new ArrayList<>();

        for (ServiceAndProviderTuple stp: serviceAndProviderTuples) {

            if (stp.serviceId.equals(serviceId)) {

                availabilities.addAll(AvailabilityDatabase.getInstance().getAvailabilitiesByServiceProvider(stp.serviceProviderId));
            }
        }

        return availabilities;
    }

    /**
     * @param serviceName if null don't use in query.
     * @param startHour if -1 don't use in query.
     * @param endHour if -1 don't use in query.
     * @param rating if -1 don't use in query.
     * @return
     */
    public List<Service> queryDatabaseForHomeOwner(String serviceName, int startHour, int endHour, int rating) {

        List<Service> currentlyOfferedServices = getAllCurrentlyOfferedServices();

        if (serviceName == null) {

            if (startHour == -1) {

                if (endHour == -1) {

                    if (rating == -1) {
                        return currentlyOfferedServices;
                    }

                    return getServicesWithRating(currentlyOfferedServices, rating);
                }

                currentlyOfferedServices = getServicesWithEndHour(currentlyOfferedServices, endHour);

                if (rating == -1) {
                    return currentlyOfferedServices;
                }

                return getServicesWithEndHour(currentlyOfferedServices, endHour);
            }

            // startHour is not -1

            currentlyOfferedServices = getServicesWithStartHour(currentlyOfferedServices, startHour);

            if (endHour == -1) {

                if (rating == -1) {
                    return currentlyOfferedServices;
                }

                return getServicesWithRating(currentlyOfferedServices, rating);
            }

            currentlyOfferedServices = getServicesWithEndHour(currentlyOfferedServices, endHour);

            if (rating == -1) {
                return currentlyOfferedServices;
            }

            return getServicesWithEndHour(currentlyOfferedServices, endHour);
        }

        currentlyOfferedServices = getServicesContainingName(currentlyOfferedServices, serviceName);

        if (startHour == -1) {

            if (endHour == -1) {

                if (rating == -1) {
                    return currentlyOfferedServices;
                }

                return getServicesWithRating(currentlyOfferedServices, rating);
            }

            currentlyOfferedServices = getServicesWithEndHour(currentlyOfferedServices, endHour);

            if (rating == -1) {
                return currentlyOfferedServices;
            }

            return getServicesWithEndHour(currentlyOfferedServices, endHour);
        }

        // startHour is not -1

        currentlyOfferedServices = getServicesWithStartHour(currentlyOfferedServices, startHour);

        if (endHour == -1) {

            if (rating == -1) {
                return currentlyOfferedServices;
            }

            return getServicesWithRating(currentlyOfferedServices, rating);
        }

        currentlyOfferedServices = getServicesWithEndHour(currentlyOfferedServices, endHour);

        if (rating == -1) {
            return currentlyOfferedServices;
        }

        return getServicesWithEndHour(currentlyOfferedServices, endHour);
    }

    private List<Service> getServicesContainingName(Collection<Service> services, String name) {

        List<Service> list = new ArrayList<>();

        for (Service service: services) {

            if (service.getName().contains(name)) {
                list.add(service);
            } else if (service.getDescription().contains(name)) {
                list.add(service);
            }
        }

        return list;
    }

    private List<Service> getServicesWithRating(Collection<Service> services, int rating) {

        List<Service> listToReturn = new ArrayList<Service>();

        for (Service service: services) {

            if (rating == service.getRating()){
                listToReturn.add(service);
            }
        }

        return listToReturn;
    }

    private List<Service> getServicesWithEndHour(Collection<Service> services, int endHour) {

        Set<Service> listToReturn = new HashSet<>();

        for (Service service: services) {

            for (ServiceAndProviderTuple spt: serviceAndProviderTuples) {

                if (service.getId().equals(spt.serviceId)) {

                    List<Availability> availabilities = AvailabilityDatabase.getInstance().getAvailabilitiesByServiceProvider(spt.serviceProviderId);

                    for (Availability availability: availabilities) {

                        if (availability.getEndHour() >= endHour) {
                            listToReturn.add(service);
                            continue;
                        }
                    }
                    continue;
                }
            }
        }

        List<Service> list = new ArrayList<>();
        list.addAll(listToReturn);

        return list;
    }

    private List<Service> getServicesWithStartHour(Collection<Service> services, int startHour) {

        Set<Service> listToReturn = new HashSet<>();

        for (Service service: services) {

            for (ServiceAndProviderTuple spt: serviceAndProviderTuples) {

                if (service.getId().equals(spt.serviceId)) {

                    List<Availability> availabilities = AvailabilityDatabase.getInstance().getAvailabilitiesByServiceProvider(spt.serviceProviderId);

                    for (Availability availability: availabilities) {

                        if (availability.getStartHour() <= startHour) {
                            listToReturn.add(service);
                            continue;
                        }
                    }
                    continue;
                }
            }
        }

        List<Service> list = new ArrayList<>();
        list.addAll(listToReturn);

        return list;
    }

    private List<Service> getAllCurrentlyOfferedServices() {

        List<Service> offered = new ArrayList<Service>();

        for (Service service: serviceList) {

            for (ServiceAndProviderTuple spt: serviceAndProviderTuples) {

                if (service.getId().equals(spt.serviceId)) {
                    offered.add(service);
                }
            }
        }

        return offered;
    }

    public boolean deleteService(Service service) {

        DatabaseReference tmpRef = databaseServices.child(service.getId());

        tmpRef.removeValue();

        return true;
    }

    public List<Service> getServiceForProvider(String serviceProviderId) {

        List<Service> servicesToReturn = new ArrayList<>();

        for (Service service: serviceList) {

            for (ServiceAndProviderTuple tuple: serviceAndProviderTuples) {

                if (service.getId().equals(tuple.serviceId) && serviceProviderId.equals(tuple.serviceProviderId)) {

                    servicesToReturn.add(service);
                }
            }
        }

        return servicesToReturn;
    }

    public List<ServiceAndProviderTuple> getServiceAndProviderTuples(){
        return serviceAndProviderTuples;
    }

    public String getSPIDByServiceID(String serviceId){
        ServiceAndProviderTuple tupleToReturn;
        String serviceIdToReturn = null;

        for (ServiceAndProviderTuple serviceAndProviderTuple: serviceAndProviderTuples) {

            if (serviceAndProviderTuple.getServiceId().equals(serviceId)) {
                tupleToReturn = serviceAndProviderTuple;
                serviceIdToReturn = tupleToReturn.getSPId();
            }
        }

        return serviceIdToReturn;
    }

    public List<Service> getAllServices() {

        return serviceList;
    }

    public static class ServiceAndProviderTuple {

        String serviceProviderId;
        String serviceId;
        String id;

        ServiceAndProviderTuple(String id, String serviceProviderId, String serviceId) {

            this.id = id;
            this.serviceId = serviceId;
            this.serviceProviderId = serviceProviderId;
        }

        public String getSPId(){
            return serviceProviderId;
        }
        public String getServiceId(){
            return serviceId;
        }
        public String getId(){
            return id;
        }
    }
}
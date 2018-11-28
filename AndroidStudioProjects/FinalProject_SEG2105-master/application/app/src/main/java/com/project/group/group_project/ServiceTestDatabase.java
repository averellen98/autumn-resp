package com.project.group.group_project;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ServiceTestDatabase {

    private static List<Service> serviceList = new ArrayList<Service>();

    private static List<ServiceTestDatabase.ServiceAndProviderTuple> serviceAndProviderTuples = new ArrayList<>();

    public ServiceTestDatabase() {
        addService("Test1", "this is test 1", 1);
        addService("Test2", "this is test 2", 1);
        addService("Test3", "this is test 3", 1);
        addService("Test4", "this is test 4", 1);
        addService("Test5", "this is test 5", 1);
        addService("Test6", "this is test 6", 1);
        addService("Test7", "this is test 7", 1);
        addService("Test8", "this is test 8", 1);
        addService("Test9", "this is test 9", 1);
        addService("Test10", "this is test 10", 1);
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

        String id = "serviceTestID";

        Service service = new Service(id, name, description, ratePerHour, 0);

        serviceList.add(service);

        return service;
    }

    public ServiceTestDatabase.ServiceAndProviderTuple addServiceToServiceProvider(String serviceProviderId, String serviceId) {

        String id = "serviceAndProviderTupleTestID";

        ServiceTestDatabase.ServiceAndProviderTuple serviceAndProviderTuple = new ServiceTestDatabase.ServiceAndProviderTuple(id, serviceProviderId, serviceId);

        serviceAndProviderTuples.add(serviceAndProviderTuple);

        return serviceAndProviderTuple;
    }

    public boolean deleteServiceFromProvider(String serviceProviderId, String serviceId){

        ServiceTestDatabase.ServiceAndProviderTuple tupleToDelete = getServiceAndProviderTuple(serviceProviderId, serviceId);

        serviceAndProviderTuples.remove(tupleToDelete);

        return true;
    }

    public void updateServiceRatings() {

        for (Service service: serviceList) {

            int rating = RatingDatabase.getInstance().getRatingForService(service.getId());

            service.setRating(rating);
        }
    }

    private ServiceTestDatabase.ServiceAndProviderTuple getServiceAndProviderTuple(String serviceProviderId, String serviceId) {

        ServiceTestDatabase.ServiceAndProviderTuple tupleToReturn = null;

        for (ServiceTestDatabase.ServiceAndProviderTuple serviceAndProviderTuple: serviceAndProviderTuples) {

            if (serviceAndProviderTuple.serviceId.equals(serviceId) && serviceAndProviderTuple.serviceProviderId.equals(serviceProviderId)) {
                tupleToReturn = serviceAndProviderTuple;
            }
        }

        return tupleToReturn;
    }

    public void updateService(String originalName, String name, String description, int ratePerHour) {

        Service service = getServiceByName(originalName);


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

        for (ServiceTestDatabase.ServiceAndProviderTuple stp: serviceAndProviderTuples) {

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

            for (ServiceTestDatabase.ServiceAndProviderTuple spt: serviceAndProviderTuples) {

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

            for (ServiceTestDatabase.ServiceAndProviderTuple spt: serviceAndProviderTuples) {

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

            for (ServiceTestDatabase.ServiceAndProviderTuple spt: serviceAndProviderTuples) {

                if (service.getId().equals(spt.serviceId)) {
                    offered.add(service);
                }
            }
        }

        return offered;
    }

    public boolean deleteService(Service service) {

        serviceList.remove(service);

        return true;
    }

    public List<Service> getServiceForProvider(String serviceProviderId) {

        List<Service> servicesToReturn = new ArrayList<>();

        for (Service service: serviceList) {

            for (ServiceTestDatabase.ServiceAndProviderTuple tuple: serviceAndProviderTuples) {

                if (service.getId().equals(tuple.serviceId) && serviceProviderId.equals(tuple.serviceProviderId)) {

                    servicesToReturn.add(service);
                }
            }
        }

        return servicesToReturn;
    }

    public List<ServiceTestDatabase.ServiceAndProviderTuple> getServiceAndProviderTuples(){
        return serviceAndProviderTuples;
    }

    public String getSPIDByServiceID(String serviceId){
        ServiceTestDatabase.ServiceAndProviderTuple tupleToReturn;
        String serviceIdToReturn = null;

        for (ServiceTestDatabase.ServiceAndProviderTuple serviceAndProviderTuple: serviceAndProviderTuples) {

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

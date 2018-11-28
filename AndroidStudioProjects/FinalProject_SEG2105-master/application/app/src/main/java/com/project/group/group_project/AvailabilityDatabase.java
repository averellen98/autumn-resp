package com.project.group.group_project;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AvailabilityDatabase {

    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final DatabaseReference databaseAvailabilities = database.getReference("availabilities");

    private static final AvailabilityDatabase instance = new AvailabilityDatabase();

    private static List<Availability> availabilities = new ArrayList<Availability>();

    static {

        databaseAvailabilities.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                availabilities.clear();

                for (DataSnapshot ds: dataSnapshot.getChildren()) {

                    String weekDayString = ds.child("weekDay").getValue(String.class);
                    String id = ds.child("id").getValue(String.class);
                    String serviceProviderId = ds.child("serviceProviderId").getValue(String.class);
                    int startHour = ds.child("startHour").getValue(Integer.class);
                    int startMinute = ds.child("startMinute").getValue(Integer.class);
                    int endHour = ds.child("endHour").getValue(Integer.class);
                    int endMinute = ds.child("endMinute").getValue(Integer.class);

                    Util.WeekDay weekDay = Util.WeekDay.getWeekDayByName(weekDayString);

                    Availability availability = new Availability(id, serviceProviderId, weekDay, startHour, startMinute, endHour, endMinute);

                    availabilities.add(availability);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private AvailabilityDatabase() {}

    public static AvailabilityDatabase getInstance() {
        return instance;
    }

    public Availability addAvailability(String serviceProviderId, Util.WeekDay weekDay, int startHour, int startMinute, int endHour, int endMinute) {

        Availability availability = getAvailability(serviceProviderId, weekDay);

        if (availability == null) {

            String id = databaseAvailabilities.push().getKey();

            Availability availability1 = new Availability(id, serviceProviderId, weekDay, startHour, startMinute, endHour, endMinute);

            databaseAvailabilities.child(id).setValue(availability1);
        } else {
            updateAvailability(serviceProviderId, weekDay, startHour, startMinute, endHour, endMinute);
        }

        return availability;
    }

    private void updateAvailability(String serviceProviderId, Util.WeekDay weekDay, int startHour, int startMinute, int endHour, int endMinute) {

        Availability availability = getAvailability(serviceProviderId, weekDay);

        if (availability != null) {

            DatabaseReference tmpRef = databaseAvailabilities.child(availability.getId());

            tmpRef.child("startHour").setValue(startHour);
            tmpRef.child("startMinute").setValue(startMinute);
            tmpRef.child("endHour").setValue(endHour);
            tmpRef.child("endMinute").setValue(endMinute);
        }
    }

    public boolean deleteAvailability(Availability availability) {

        DatabaseReference tmpRef = databaseAvailabilities.child(availability.getId());

        tmpRef.removeValue();

        return true;
    }

    public List<Availability> getAvailabilities() {
        return availabilities;
    }

    public List<Availability> getAvailabilitiesByServiceProvider(String serviceProviderId) {

        List<Availability> serviceProviderAvailabilities = new ArrayList<Availability>();

        for (Availability availability: availabilities) {

            if (availability.getServiceProviderId().equals(serviceProviderId)) {
                serviceProviderAvailabilities.add(availability);
            }
        }

        return serviceProviderAvailabilities;
    }

    private Availability getAvailability(String serviceProviderId, Util.WeekDay weekDay) {

        for (Availability availability: availabilities) {

            if (availability.getServiceProviderId().equals(serviceProviderId) && availability.getWeekDay().equals(weekDay)) {
                return availability;
            }
        }

        return null;
    }

    private boolean isAvailabilityAlreadyInDatabase(Util.WeekDay weekDay, int startHour, int startMinute, int endHour, int endMinute) {

        for (Availability availability: availabilities) {

            if (availability.getWeekDay().equals(weekDay) && availability.getStartHour() == startHour && availability.getStartMinute() == startMinute && availability.getEndHour() == endHour && availability.getEndMinute() == endMinute) {
                return true;
            }
        }

        return false;
    }
}
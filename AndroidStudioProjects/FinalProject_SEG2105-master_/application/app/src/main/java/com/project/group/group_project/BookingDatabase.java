package com.project.group.group_project;

import android.support.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BookingDatabase {

    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final DatabaseReference databaseBookings = database.getReference("bookings");

    private static final BookingDatabase instance = new BookingDatabase();

    private static List<Booking> bookingList = new ArrayList<>();

    static {

        databaseBookings.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                bookingList.clear();

                for (DataSnapshot ds: dataSnapshot.getChildren()) {

                    String id = ds.child("id").getValue(String.class);
                    String homeOwnerId = ds.child("homeOwnerId").getValue(String.class);
                    String serviceId = ds.child("serviceId").getValue(String.class);
                    String serviceProviderId = ds. child("serviceProviderId").getValue(String.class);
                    int startHour = ds.child("startHour").getValue(Integer.class);
                    int startMinute = ds.child("startMinute").getValue(Integer.class);
                    int endHour = ds.child("endHour").getValue(Integer.class);
                    int endMinute = ds.child("endMinute").getValue(Integer.class);
                    int day = ds.child("day").getValue(Integer.class);
                    int month = ds.child("month").getValue(Integer.class);
                    int year = ds.child("year").getValue(Integer.class);

                    Booking booking = new Booking(id, homeOwnerId, serviceId, serviceProviderId, startHour, startMinute, endHour, endMinute, day, month, year);

                    bookingList.add(booking);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private BookingDatabase() {}

    public static BookingDatabase getInstance() { return instance; }

    public Booking addBooking(String homeOwnerId, String serviceId, String serviceProviderId, int startHour, int startMinute, int endHour, int endMinute, int day, int month, int year) {

        String id = databaseBookings.push().getKey();

        Booking booking = new Booking(id, homeOwnerId, serviceId, serviceProviderId, startHour, startMinute, endHour, endMinute, day, month, year);

        databaseBookings.child(id).setValue(booking);

        return booking;
    }

    public List<Booking> getBookingByHomeOwner(String homeOwnerId) {

        List<Booking> homeOwnerBookings = new ArrayList<>();

        for (Booking booking: bookingList) {

            if (booking.getHomeOwnerId().equals(homeOwnerId)) {
                homeOwnerBookings.add(booking);
            }
        }

        return homeOwnerBookings;
    }

    public Booking getBookingById(String id) {

        for (Booking booking: bookingList) {

            if (booking.getId().equals(id)) {
                return booking;
            }
        }

        return null;
    }
}
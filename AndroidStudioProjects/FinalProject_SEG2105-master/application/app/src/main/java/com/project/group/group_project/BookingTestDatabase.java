package com.project.group.group_project;

import java.util.ArrayList;
import java.util.List;

public class BookingTestDatabase {


    private static List<Booking> bookingList = new ArrayList<>();

    public BookingTestDatabase() {
        addBooking("h1", "Test1", "sp1", 12, 0, 13, 0, 1, 1, 2019);
        addBooking("userTestID", "Test1", "sp1", 12, 0, 13, 0, 1, 1, 2019);
        addBooking("h4", "Test1", "sp1", 12, 0, 13, 0, 1, 1, 2019);

    }

    public List<Booking> getAllBookings(){
        return bookingList;
    }

    public Booking addBooking(String homeOwnerId, String serviceId, String serviceProviderId, int startHour, int startMinute, int endHour, int endMinute, int day, int month, int year) {

        String id = "bookingTestID";

        Booking booking = new Booking(id, homeOwnerId, serviceId, serviceProviderId, startHour, startMinute, endHour, endMinute, day, month, year);

        bookingList.add(booking);

        return booking;
    }

    public static boolean isValidBooking(Booking booking){
        ServiceTestDatabase serviceTestDatabase = new ServiceTestDatabase();
        List<Service> services = serviceTestDatabase.getAllServices();
        for (Service service: services){
            if (service.getId().equals(booking.getServiceId())){
                return true;
            }
        }
        return false;
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

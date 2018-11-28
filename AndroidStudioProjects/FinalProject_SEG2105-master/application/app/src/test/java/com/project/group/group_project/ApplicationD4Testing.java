package com.project.group.group_project;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class ApplicationD4Testing {

    @Test
    public void verify_QueryDBForHomeOwner() {
        ServiceTestDatabase serviceTestDatabase = new ServiceTestDatabase();
        List<Service> listServices = serviceTestDatabase.queryDatabaseForHomeOwner("Test0", 12, 14, 1);
        boolean result = false;

        if (listServices.size() == 0){
            result = false;
        } else {
            result = true;
        }
        assertEquals(false, result);
    }

    @Test
    public void verify_QueryDBForServiceNotAvailable(){
        ServiceTestDatabase serviceTestDatabase = new ServiceTestDatabase();
        Service thisService = serviceTestDatabase.getAllServices().get(0);
        boolean result = false;

        if (serviceTestDatabase.isServiceAlreadyInDatabase(thisService.getName())){
            List<Service> listServices = serviceTestDatabase.queryDatabaseForHomeOwner(thisService.getName(), 12, 14, 1);
            if (listServices.contains(thisService)){
                result = true;
            }
            else {
                result = false;
            }
        }
        assertEquals(false, result);
    }

    @Test
    public void verify_BookingReturnsFalse(){
        BookingTestDatabase bookingTestDatabase = new BookingTestDatabase();
        List<Booking> booking = bookingTestDatabase.getAllBookings();
        Booking book = booking.get(0);
        boolean result = bookingTestDatabase.isValidBooking(book);

        assertEquals(false, result);
    }

    @Test
    public void verify_BookingReturnsTrue() {
        BookingTestDatabase bookingTestDatabase = new BookingTestDatabase();
        ServiceTestDatabase serviceTestDatabase = new ServiceTestDatabase();
        List<Service> services = serviceTestDatabase.getAllServices();

        Booking booking = bookingTestDatabase.addBooking("ho", "serviceTestID", "sp", 12, 0, 13, 0, 1, 2, 2019);
        booking.setServiceId("serviceTestID");

        boolean result = bookingTestDatabase.isValidBooking(booking);
        assertEquals(true, result);
    }

    @Test
    public void verify_RatingReturnsTrue(){
        ServiceTestDatabase serviceTestDatabase = new ServiceTestDatabase();
        RatingTestDatabase ratingTestDatabase = new RatingTestDatabase();
        List<Service> services = serviceTestDatabase.getAllServices();

        Service service = services.get(0);
        List<Rating> ratingsForService = ratingTestDatabase.getRatingsByService(service.getId());
        boolean result = false;
        if (ratingsForService.size() != 0){
            result = true;
        }
        assertTrue(result);
    }

    @Test
    public void verify_RatingReturnsFalse(){
        RatingTestDatabase ratingTestDatabase = new RatingTestDatabase();

        Service service = new Service("testID", "service Test", "this is a testing service", 4, 5);
        List<Rating> ratingsForService = ratingTestDatabase.getRatingsByService(service.getId());
        boolean result = false;
        if (ratingsForService.size() != 0){
            result = true;
        }
        assertFalse(result);
    }

    @Test
    public void verify_BookingByHomeOwner_Functionality(){
        BookingTestDatabase bookingTestDatabase = new BookingTestDatabase();
        UserTestDatabase userTestDatabase = new UserTestDatabase();
        User thisUser = userTestDatabase.addUser("homeOwnerTest", "home", UserRole.HOME_OWNER, "home", "owner");

        List<Booking> bookingHomeOwner = bookingTestDatabase.getBookingByHomeOwner(thisUser.getId());

        boolean result = false;
        if (bookingHomeOwner.size() != 0){
            result = true;
        }
        assertEquals(true, result);
    }

    @Test
    public void verify_AvailabilityIsValid(){
        ServiceTestDatabase serviceTestDatabase = new ServiceTestDatabase();
        BookingTestDatabase bookingTestDatabase = new BookingTestDatabase();
        Booking booking = bookingTestDatabase.getAllBookings().get(0);
        ServiceProvider sp = new ServiceProvider("spTest", "sp", "sp", UserRole.SERVICE_PROVIDER);

        Availability monday = new Availability("monday", "sp1", Util.WeekDay.MONDAY, 1, 0, 23, 0);
        Availability tuesday = new Availability("tuesday", "sp1", Util.WeekDay.TUESDAY, 1, 0, 23, 0);
        Availability wednesday = new Availability("wednesday", "sp1", Util.WeekDay.WEDNESDAY, 1, 0, 23, 0);
        Availability thursday = new Availability("thursday", "sp1", Util.WeekDay.THURSDAY, 1, 0, 23, 0);
        Availability friday = new Availability("friday", "sp1", Util.WeekDay.FRIDAY, 1, 0, 23, 0);

        List<Availability> availabilities = new ArrayList<Availability>();
        availabilities.add(monday);
        availabilities.add(tuesday);
        availabilities.add(wednesday);
        availabilities.add(thursday);
        availabilities.add(friday);
        sp.setAvailabilities(availabilities);

        List<Availability> a = serviceTestDatabase.getAvailabilitiesForService("serviceTestID");
        Availability avail = sp.getAvailabilities().get(0);
        boolean result = false;
        if (booking.getStartHour() >= avail.getStartHour() && booking.getEndHour() <= avail.getEndHour()){
            result = true;
        }
        assertEquals(true, result);
    }

    @Test
    public void verify_RatingForServices_Functionality(){
        ServiceTestDatabase serviceTestDatabase = new ServiceTestDatabase();
        RatingTestDatabase ratingTestDatabase = new RatingTestDatabase();
        Service service = serviceTestDatabase.getAllServices().get(0);
        List<Rating> ratings = ratingTestDatabase.getRatingsByService(service.getId());

        boolean result = false;
        if (ratings.size() != 0){
            result = true;
        }
        assertTrue(result);
    }

    @Test
    public void verify_GetServicesByRating_Functionality(){
        ServiceTestDatabase serviceTestDatabase = new ServiceTestDatabase();
        RatingTestDatabase ratingTestDatabase = new RatingTestDatabase();
        Service service = new Service("serviceT", "service Test", "service testing description", 9, 5);
        ratingTestDatabase.addRating(service.getId(), "this is a test rate", 5);
        Service service2 = serviceTestDatabase.getAllServices().get(0);
        int rate = ratingTestDatabase.getRatingForService(service.getId());
        int rate2 = ratingTestDatabase.getRatingForService(service2.getId());
        int searchRate = 3;

        boolean result = false;
        if (rate >= searchRate){
            result = true;
        }
        assertEquals(true, result);

        if (rate2 >= searchRate){
            result = true;
        }
        assertNotEquals(false, result);
    }

}

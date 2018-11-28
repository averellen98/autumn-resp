package com.project.group.group_project;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class CreateBooking extends Activity {
    private String serviceName;
    private String userId;
    private static final ServiceDatabase serviceDatabase = ServiceDatabase.getInstance();
    private static final BookingDatabase bookingDatabase = BookingDatabase.getInstance();
    private List<Service> allServices = serviceDatabase.getAllServices();
    //private List<Booking> allBookings = bookingDatabase.getAllBookings();
    private List<ServiceDatabase.ServiceAndProviderTuple> serviceAndProviderTuples = serviceDatabase.getServiceAndProviderTuples();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_booking);

        Intent intent = getIntent();
        serviceName = intent.getStringExtra(Util.SERVICE_NAME);
        userId = intent.getStringExtra(Util.USER_ID);

    }

    private boolean validateComponents() {

        String startHour = ((TextView) findViewById(R.id.bookingStartHourText)).getText().toString();

        if (!Util.validateHour(startHour)) {

            Toast.makeText(this, "Start hour is invalid.", Toast.LENGTH_LONG).show();
            return false;
        }

        int startHH = Integer.parseInt(startHour);

        String startMinute = ((TextView) findViewById(R.id.bookingStartMinuteText)).getText().toString();

        if (!Util.validateMinute(startMinute)) {

            Toast.makeText(this, "Start minute is invalid.", Toast.LENGTH_LONG).show();
            return false;
        }

        int startMM = Integer.parseInt(startMinute);

        String endHour = ((TextView) findViewById(R.id.bookingEndHourText)).getText().toString();

        if (!Util.validateHour(endHour)) {

            Toast.makeText(this, "End hour is invalid.", Toast.LENGTH_LONG).show();
            return false;
        }

        int endHH = Integer.parseInt(endHour);

        String endMinute = ((TextView) findViewById(R.id.bookingEndMinuteText)).getText().toString();

        if (!Util.validateMinute(endMinute)) {

            Toast.makeText(this, "End hour is invalid.", Toast.LENGTH_LONG).show();
            return false;
        }

        int endMM = Integer.parseInt(endMinute);

        String dayString = ((TextView) findViewById(R.id.bookingDayText)).getText().toString();

        if (!Util.validateInteger(dayString)) {

            Toast.makeText(this, "Day is invalid.", Toast.LENGTH_LONG).show();
            return false;
        }

        int day = Integer.parseInt(dayString);

        String monthString = ((TextView) findViewById(R.id.bookingMonthText)).getText().toString();

        if (!Util.validateInteger(monthString)) {

            Toast.makeText(this, "Month is invalid.", Toast.LENGTH_LONG).show();
            return false;
        }

        int month = Integer.parseInt(monthString);

        if (!Util.validateMonth(month)) {

            Toast.makeText(this, "Month is out of range, please enter a month between 1-12.", Toast.LENGTH_LONG).show();
            return false;
        }

        String yearString = ((TextView) findViewById(R.id.bookingYearText)).getText().toString();

        if (!Util.validateInteger(yearString)) {

            Toast.makeText(this, "Year is invalid.", Toast.LENGTH_LONG).show();
            return false;
        }

        int year = Integer.parseInt(yearString);

        if (!Util.validateDay(day, month, year)) {

            Toast.makeText(this, "Day is invalid for the entered month and year.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!Util.validateDateHasNotPassed(day, month, year)) {

            Toast.makeText(this, "Date is today or has already passed, please enter a later date.", Toast.LENGTH_LONG).show();
            return false;
        }

        // TODO implement validation of start time and end time with service provider availabilities for the selected service.

        return true;
    }
}
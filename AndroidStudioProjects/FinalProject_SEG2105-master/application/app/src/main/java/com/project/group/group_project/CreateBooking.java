package com.project.group.group_project;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.List;

public class CreateBooking extends Activity {

    private static final ServiceDatabase serviceDatabase = ServiceDatabase.getInstance();
    private static final BookingDatabase bookingDatabase = BookingDatabase.getInstance();

    private String serviceId;
    private String userId;

    private TextView bookingStartHourText;
    private TextView bookingStartMinuteText;
    private TextView bookingEndHourText;
    private TextView bookingEndMinuteText;
    private TextView bookingDayText;
    private TextView bookingMonthText;
    private TextView bookingYearText;

    private List<Availability> serviceAvailabilities;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_booking);

        Intent intent = getIntent();
        serviceId = intent.getStringExtra(Util.SERVICE_ID);
        userId = intent.getStringExtra(Util.USER_ID);

        serviceAvailabilities = serviceDatabase.getAvailabilitiesForService(serviceId);

        String availabilityString = Util.buildAvailabilityString(serviceAvailabilities);

        ((TextView) findViewById(R.id.serviceProviderAvailabilityText)).setText(availabilityString);
    }

    public void cancelCreateBookingOnClick(View view) {

        Intent intent = new Intent(this, HomeOwnerSearchServices.class);
        intent.putExtra(Util.USER_ID, userId);

        startActivity(intent);
    }

    public void createBookingOnClick(View view) {
        bookingStartHourText = findViewById(R.id.bookingStartHourText);
        bookingStartMinuteText = findViewById(R.id.bookingStartMinuteText);
        bookingEndHourText = findViewById(R.id.bookingEndHourText);
        bookingEndMinuteText = findViewById(R.id.bookingEndMinuteText);
        bookingDayText = findViewById(R.id.bookingDayText);
        bookingMonthText = findViewById(R.id.bookingMonthText);
        bookingYearText = findViewById(R.id.bookingYearText);
        boolean isValidBooking = validateComponents();

        if (isValidBooking) {

            String serviceProviderId = serviceDatabase.getSPIDByServiceID(serviceId);

            if (serviceProviderId == null || serviceProviderId.isEmpty()){

                Toast.makeText(this, "There is no service providers for this service. Sorry, try again.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, HomeOwnerSearchServices.class);
                intent.putExtra(Util.USER_ID, userId);
                startActivity(intent);

            } else {

                int startHour = Integer.parseInt(bookingStartHourText.getText().toString());
                int startMinute = Integer.parseInt(bookingStartMinuteText.getText().toString());
                int endHour = Integer.parseInt(bookingEndHourText.getText().toString());
                int endMinute = Integer.parseInt(bookingEndMinuteText.getText().toString());
                int day = Integer.parseInt(bookingDayText.getText().toString());
                int month = Integer.parseInt(bookingMonthText.getText().toString());
                int year = Integer.parseInt(bookingYearText.getText().toString());

                bookingDatabase.addBooking(userId, serviceId, serviceProviderId, startHour, startMinute, endHour, endMinute, day, month, year);
                Intent intent = new Intent(this, HomeOwnerBookings.class);
                intent.putExtra(Util.USER_ID, userId);
                startActivity(intent);

            }
        }

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

        Util.WeekDay weekDayOfBooking = Util.getWeekDayForDate(day, month, year);

        boolean availableSlotFound = false;

        for (Availability availability: serviceAvailabilities) {

            if (availability.getWeekDay().equals(weekDayOfBooking)) {

                if (availability.getStartHour() < startHH) {

                    if (availability.getEndHour() > endHH) {
                        availableSlotFound = true;
                    }

                    if (availability.getEndHour() == endHH && availability.getEndMinute() >= endMM) {
                        availableSlotFound = true;
                    }
                }

                if (availability.getStartHour() == startHH && availability.getStartMinute() <= startMM) {

                    if (availability.getEndHour() > endHH) {
                        availableSlotFound = true;
                    }

                    if (availability.getEndHour() == endHH && availability.getEndMinute() >= endMM) {
                        availableSlotFound = true;
                    }
                }
            }
        }

        if (availableSlotFound == false) {
            Toast.makeText(this, "There is not a service provider with that that time available, please enter a different time slot.", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}
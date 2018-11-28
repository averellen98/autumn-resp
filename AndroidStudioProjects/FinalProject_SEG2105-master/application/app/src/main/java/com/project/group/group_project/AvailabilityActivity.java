package com.project.group.group_project;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class AvailabilityActivity extends Activity {

    private static final AvailabilityDatabase availabilityDatabase = AvailabilityDatabase.getInstance();

    private String serviceProviderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability);

        Intent intent = getIntent();
        serviceProviderId = intent.getStringExtra(Util.USER_ID);

        List<Availability> availabilityList = availabilityDatabase.getAvailabilitiesByServiceProvider(serviceProviderId);

        if (!availabilityList.isEmpty()) {

            for (Availability availability: availabilityList) {

                switch (availability.getWeekDay()) {
                    case SUNDAY:
                        ((TextView) findViewById(R.id.sunStartHourText)).setText(String.valueOf(availability.getStartHour()));
                        ((TextView) findViewById(R.id.sunStartMinuteText)).setText(String.valueOf(availability.getStartMinute()));
                        ((TextView) findViewById(R.id.sunEndHourText)).setText(String.valueOf(availability.getEndHour()));
                        ((TextView) findViewById(R.id.sunEndMinuteText)).setText(String.valueOf(availability.getEndMinute()));
                        break;
                    case MONDAY:
                        ((TextView) findViewById(R.id.mondayStartHourText)).setText(String.valueOf(availability.getStartHour()));
                        ((TextView) findViewById(R.id.mondayStartMinuteText)).setText(String.valueOf(availability.getStartMinute()));
                        ((TextView) findViewById(R.id.mondayEndHour)).setText(String.valueOf(availability.getEndHour()));
                        ((TextView) findViewById(R.id.mondayEndMinute)).setText(String.valueOf(availability.getEndMinute()));
                        break;
                    case TUESDAY:
                        ((TextView) findViewById(R.id.tuesStartHour)).setText(String.valueOf(availability.getStartHour()));
                        ((TextView) findViewById(R.id.tuesStartMinute)).setText(String.valueOf(availability.getStartMinute()));
                        ((TextView) findViewById(R.id.tuesEndHour)).setText(String.valueOf(availability.getEndHour()));
                        ((TextView) findViewById(R.id.tuesEndMinute)).setText(String.valueOf(availability.getEndMinute()));
                        break;
                    case WEDNESDAY:
                        ((TextView) findViewById(R.id.wedStartHour)).setText(String.valueOf(availability.getStartHour()));
                        ((TextView) findViewById(R.id.wedStartMinute)).setText(String.valueOf(availability.getStartMinute()));
                        ((TextView) findViewById(R.id.wedEndHour)).setText(String.valueOf(availability.getEndHour()));
                        ((TextView) findViewById(R.id.wedEndMinute)).setText(String.valueOf(availability.getEndMinute()));
                        break;
                    case THURSDAY:
                        ((TextView) findViewById(R.id.thursStartHour)).setText(String.valueOf(availability.getStartHour()));
                        ((TextView) findViewById(R.id.thursStartMinute)).setText(String.valueOf(availability.getStartMinute()));
                        ((TextView) findViewById(R.id.thursEndHour)).setText(String.valueOf(availability.getEndHour()));
                        ((TextView) findViewById(R.id.thursEndMinute)).setText(String.valueOf(availability.getEndMinute()));
                        break;
                    case FRIDAY:
                        ((TextView) findViewById(R.id.friStartHour)).setText(String.valueOf(availability.getStartHour()));
                        ((TextView) findViewById(R.id.friStartMinute)).setText(String.valueOf(availability.getStartMinute()));
                        ((TextView) findViewById(R.id.friEndHour)).setText(String.valueOf(availability.getEndHour()));
                        ((TextView) findViewById(R.id.friEndMinute)).setText(String.valueOf(availability.getEndMinute()));
                        break;
                    case SATURDAY:
                        ((TextView) findViewById(R.id.satStartHour)).setText(String.valueOf(availability.getStartHour()));
                        ((TextView) findViewById(R.id.satStartMinute)).setText(String.valueOf(availability.getStartMinute()));
                        ((TextView) findViewById(R.id.satEndHour)).setText(String.valueOf(availability.getEndHour()));
                        ((TextView) findViewById(R.id.satEndMinute)).setText(String.valueOf(availability.getEndMinute()));
                        break;
                }
            }
        }
    }

    public void cancelAvailabilitiesOnClick(View view) {

        Intent intent = new Intent(this, ServiceProviderView.class);
        intent.putExtra(Util.USER_ID, serviceProviderId);

        startActivity(intent);
    }

    public void saveAvailabilitiesOnClick(View view) {

        int sunStartHour = validateAndRetrieveHour(R.id.sunStartHourText);
        int sunStartMin = validateAndRetrieveMinute(R.id.sunStartMinuteText);
        int sunEndHour = validateAndRetrieveHour(R.id.sunEndHourText);
        int sunEndMin = validateAndRetrieveMinute(R.id.sunEndMinuteText);

        if (Util.startIsBeforeEnd(sunStartHour, sunStartMin, sunEndHour, sunEndMin)) {

            availabilityDatabase.addAvailability(serviceProviderId, Util.WeekDay.SUNDAY, sunStartHour, sunStartMin, sunEndHour, sunEndMin);
        } else {
            Toast.makeText(this, "Sunday start time is after end time, availability will not be updated for this day.", Toast.LENGTH_LONG).show();
        }

        int monStartHour = validateAndRetrieveHour(R.id.mondayStartHourText);
        int monStartMin = validateAndRetrieveMinute(R.id.mondayStartMinuteText);
        int monEndHour = validateAndRetrieveHour(R.id.mondayEndHour);
        int monEndMin = validateAndRetrieveMinute(R.id.mondayEndMinute);

        if (Util.startIsBeforeEnd(monStartHour, monStartMin, monEndHour, monEndMin)) {

            availabilityDatabase.addAvailability(serviceProviderId, Util.WeekDay.MONDAY, monStartHour, monStartMin, monEndHour, monEndMin);
        } else {
            Toast.makeText(this, "Monday start time is after end time, availability will not be updated for this day.", Toast.LENGTH_LONG).show();
        }

        int tuesStartHour = validateAndRetrieveHour(R.id.tuesStartHour);
        int tuesStartMin = validateAndRetrieveMinute(R.id.tuesStartMinute);
        int tuesEndHour = validateAndRetrieveHour(R.id.tuesEndHour);
        int tuesEndMin = validateAndRetrieveMinute(R.id.tuesEndMinute);

        if (Util.startIsBeforeEnd(tuesStartHour, tuesStartMin, tuesEndHour, tuesEndMin)) {

            availabilityDatabase.addAvailability(serviceProviderId, Util.WeekDay.TUESDAY, tuesStartHour, tuesStartMin, tuesEndHour, tuesEndMin);
        } else {
            Toast.makeText(this, "Tuesday start time is after end time, availability will not be updated for this day.", Toast.LENGTH_LONG).show();
        }

        int wedStartHour = validateAndRetrieveHour(R.id.wedStartHour);
        int wedStartMin = validateAndRetrieveMinute(R.id.wedStartMinute);
        int wedEndHour = validateAndRetrieveHour(R.id.wedEndHour);
        int wedEndMin = validateAndRetrieveMinute(R.id.wedEndMinute);

        if (Util.startIsBeforeEnd(wedStartHour, wedStartMin, wedEndHour, wedEndMin)) {

            availabilityDatabase.addAvailability(serviceProviderId, Util.WeekDay.WEDNESDAY, wedStartHour, wedStartMin, wedEndHour, wedEndMin);
        } else {
            Toast.makeText(this, "Wednesday start time is after end time, availability will not be updated for this day.", Toast.LENGTH_LONG).show();
        }

        int thursStartHour = validateAndRetrieveHour(R.id.thursStartHour);
        int thursStartMin = validateAndRetrieveMinute(R.id.thursStartMinute);
        int thursEndHour = validateAndRetrieveHour(R.id.thursEndHour);
        int thursEndMin = validateAndRetrieveMinute(R.id.thursEndMinute);

        if (Util.startIsBeforeEnd(thursStartHour, thursStartMin, thursEndHour, thursEndMin)) {

            availabilityDatabase.addAvailability(serviceProviderId, Util.WeekDay.THURSDAY, thursStartHour, thursStartMin, thursEndHour, thursEndMin);
        } else {
            Toast.makeText(this, "Wednesday start time is after end time, availability will not be updated for this day.", Toast.LENGTH_LONG).show();
        }

        int friStartHour = validateAndRetrieveHour(R.id.friStartHour);
        int friStartMin = validateAndRetrieveMinute(R.id.friStartMinute);
        int friEndHour = validateAndRetrieveHour(R.id.friEndHour);
        int friEndMin = validateAndRetrieveMinute(R.id.friEndMinute);

        if (Util.startIsBeforeEnd(friStartHour, friStartMin, friEndHour, friEndMin)) {

            availabilityDatabase.addAvailability(serviceProviderId, Util.WeekDay.FRIDAY, friStartHour, friStartMin, friEndHour, friEndMin);
        } else {
            Toast.makeText(this, "Wednesday start time is after end time, availability will not be updated for this day.", Toast.LENGTH_LONG).show();
        }

        int satStartHour = validateAndRetrieveHour(R.id.satStartHour);
        int satStartMin = validateAndRetrieveMinute(R.id.satStartMinute);
        int satEndHour = validateAndRetrieveHour(R.id.satEndHour);
        int satEndMin = validateAndRetrieveMinute(R.id.satEndMinute);

        if (Util.startIsBeforeEnd(satStartHour, satStartMin, satEndHour, satEndMin)) {
            availabilityDatabase.addAvailability(serviceProviderId, Util.WeekDay.SATURDAY, satStartHour, satStartMin, satEndHour, satEndMin);
        } else {
            Toast.makeText(this, "Saturday start time is after end time, availability will not be updated for this day.", Toast.LENGTH_LONG).show();
        }

        Intent intent = new Intent(this, ServiceProviderView.class);
        intent.putExtra(Util.USER_ID, serviceProviderId);
        startActivity(intent);
    }

    private int validateAndRetrieveHour(int id) {

        String hour = ((TextView) findViewById(id)).getText().toString();

        if (Util.validateHour(hour)) {
            return Integer.parseInt(hour);
        }

        Toast.makeText(this, "Hour is out of bounds, will be replaced with 0.", Toast.LENGTH_LONG).show();

        return 0;
    }

    private int validateAndRetrieveMinute(int id) {

        String minute = ((TextView) findViewById(id)).getText().toString();

        if (Util.validateMinute(minute)) {
            return Integer.parseInt(minute);
        }

        Toast.makeText(this, "Minute is out of bounds, will be replaced with 0.", Toast.LENGTH_LONG).show();

        return 0;
    }
}
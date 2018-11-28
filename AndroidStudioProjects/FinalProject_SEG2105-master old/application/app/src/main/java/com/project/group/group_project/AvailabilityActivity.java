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

    public static final String SERVICE_PROVIDER_ID = "service_provider_id";

    private String serviceProviderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_availability);

        Intent intent = getIntent();
        serviceProviderId = intent.getStringExtra(SERVICE_PROVIDER_ID);

        List<Availability> availabilityList = availabilityDatabase.getAvailabilitiesByServiceProvider(serviceProviderId);

        if (!availabilityList.isEmpty()) {

            for (Availability availability: availabilityList) {

                switch (availability.getWeekDay()) {
                    case SUNDAY:
                        ((TextView) findViewById(R.id.sunStartHourText)).setText(availability.getStartHour());
                        ((TextView) findViewById(R.id.sunStartMinuteText)).setText(availability.getStartMinute());
                        ((TextView) findViewById(R.id.sunEndHourText)).setText(availability.getEndHour());
                        ((TextView) findViewById(R.id.sunEndMinuteText)).setText(availability.getEndMinute());
                        break;
                    case MONDAY:
                        ((TextView) findViewById(R.id.mondayStartHourText)).setText(availability.getStartHour());
                        ((TextView) findViewById(R.id.mondayStartMinuteText)).setText(availability.getStartMinute());
                        ((TextView) findViewById(R.id.mondayEndHour)).setText(availability.getEndHour());
                        ((TextView) findViewById(R.id.mondayEndMinute)).setText(availability.getEndMinute());
                        break;
                    case TUESDAY:
                        ((TextView) findViewById(R.id.tuesStartHour)).setText(availability.getStartHour());
                        ((TextView) findViewById(R.id.tuesStartMinute)).setText(availability.getStartMinute());
                        ((TextView) findViewById(R.id.tuesEndHour)).setText(availability.getEndHour());
                        ((TextView) findViewById(R.id.tuesEndMinute)).setText(availability.getEndMinute());
                        break;
                    case WEDNESDAY:
                        ((TextView) findViewById(R.id.wedStartHour)).setText(availability.getStartHour());
                        ((TextView) findViewById(R.id.wedStartMinute)).setText(availability.getStartMinute());
                        ((TextView) findViewById(R.id.wedEndHour)).setText(availability.getEndHour());
                        ((TextView) findViewById(R.id.wedEndMinute)).setText(availability.getEndMinute());
                        break;
                    case THURSDAY:
                        ((TextView) findViewById(R.id.thursStartHour)).setText(availability.getStartHour());
                        ((TextView) findViewById(R.id.thursStartMinute)).setText(availability.getStartMinute());
                        ((TextView) findViewById(R.id.thursEndHour)).setText(availability.getEndHour());
                        ((TextView) findViewById(R.id.thursEndMinute)).setText(availability.getEndMinute());
                        break;
                    case FRIDAY:
                        ((TextView) findViewById(R.id.friStartHour)).setText(availability.getStartHour());
                        ((TextView) findViewById(R.id.friStartMinute)).setText(availability.getStartMinute());
                        ((TextView) findViewById(R.id.friEndHour)).setText(availability.getEndHour());
                        ((TextView) findViewById(R.id.friEndMinute)).setText(availability.getEndMinute());
                        break;
                    case SATURDAY:
                        ((TextView) findViewById(R.id.satStartHour)).setText(availability.getStartHour());
                        ((TextView) findViewById(R.id.satStartMinute)).setText(availability.getStartMinute());
                        ((TextView) findViewById(R.id.satEndHour)).setText(availability.getEndHour());
                        ((TextView) findViewById(R.id.satEndMinute)).setText(availability.getEndMinute());
                        break;
                }
            }
        }
    }

    public void saveAvailabilitiesOnClick(View view) {

        int sunStartHour = validateAndRetrieveHour(R.id.sunStartHourText);
        int sunStartMin = validateAndRetrieveMinute(R.id.sunStartMinuteText);
        int sunEndHour = validateAndRetrieveHour(R.id.sunEndHourText);
        int sunEndMin = validateAndRetrieveMinute(R.id.sunEndMinuteText);

        availabilityDatabase.addAvailability(serviceProviderId, Util.WeekDay.SUNDAY, sunStartHour, sunStartMin, sunEndHour, sunEndMin);

        int monStartHour = validateAndRetrieveHour(R.id.mondayStartHourText);
        int monStartMin = validateAndRetrieveMinute(R.id.mondayStartMinuteText);
        int monEndHour = validateAndRetrieveHour(R.id.mondayEndHour);
        int monEndMin = validateAndRetrieveMinute(R.id.mondayEndMinute);

        availabilityDatabase.addAvailability(serviceProviderId, Util.WeekDay.MONDAY, monStartHour, monStartMin, monEndHour, monEndMin);

        int tuesStartHour = validateAndRetrieveHour(R.id.tuesStartHour);
        int tuesStartMin = validateAndRetrieveMinute(R.id.tuesStartMinute);
        int tuesEndHour = validateAndRetrieveHour(R.id.tuesEndHour);
        int tuesEndMin = validateAndRetrieveMinute(R.id.tuesEndMinute);

        availabilityDatabase.addAvailability(serviceProviderId, Util.WeekDay.TUESDAY, tuesStartHour, tuesStartMin, tuesEndHour, tuesEndMin);

        int wedStartHour = validateAndRetrieveHour(R.id.wedStartHour);
        int wedStartMin = validateAndRetrieveMinute(R.id.wedStartMinute);
        int wedEndHour = validateAndRetrieveHour(R.id.wedEndHour);
        int wedEndMin = validateAndRetrieveMinute(R.id.wedEndMinute);

        availabilityDatabase.addAvailability(serviceProviderId, Util.WeekDay.WEDNESDAY, wedStartHour, wedStartMin, wedEndHour, wedEndMin);

        int thursStartHour = validateAndRetrieveHour(R.id.thursStartHour);
        int thursStartMin = validateAndRetrieveMinute(R.id.thursStartMinute);
        int thursEndHour = validateAndRetrieveHour(R.id.thursEndHour);
        int thursEndMin = validateAndRetrieveMinute(R.id.thursEndMinute);

        availabilityDatabase.addAvailability(serviceProviderId, Util.WeekDay.THURSDAY, thursStartHour, thursStartMin, thursEndHour, thursEndMin);

        int friStartHour = validateAndRetrieveHour(R.id.friStartHour);
        int friStartMin = validateAndRetrieveMinute(R.id.friStartMinute);
        int friEndHour = validateAndRetrieveHour(R.id.friEndHour);
        int friEndMin = validateAndRetrieveMinute(R.id.friEndMinute);

        availabilityDatabase.addAvailability(serviceProviderId, Util.WeekDay.FRIDAY, friStartHour, friStartMin, friEndHour, friEndMin);

        int satStartHour = validateAndRetrieveHour(R.id.satStartHour);
        int satStartMin = validateAndRetrieveMinute(R.id.satStartMinute);
        int satEndHour = validateAndRetrieveHour(R.id.satEndHour);
        int satEndMin = validateAndRetrieveMinute(R.id.satEndMinute);

        availabilityDatabase.addAvailability(serviceProviderId, Util.WeekDay.SATURDAY, satStartHour, satStartMin, satEndHour, satEndMin);

        Intent intent = new Intent(this, ServiceProviderView.class);
        intent.putExtra(ServiceProviderView.SERVICE_PROVIDER_ID, serviceProviderId);
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
package com.project.group.group_project;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class HomeOwnerSearchServices extends Activity {

    private TextView serviceNameText;
    private TextView serviceStartHourText;
    private TextView serviceEndHourText;
    private TextView serviceRatingText;

    private String userId;

    private String searchByType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_owner_search_services);

        Intent intent = getIntent();
        userId = intent.getStringExtra(Util.USER_ID);

        serviceNameText = findViewById(R.id.serviceNameText);
        serviceStartHourText = findViewById(R.id.serviceStartHourText);
        serviceEndHourText = findViewById(R.id.serviceEndHourText);
        serviceRatingText = findViewById(R.id.serviceRatingText);
    }

    public void searchForServicesOnClick(View view) {

        if (validateComponents()) {

            String serviceName = (serviceNameText.getText().toString().isEmpty() ? null : serviceNameText.getText().toString());
            int serviceStartHour = Integer.parseInt((serviceStartHourText.getText().toString().isEmpty() ? "-1" : serviceStartHourText.getText().toString()));
            int serviceEndHour = Integer.parseInt((serviceEndHourText.getText().toString().isEmpty() ? "-1" : serviceEndHourText.getText().toString()));
            int serviceRating = Integer.parseInt(serviceRatingText.getText().toString().isEmpty() ? "-1" : serviceRatingText.getText().toString());

            Intent intent = new Intent(this, HomeOwnerFoundServiceList.class);
            intent.putExtra(Util.USER_ID, userId);
            intent.putExtra(Util.SEARCH_NAME, serviceName);
            intent.putExtra(Util.SEARCH_START_HOUR, serviceStartHour);
            intent.putExtra(Util.SEARCH_END_HOUR, serviceEndHour);
            intent.putExtra(Util.SEARCH_RATING, serviceRating);

            startActivity(intent);
        }

        // if none of the fields are filled, show all services that are currently offered

        // if a field is empty, ignore it, otherwise search using the AND operator.
        // first field is serviceName, second fields needs some magic, and third field needs some as well.

        // for second fields: Find ServiceProvider with hours within the boundary hours, then use the services they provide.

        // for third field: gather all the ratings for the service, then calculate the average. If the average (as an int) is the same then return this service.
    }

    private boolean validateComponents() {

        String serviceStartHour = serviceStartHourText.getText().toString();
        String serviceEndHour = serviceEndHourText.getText().toString();
        String serviceRating = serviceRatingText.getText().toString();

        if (!serviceEndHour.isEmpty()) {

            if (!Util.validateInteger(serviceStartHour)) {
                Toast.makeText(this, "Enter a valid integer start hour.", Toast.LENGTH_LONG).show();
                return false;
            }

            if (!Util.validateHour(serviceStartHour)) {
                Toast.makeText(this, "Enter a valid start hour between 0 and 23.", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if (!serviceEndHour.isEmpty()) {

            if (!Util.validateInteger(serviceEndHour)) {
                Toast.makeText(this, "Enter a valid integer end hour.", Toast.LENGTH_LONG).show();
                return false;
            }

            if (!Util.validateHour(serviceEndHour)) {
                Toast.makeText(this, "Enter a valid end hour between 0 and 23.", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        if (!serviceRating.isEmpty()) {

            if (!Util.validateInteger(serviceRating)) {
                Toast.makeText(this, "Enter a valid integer rating.", Toast.LENGTH_LONG).show();
                return false;
            }

            int rating = Integer.parseInt(serviceRating);

            if (!Util.validateRating(rating)) {
                Toast.makeText(this, "Enter a valid rating between 1 and 5.", Toast.LENGTH_LONG).show();
                return false;
            }
        }

        return true;
    }
}
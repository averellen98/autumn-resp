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

    private static final ServiceDatabase serviceDatabase = ServiceDatabase.getInstance();

    private List<Service> allServices = serviceDatabase.getAllServices();

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

        String serviceName = serviceNameText.getText().toString();
        String serviceStartHour = serviceStartHourText.getText().toString();
        String serviceEndHour = serviceEndHourText.getText().toString();
        String serviceRating = serviceRatingText.getText().toString();

        if (serviceName == null || serviceName.isEmpty()){
            if (serviceStartHour == null || serviceStartHour.isEmpty()) {
                if (serviceEndHour == null || serviceEndHour.isEmpty()) {
                    if (serviceRating == null || serviceRating.isEmpty()) {
                        searchByType = "allServices";
                    }
                    else {
                        Toast.makeText(this, "Please Enter a Service Type or Name", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(this, "Please Enter a Service Type or Name", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this, "Please Enter a Service Type or Name", Toast.LENGTH_SHORT).show();
            }
        }

        else if (serviceName != null || !serviceName.isEmpty()){
            if (serviceStartHour == null || serviceStartHour.isEmpty()) {
                if (serviceEndHour == null || serviceEndHour.isEmpty()) {
                    if (serviceRating != null || !serviceRating.isEmpty()) {
                        searchByType = "serviceNameAndRate";
                    }
                    else {
                        Toast.makeText(this, "Please Enter a Rating, or Start and End Time", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    if (serviceRating != null || !serviceRating.isEmpty()) {
                        Toast.makeText(this, "Please enter either a Rating, or Start and End Time", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(this, "Please Enter an Start Time", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            else {
                if (serviceEndHour == null || serviceEndHour.isEmpty()) {
                    if (serviceRating != null || !serviceRating.isEmpty()) {
                        Toast.makeText(this, "Please enter either a Rating, or Start and End Time", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(this, "Please Enter an End Time", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    if (serviceRating != null || !serviceRating.isEmpty()) {
                        Toast.makeText(this, "Please enter either a Rating, or Start and End Time", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        searchByType = "serviceNameAndTime";
                    }
                }
            }

        }


        Intent intent = new Intent(this, HomeOwnerFoundServiceList.class);
        intent.putExtra(Util.USER_ID, userId);
        intent.putExtra("searchByType", searchByType);
        intent.putExtra("serviceName", serviceName);
        intent.putExtra("serviceStart", serviceStartHour);
        intent.putExtra("serviceEnd", serviceStartHour);
        intent.putExtra("serviceRate", serviceRating);
        startActivity(intent);


        // TODO implement this


        // if none of the fields are filled, show all services

        // if a field is empty, ignore it, otherwise search using the AND operator.
        // first field is serviceName, second fields needs some magic, and third field needs some as well.

        // for second fields: Find ServiceProvider with hours within the boundary hours, then use the services they provide.

        // for third field: gather all the ratings for the service, then calculate the average. If the average (as an int) is the same then return this service.
    }

}









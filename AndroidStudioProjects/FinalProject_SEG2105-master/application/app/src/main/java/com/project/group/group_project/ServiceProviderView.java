package com.project.group.group_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ServiceProviderView extends AppCompatActivity {

    private String serviceProviderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceprovider_view);

        Intent intent = getIntent();
        serviceProviderId = intent.getStringExtra(Util.USER_ID);
    }

    public void viewAvailabilitiesOnClick(View view) {

        Intent intent = new Intent(this, AvailabilityActivity.class);
        intent.putExtra(Util.USER_ID, serviceProviderId);

        startActivity(intent);
    }

    public void viewAvailableServicesOnClick(View view) {

        Intent intent = new Intent(this, SPAvailableServiceActivity.class);
        intent.putExtra(Util.USER_ID, serviceProviderId);

        startActivity(intent);
    }

    public void viewOfferedServicesOnClick(View view) {

        Intent intent = new Intent(this, SPViewServicesActivity.class);
        intent.putExtra(Util.USER_ID, serviceProviderId);

        startActivity(intent);
    }

    public void logOutOfServicePOnClick(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
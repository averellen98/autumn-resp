package com.project.group.group_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ServiceProviderView extends AppCompatActivity {

    public static final String SERVICE_PROVIDER_ID = "service_provider_id";

    private String serviceProviderId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serviceprovider_view);

        Intent intent = getIntent();
        serviceProviderId = intent.getStringExtra(SERVICE_PROVIDER_ID);
    }

    public void viewAvailabilitiesOnClick(View view) {

        Intent intent = new Intent(this, AvailabilityActivity.class);
        intent.putExtra(ServiceProviderView.SERVICE_PROVIDER_ID, serviceProviderId);

        startActivity(intent);
    }

    public void viewAvailableServicesOnClick(View view) {

        Intent intent = new Intent(this, SPAvailableServiceActivity.class);
        intent.putExtra(ServiceProviderView.SERVICE_PROVIDER_ID, serviceProviderId);

        startActivity(intent);
    }

    public void viewOfferedServicesOnClick(View view) {

        Intent intent = new Intent(this, SPViewServicesActivity.class);
        intent.putExtra(ServiceProviderView.SERVICE_PROVIDER_ID, serviceProviderId);

        startActivity(intent);
    }
}

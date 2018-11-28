package com.project.group.group_project;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class HomeOwnerView extends AppCompatActivity {

    private String userId;
    private BookingDatabase bkdb = BookingDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homeowner_view);

        Intent intent = getIntent();
        userId = intent.getStringExtra(Util.USER_ID);
    }

    public void searchForServiceOnClick(View view) {

        Intent intent = new Intent(this, HomeOwnerSearchServices.class);
        intent.putExtra(Util.USER_ID, userId);

        startActivity(intent);
    }

    public void viewBookingsOnClick(View view) {

        Intent intent = new Intent(this, HomeOwnerBookings.class);
        intent.putExtra(Util.USER_ID, userId);

        startActivity(intent);
    }

    public void rateServicesOnClick(View view) {

        Intent intent = new Intent(this, HomeOwnerServiceListForRating.class);
        intent.putExtra(Util.USER_ID, userId);

        startActivity(intent);
    }

    public void logOutHOOnClick(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
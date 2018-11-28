package com.project.group.group_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CreateServiceRating extends Activity {

    private ServiceDatabase serviceDatabase = ServiceDatabase.getInstance();
    private RatingDatabase ratingDatabase = RatingDatabase.getInstance();

    private String serviceID;
    private Service serviceToBeRated;

    private TextView serviceTextName;
    private TextView commentText;
    private TextView rateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_owner_rate_service);

        Intent intent = getIntent();
        serviceID = intent.getStringExtra(Util.SERVICE_ID);

        serviceToBeRated = serviceDatabase.getServiceById(serviceID);

        serviceTextName = findViewById(R.id.serviceNameView);
        serviceTextName.setText(serviceToBeRated.getName());

        commentText = findViewById(R.id.ratingComment);
        rateText = findViewById(R.id.ratingRate);
    }

    public void confirmOnClick(View view){

        String serviceComment = commentText.getText().toString();
        String serviceRate = rateText.getText().toString();

        if (isRatingValid(serviceComment, serviceRate)){

            int rateInteger = Integer.parseInt(serviceRate);

            ratingDatabase.addRating(serviceID, serviceComment, rateInteger);

            serviceDatabase.updateServiceRatings();

            Intent intent = new Intent(this, HomeOwnerServiceListForRating.class);
            startActivity(intent);
        }
    }

    private boolean isRatingValid(String serviceComment, String serviceRate){

        if (!Util.validateInteger(serviceRate)) {
            Toast.makeText(this, "Please enter a valid integer rating between 1 and 5.", Toast.LENGTH_LONG).show();
            return false;
        }

        int rateInteger = Integer.parseInt(serviceRate);

        if (serviceComment == null || serviceComment.isEmpty()){
            Toast.makeText(this, "Please enter a comment.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (serviceRate == null || serviceRate.isEmpty()){
            Toast.makeText(this, "Please enter a rating.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (rateInteger <= 0 || rateInteger > 5){
            Toast.makeText(this, "Please enter a rating between 1 and 5.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }
}
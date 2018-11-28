package com.project.group.group_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class CreateServiceRating extends Activity {
    //TODO this is the class that actually implements the rating portion

    private ServiceDatabase serviceDatabase = ServiceDatabase.getInstance();
    private RatingDatabase ratingDatabase = RatingDatabase.getInstance();

    private String serviceName;
    private String serviceID;
    private String serviceDescription;
    private String serviceRatePerHour;
    private TextView serviceTextName;
    private TextView commentText;
    private TextView rateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_owner_rate_service);

        Intent intent = getIntent();
        serviceName = intent.getStringExtra(Util.SERVICE_NAME);
        serviceID = intent.getStringExtra("serviceID");
        serviceDescription = intent.getStringExtra(Util.SERVICE_DESCRIPTION);
        serviceRatePerHour = intent.getStringExtra(Util.SERVICE_RATE);

        serviceTextName = findViewById(R.id.serviceNameView);
        serviceTextName.setText(serviceName);

        commentText = findViewById(R.id.ratingComment);
        rateText = findViewById(R.id.ratingRate);

    }

    public void confirmOnClick(View view){
        String serviceComment = commentText.getText().toString();
        String serviceRate = rateText.getText().toString();
        int rateInteger = Integer.parseInt(serviceRate);

        if (isRatingValid(serviceComment, serviceRate)){
            ratingDatabase.addRating(serviceID, serviceComment, rateInteger);

            Intent intent = new Intent(this, HomeOwnerRateService.class);
            startActivity(intent);

        }
    }

    public boolean isRatingValid(String serviceComment, String serviceRate){
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

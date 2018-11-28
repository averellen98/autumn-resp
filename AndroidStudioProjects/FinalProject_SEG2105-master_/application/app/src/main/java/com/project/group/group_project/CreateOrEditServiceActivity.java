package com.project.group.group_project;

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class CreateOrEditServiceActivity extends Activity {

    private static final ServiceDatabase serviceDatabase = ServiceDatabase.getInstance();

    private boolean isEdit;
    private String originalName;

    private TextView nameText;
    private TextView descriptionText;
    private TextView ratePerHourText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_or_edit_service);

        nameText = findViewById(R.id.serviceNameText);
        descriptionText = findViewById(R.id.serviceDescriptionText);
        ratePerHourText = findViewById(R.id.ratePerHourText);

        try {
            Intent intent = getIntent();
            String name = intent.getStringExtra(Util.SERVICE_NAME);
            String desc = intent.getStringExtra(Util.SERVICE_DESCRIPTION);
            int rate = intent.getIntExtra(Util.SERVICE_RATE, 0);

            if (name != null && !name.isEmpty()) {
                isEdit = true;
                originalName = name;
            }

            double act_rate = rate / 100;

            nameText.setText(name);
            descriptionText.setText(desc);
            ratePerHourText.setText(Double.toString(act_rate));

            if (isEdit) {
                Button createButton = findViewById(R.id.createButton);
                createButton.setText("Update Service");
            }
        } catch (Exception e) {
            //do nothing it may be a create
        }
    }

    public void onClickCreateService(View view) {

        String name = nameText.getText().toString();
        String description = descriptionText.getText().toString();
        String ratePerHourString = ratePerHourText.getText().toString();

        if (isServiceValid(name, description, ratePerHourString)) {

            if (serviceDatabase.isServiceAlreadyInDatabase(name)) {

                if (isEdit) {
                    serviceDatabase.updateService(name, name, description, getRatePerHour(ratePerHourString));

                    Intent intent = new Intent(this, ViewServicesActivity.class);

                    startActivity(intent);
                } else {
                    Toast.makeText(this, "Service has already been created with that name.", Toast.LENGTH_LONG);
                }

            } else {

                if (isEdit) {
                    serviceDatabase.updateService(originalName, name, description, getRatePerHour(ratePerHourString));
                } else {
                    serviceDatabase.addService(name, description, getRatePerHour(ratePerHourString));
                }

                Intent intent = new Intent(this, ViewServicesActivity.class);

                startActivity(intent);
            }
        }
    }

    private int getRatePerHour(String string) {

        Double doubleVal = Double.parseDouble(string);

        return (int) (doubleVal * 100);
    }

    private boolean isServiceValid(String name, String description, String ratePerHour) {

        if  (name == null || name.isEmpty()) {
            Toast.makeText(this, "Please input service name.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (description == null || description.isEmpty()) {
            Toast.makeText(this, "Please input a description.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (ratePerHour == null || ratePerHour.isEmpty()) {
            Toast.makeText(this, "Please input rate per hour.", Toast.LENGTH_LONG).show();
            return false;
        }

        try {
            Double.parseDouble(ratePerHour);
        } catch (Exception e) {
            Toast.makeText(this, "Please input valid numeric rate per hour.", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}
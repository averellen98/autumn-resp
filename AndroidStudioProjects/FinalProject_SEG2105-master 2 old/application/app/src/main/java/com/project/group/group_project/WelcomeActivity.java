package com.project.group.group_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    private String userFirstName;
    private String userRole;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Intent intent = getIntent();
        userFirstName = intent.getStringExtra(MainActivity.USER_FIRSTNAME_KEY);
        userRole = intent.getStringExtra(MainActivity.USER_ROLE_KEY);

        String welcomeMessage = buildWelcomeMessage(userFirstName, userRole);

        setWelcomeText(welcomeMessage);
    }

    private String buildWelcomeMessage(String userFirstName, String userRole) {

        StringBuilder sb = new StringBuilder();

        sb.append("Welcome " + userFirstName + "!\r\n");
        sb.append("You are logged in as " + userRole);

        return sb.toString();
    }

    private void setWelcomeText(String welcomeMessage) {

        TextView textView = findViewById(R.id.welcomeMessageText);
        textView.setText(welcomeMessage);
    }

    public void continueButtonOnClick(View view){

        String role = userRole;
        Toast.makeText(this, role,Toast.LENGTH_SHORT).show();

        if (role.equals("Admin")){
            Intent intent = new Intent(this, AdminView.class);
            startActivity(intent);
        } else if (role.equals("Home Owner")){
            Intent intent = new Intent(this, HomeOwnerView.class);
            startActivity(intent);
        } else if (role.equals("Service Provider")){
            Intent intent = new Intent(this, ServiceProviderView.class);
            startActivity(intent);
        }
    }
}
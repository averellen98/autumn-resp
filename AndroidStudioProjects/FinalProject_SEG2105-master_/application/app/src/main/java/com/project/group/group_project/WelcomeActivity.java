package com.project.group.group_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    private static final UserDatabase userDatabase = UserDatabase.getInstance();

    private User currentUser;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Intent intent = getIntent();
        userId = intent.getStringExtra(Util.USER_ID);
        currentUser = userDatabase.getUserById(userId);

        if (currentUser == null) {
            throw new IllegalStateException();
        }

        String welcomeMessage = buildWelcomeMessage();

        setWelcomeText(welcomeMessage);
    }

    private String buildWelcomeMessage() {

        StringBuilder sb = new StringBuilder();

        sb.append("Welcome " + currentUser.getFirstName() + "!\r\n");
        sb.append("You are logged in as " + currentUser.getRole().getName());

        return sb.toString();
    }

    private void setWelcomeText(String welcomeMessage) {

        TextView textView = findViewById(R.id.welcomeMessageText);
        textView.setText(welcomeMessage);
    }

    public void continueButtonOnClick(View view){

        String role = currentUser.getRole().getName();
        Toast.makeText(this, role,Toast.LENGTH_SHORT).show();

        if (role.equals("Admin")){
            Intent intent = new Intent(this, AdminView.class);
            startActivity(intent);
        } else if (role.equals("Home Owner")){
            Intent intent = new Intent(this, HomeOwnerView.class);
            intent.putExtra(Util.USER_ID, userId);
            startActivity(intent);
        } else if (role.equals("Service Provider")){
            Intent intent = new Intent(this, ServiceProviderView.class);
            intent.putExtra(Util.USER_ID, userId);
            startActivity(intent);
        }
    }
}
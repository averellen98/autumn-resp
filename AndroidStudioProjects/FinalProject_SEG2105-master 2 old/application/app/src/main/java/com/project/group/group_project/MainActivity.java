package com.project.group.group_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    public static final String USER_FIRSTNAME_KEY = "com.project.group.userFirstNameKey";
    public static final String USER_ROLE_KEY = "com.project.group.userRoleKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void logInOnClick(View view) {
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }

    public void signUpOnClick(View view){
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }
}

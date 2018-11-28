package com.project.group.group_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AdminView extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_view);
    }

    public void viewUsersOnClick(View view) {

        Intent intent = new Intent(this, UsersListView.class);
        startActivity(intent);
    }

    public void editServicesOnClick(View view) {

        Intent intent = new Intent(this, ViewServicesActivity.class);
        startActivity(intent);
    }
}

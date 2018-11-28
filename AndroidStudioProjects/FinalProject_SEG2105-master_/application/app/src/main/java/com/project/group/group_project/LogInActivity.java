package com.project.group.group_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LogInActivity extends AppCompatActivity {

    private TextView username;
    private TextView password;

    private UserDatabase userDatabase = UserDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        username = findViewById(R.id.usernameText);
        password = findViewById(R.id.passwordText);
    }


    public void logInOnClick(View view) {

        User user;
        String u_username = username.getText().toString();
        String u_password = password.getText().toString();

        if (userDatabase.isUserInDatabase(u_username, u_password)){

            Toast.makeText(this, "User found.", Toast.LENGTH_SHORT).show();

            user = userDatabase.getUser(u_username, u_password);

            if (user != null) {

                Intent intent = new Intent(this, WelcomeActivity.class);
                intent.putExtra(Util.USER_ID, user.getId());

                startActivity(intent);
            }

        } else {

            Toast.makeText(this, "User not found, please try again.", Toast.LENGTH_SHORT).show();
            username.setText("");
            password.setText("");
        }
    }
}

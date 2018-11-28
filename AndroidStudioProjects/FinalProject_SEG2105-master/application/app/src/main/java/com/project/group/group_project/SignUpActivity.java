package com.project.group.group_project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class SignUpActivity extends AppCompatActivity {

    private TextView username;
    private TextView password;
    private TextView confirmPassword;
    private TextView firstName;
    private TextView lastName;
    private RadioGroup roleGroup;

    private UserDatabase userDatabase = UserDatabase.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        username = findViewById(R.id.usernameText);
        password = findViewById(R.id.passwordText);
        confirmPassword = findViewById(R.id.passwordConfirmText);
        firstName = findViewById(R.id.firstNameText);
        lastName = findViewById(R.id.lastNameText);
        roleGroup = findViewById(R.id.roleRadioGroup);
    }

    public void cancelSignUpOnCLick(View view) {

        Intent intent = new Intent(this, MainActivity.class);

        startActivity(intent);
    }

    public void signUpOnClick(View view) {

        String u_username = username.getText().toString();
        String u_password = password.getText().toString();
        String u_confirmPassword = confirmPassword.getText().toString();
        String roleString = getRoleFromRadioButtonId(roleGroup.getCheckedRadioButtonId());
        String u_firstName = firstName.getText().toString();
        String u_lastName = lastName.getText().toString();

        boolean canBeUser = verifyUserCriteria(u_username, u_password, u_confirmPassword);

        if (canBeUser) {

            if (userDatabase.isUserAlreadyPresentInDatabase(u_username)) {

                Toast.makeText(this, "User has already been created with this username.", Toast.LENGTH_LONG).show();
            } else {

                if (UserRole.getRoleByName(roleString).equals(UserRole.SERVICE_PROVIDER)) {

                    Intent intent = new Intent(this, CreateProfileSPActivity.class);
                    intent.putExtra(Util.LAST_NAME, u_lastName);
                    intent.putExtra(Util.FIRST_NAME, u_firstName);
                    intent.putExtra(Util.PASSWORD, u_password);
                    intent.putExtra(Util.USERNAME, u_username);
                    intent.putExtra(Util.ROLE, roleString);

                    startActivity(intent);

                } else {

                    UserRole role = UserRole.getRoleByName(roleString);

                    User user = userDatabase.addUser(u_username, u_password, role, u_firstName, u_lastName);

                    Log.e("ERROR", user.getId());

                    Intent intent = new Intent(this, WelcomeActivity.class);
                    intent.putExtra(Util.USER_ID, user.getId());

                    startActivity(intent);
                }
            }

        } else {
            Toast.makeText(this, "Please input all required information.", Toast.LENGTH_LONG).show();
        }
    }

    private String getRoleFromRadioButtonId(int id) {

        RadioButton rb = findViewById(id);

        return rb.getText().toString();
    }

    private boolean verifyUserCriteria(String username, String password, String confirmPassword) {

        if (username == null || username.isEmpty()) {
            Toast.makeText(this, "Please enter an username.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (password == null || password.isEmpty()) {
            Toast.makeText(this, "Please input password.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (confirmPassword == null || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please enter confirm password.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Confirm password does not match password.", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}
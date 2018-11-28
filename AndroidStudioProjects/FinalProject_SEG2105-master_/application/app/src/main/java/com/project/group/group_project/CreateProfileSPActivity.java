package com.project.group.group_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

public class CreateProfileSPActivity extends Activity {

    private String username;
    private String password;
    private UserRole role;
    private String firstName;
    private String lastName;

    private TextView companyNameText;
    private TextView descriptionText;
    private TextView streetAddressText;
    private TextView postalCodeText;
    private TextView cityText;
    private TextView provinceText;
    private RadioGroup licensedRadioGroup;
    private TextView phoneNumText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_serviceprovider_profile);



        Intent intent = getIntent();
        username = intent.getStringExtra(Util.USERNAME);
        password = intent.getStringExtra(Util.PASSWORD);
        role = UserRole.getRoleByName(intent.getStringExtra(Util.ROLE));
        firstName = intent.getStringExtra(Util.FIRST_NAME);
        lastName = intent.getStringExtra(Util.LAST_NAME);

        companyNameText = findViewById(R.id.companyNameText);
        descriptionText = findViewById(R.id.generalDescriptionText);
        streetAddressText = findViewById(R.id.streetAddressText);
        postalCodeText = findViewById(R.id.postalCodeText);
        cityText = findViewById(R.id.cityText);
        provinceText = findViewById(R.id.provinceText);
        phoneNumText = findViewById(R.id.phoneNumText);
        licensedRadioGroup = findViewById(R.id.licensedRadioGroup);
    }

    public void onClickCreateProfile(View view) {

        String companyName = companyNameText.getText().toString();
        String description = descriptionText.getText().toString();
        String phoneNum = phoneNumText.getText().toString();
        String street = streetAddressText.getText().toString();
        String postalCode = postalCodeText.getText().toString();
        String city = cityText.getText().toString();
        String province = provinceText.getText().toString();
        String licensed = getRoleFromRadioButtonId(licensedRadioGroup.getCheckedRadioButtonId());

        boolean isLicensed = false;

        if (licensed.equalsIgnoreCase("yes")) {
            isLicensed = true;
        }

        if (isProfileValid(companyName, description, street, postalCode, city, province, phoneNum)) {

            int phoneNumber = Integer.parseInt(phoneNum);

            UserDatabase userDatabase = UserDatabase.getInstance();

            User user = userDatabase.addServiceProvider(username, password, firstName, lastName, street, city, province, postalCode, phoneNumber, companyName, description, isLicensed);

            Intent intent = new Intent(this, ServiceProviderView.class);
            intent.putExtra(Util.USER_ID, user.getId());

            startActivity(intent);
        }
    }

    private String getRoleFromRadioButtonId(int id) {

        RadioButton rb = findViewById(id);

        return rb.getText().toString();
    }

    private boolean isProfileValid(String companyName, String description, String street, String postalCode, String city, String province, String phoneNum) {

        if  (companyName == null || companyName.isEmpty()) {
            Toast.makeText(this, "Please input company name.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (description == null || description.isEmpty()) {
            Toast.makeText(this, "Please input a description.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (street == null || street.isEmpty()) {
            Toast.makeText(this, "Please input a street.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (postalCode == null || postalCode.isEmpty()) {
            Toast.makeText(this, "Please input a postal code.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (city == null || city.isEmpty()) {
            Toast.makeText(this, "Please input a city.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (province == null || province.isEmpty()) {
            Toast.makeText(this, "Please input a province.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (phoneNum == null || phoneNum.isEmpty()) {
            Toast.makeText(this, "Please input a phone number.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!Util.validatePhoneNumber(phoneNum)) {
            Toast.makeText(this, "Please input a valid phone number.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!Util.validateProvince(province)) {
            Toast.makeText(this, "Please input a valid Canadian province. Use the full name.", Toast.LENGTH_LONG).show();
            return false;
        }

        if (!Util.validatePostalCode(postalCode)) {
            Toast.makeText(this, "Please input a valid postal code (eg. K1S4D8).", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }
}
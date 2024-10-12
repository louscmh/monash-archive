package com.fit2081.fit2081assignment3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        sharedPreferences = getSharedPreferences("UNIQUE_FILE_NAME", MODE_PRIVATE);

        String lastUsername = sharedPreferences.getString("KEY_USERNAME", "");
        TextView loginUsername = findViewById(R.id.loginUsername);
        loginUsername.setText(lastUsername);
    }

    public void onLoginPageButtonClick(View view) {
        TextView loginUsername = findViewById(R.id.loginUsername);
        TextView loginPassword = findViewById(R.id.loginPassword);

        String usernameString = loginUsername.getText().toString();
        String passwordString = loginPassword.getText().toString();

        String loginUsernameCheck = sharedPreferences.getString("KEY_USERNAME", "");
        String loginPasswordCheck = sharedPreferences.getString("KEY_PASSWORD", "");

        if (usernameString.equals(loginUsernameCheck) && passwordString.equals(loginPasswordCheck)) {
            Intent intent = new Intent(this, DashboardActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Login Successfully", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Authentication failure: Username or Password incorrect", Toast.LENGTH_SHORT).show();
        }
    }

    public void onRegisterButtonClick(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
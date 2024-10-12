package com.fit2081.fit2081assignment2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onRegisterButtonClick(View view){
        TextView registerUsername = findViewById(R.id.usernameEdit);
        TextView registerPassword = findViewById(R.id.passwordInitialEdit);
        TextView registerPasswordConfirm = findViewById(R.id.passwordConfirmEdit);

        String usernameString = registerUsername.getText().toString();
        String passwordString = registerPassword.getText().toString();
        String passwordConfirmString = registerPasswordConfirm.getText().toString();

        if (!passwordString.equals(passwordConfirmString) || passwordString.equals("") || usernameString.equals("") || !usernameString.matches("^(?!^[\\d\\s]+$)[a-zA-Z\\d\\s]+$")) {
            Toast.makeText(this, "Registration Failure!", Toast.LENGTH_SHORT).show();
            return;
        } else {
            Toast.makeText(this, "Registration Success!", Toast.LENGTH_SHORT).show();
        }

        saveDataToSharedPreference(usernameString,passwordString);

        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void onLoginButtonClick(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }
    private void saveDataToSharedPreference(String usernameValue, String passwordValue){
        SharedPreferences sharedPreferences = getSharedPreferences("UNIQUE_FILE_NAME", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString("KEY_USERNAME", usernameValue);
        editor.putString("KEY_PASSWORD", passwordValue);

        editor.apply();
    }
}
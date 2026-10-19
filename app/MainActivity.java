package com.dayflow.app;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setPadding(40, 60, 40, 40);

        TextView title = new TextView(this);
        title.setText("DayFlow");
        title.setTextSize(32);

        TextView subtitle = new TextView(this);
        subtitle.setText("Login to continue");
        subtitle.setTextSize(18);

        EditText email = new EditText(this);
        email.setHint("Email");

        EditText password = new EditText(this);
        password.setHint("Password");

        Button loginButton = new Button(this);
        loginButton.setText("LOGIN");

        layout.addView(title);
        layout.addView(subtitle);
        layout.addView(email);
        layout.addView(password);
        layout.addView(loginButton);

        setContentView(layout);
    }
}
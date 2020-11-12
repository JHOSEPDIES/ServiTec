package com.example.servitec.ui.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.servitec.ui.MainActivity;
import com.example.servitec.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

    }

    public void ingresar(View v)
    {
        Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
    }
}
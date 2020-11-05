package com.example.servitec.ui.login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;

import com.example.servitec.MainActivity;
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

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            Explode explode = new Explode();
            explode.setDuration(1000);
            getWindow().setExitTransition(explode);
            startActivity(intent, ActivityOptionsCompat.makeSceneTransitionAnimation(this, v ,"").toBundle());
            finish();
        }
        else
        {
            startActivity(intent);
            finish();
        }
    }
}
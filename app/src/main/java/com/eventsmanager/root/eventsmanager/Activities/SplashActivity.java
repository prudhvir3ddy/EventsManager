package com.eventsmanager.root.eventsmanager.Activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.eventsmanager.root.eventsmanager.R;
import com.eventsmanager.root.eventsmanager.utils.SharedPrefs;

public class SplashActivity extends AppCompatActivity {

    SharedPrefs sharedPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        sharedPrefs=new SharedPrefs(this);
Log.d("logom",""+sharedPrefs.getlogin());

        if(sharedPrefs.getopened()==null)
        {
            startActivity(new Intent(SplashActivity.this,Intro.class));
            finish();
        }
        else if (sharedPrefs.getlogin()==null){

            startActivity(new Intent(getApplicationContext(),StudentLogin.class));
            finish();
        }
        else {
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
            finish();
        }


    }
}

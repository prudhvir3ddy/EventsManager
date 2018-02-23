package com.eventsmanager.root.eventsmanager.Activities;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.eventsmanager.root.eventsmanager.R;
import com.eventsmanager.root.eventsmanager.utils.SharedPrefs;
import com.eventsmanager.root.eventsmanager.utils.Urls;

public class Profile extends AppCompatActivity {
SharedPrefs sharedPrefs;
TextView name,email,phone,rollno,branch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        sharedPrefs=new SharedPrefs(this);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Profile");
        name=(TextView)findViewById(R.id.name);
        email=(TextView)findViewById(R.id.email);
        phone=(TextView)findViewById(R.id.phone);
        rollno=(TextView)findViewById(R.id.rollno);
        branch=(TextView)findViewById(R.id.branch);

        branch.setText("Branch: "+sharedPrefs.getLogedInBranch());
        rollno.setText("rollnumber: "+(sharedPrefs.getRollno()));
        name.setText("UserName: "+sharedPrefs.getLogedInUserName());
        email.setText("Email: "+sharedPrefs.getLogedInEmail());
        phone.setText("Phone: "+sharedPrefs.getLogedInPhone());


    }
}

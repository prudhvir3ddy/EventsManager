package com.eventsmanager.root.eventsmanager.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.eventsmanager.root.eventsmanager.R;
import com.eventsmanager.root.eventsmanager.utils.Connection;
import com.eventsmanager.root.eventsmanager.utils.SharedPrefs;
import com.eventsmanager.root.eventsmanager.utils.Urls;

import net.steamcrafted.loadtoast.LoadToast;

import org.json.JSONArray;

public class AddPrizeEvents extends AppCompatActivity {

    EditText e1,e2,e3;
    Button b;
    LoadToast loadToast;
    Connection connection;
    SharedPrefs sharedPrefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_prize_events);
        loadToast=new LoadToast(this);
        loadToast.setText("updating..");
        e1=(EditText)findViewById(R.id.aeventname);
        e2=(EditText)findViewById(R.id.aeventdesc);
        e3=(EditText)findViewById(R.id.aeventyear);
        b=(Button)findViewById(R.id.addparticipated);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (connection.isInternet()) {
                    getdata();
                } else {
                    Toast.makeText(getApplicationContext(), "no internet connection", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    public void getdata()
    {
        loadToast.show();
        String eventname=e1.getText().toString();
        String eventdesc=e2.getText().toString();
        String eventyear=e3.getText().toString();
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.post(Urls.addprize)
                .addBodyParameter("name",sharedPrefs.getLogedInUserName())
                .addBodyParameter("email",sharedPrefs.getLogedInEmail())
                .addBodyParameter("branch",sharedPrefs.getLogedInBranch())
                .addBodyParameter("phone",sharedPrefs.getLogedInPhone())
                .addBodyParameter("rollno",sharedPrefs.getRollno())
                .addBodyParameter("eventname",eventname)
                .addBodyParameter("eventdesc",eventdesc)
                .addBodyParameter("eventyear",eventyear)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("response",""+response);
                        loadToast.success();
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("error",""+anError);
                        loadToast.error();
                    }
                });
    }
}

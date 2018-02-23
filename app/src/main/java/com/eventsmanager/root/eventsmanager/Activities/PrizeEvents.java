package com.eventsmanager.root.eventsmanager.Activities;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.eventsmanager.root.eventsmanager.Adapters.Participation_Adapter;
import com.eventsmanager.root.eventsmanager.Adapters.PrizeEvents_Adapter;
import com.eventsmanager.root.eventsmanager.Models.Participate_Model;
import com.eventsmanager.root.eventsmanager.Models.PrizeEventsModel;
import com.eventsmanager.root.eventsmanager.R;
import com.eventsmanager.root.eventsmanager.utils.Connection;
import com.eventsmanager.root.eventsmanager.utils.SharedPrefs;
import com.eventsmanager.root.eventsmanager.utils.Urls;

import net.steamcrafted.loadtoast.LoadToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class PrizeEvents extends AppCompatActivity {
    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    LoadToast loadToast;
    Connection connection;
    SharedPrefs sharedPrefs;
    FloatingActionButton floatingActionButton;
    PrizeEvents_Adapter adapter;
    List<PrizeEventsModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prize_events);
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Prize Winning Events");
       connection=new Connection(this);
        loadToast = new LoadToast(this);
        loadToast.setText("loading..");
        sharedPrefs=new SharedPrefs(this);
        recyclerView=(RecyclerView)findViewById(R.id.prize_recyclerview);
        floatingActionButton=(FloatingActionButton)findViewById(R.id.fab);
        list = new ArrayList<>();
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        Boolean checkinternet = (connection.isInternet());
        if (checkinternet) {
            Log.d("TAG", "online");
            getData();

        } else {
            Toast.makeText(getApplicationContext(), "no internet connection", Toast.LENGTH_SHORT).show();
        }
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PrizeEvents.this,AddPrizeEvents.class));
            }
        });


    }

    public void getData() {
        loadToast.show();
        Log.d("rollnumer:",""+sharedPrefs.getRollno());
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.post(Urls.prize)
                .addBodyParameter("roll",sharedPrefs.getRollno())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("response",""+response);
                        loadToast.success();
                        parsedata(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        loadToast.error();
                        Log.d("error",""+anError);
                    }
                });
    }

    public void parsedata(JSONArray response) {
        int j = response.length();
        for (int i = j - 1; i >= 0; i--) {
            PrizeEventsModel model = new PrizeEventsModel();
            JSONObject json;
            try {
                json = response.getJSONObject(i);

                if (!json.has("AppKeyError")) {

                    if (!json.has("NoNotifications")) {

                        json = response.getJSONObject(i);

                        model.setEventname(json.getString("eventname"));
                        model.setEventdesc(json.getString("eventdesc"));
                        model.setTime(json.getString("pyear"));

                        list.add(model);

                    } else {
                        Toast.makeText(getApplicationContext(), "No Notifications", Toast.LENGTH_SHORT).show();
                        recyclerView.setVisibility(View.GONE);
                        //  nonotifi.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "please try after sometime", Toast.LENGTH_SHORT).show();

                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "please try after sometime", Toast.LENGTH_SHORT).show();
            }
        }
        adapter = new PrizeEvents_Adapter(list, this);
        recyclerView.setAdapter(adapter);
    }
}

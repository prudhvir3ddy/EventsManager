package com.eventsmanager.root.eventsmanager.Activities;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.eventsmanager.root.eventsmanager.Adapters.Notifications_Adapter;
import com.eventsmanager.root.eventsmanager.Models.NotificationsModel;
import com.eventsmanager.root.eventsmanager.R;
import com.eventsmanager.root.eventsmanager.utils.Connection;
import com.eventsmanager.root.eventsmanager.utils.Urls;

import net.steamcrafted.loadtoast.LoadToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Notifications extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    LoadToast loadToast;
    Connection connection;
    Context context;
    Notifications_Adapter adapter;
    List<NotificationsModel> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notifications);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Notifications");
        recyclerView = findViewById(R.id.notifications_f_recyclerview);
       // nonotifi = findViewById(R.id.nonotifications);
        list = new ArrayList<>();
        loadToast = new LoadToast(this);
        loadToast.setText("Loading...");
        connection = new Connection(getApplicationContext());
        Boolean checkinternet = (connection.isInternet());
        if (checkinternet) {
            Log.d("TAG", "online startted");
            getData();

        } else {
            Toast.makeText(getApplicationContext(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(), HomeActivity.class));
        }

//        recyclerView.addOnItemTouchListener(new Onitemtouchlistener(context, new Onitemtouchlistener.OnItemClickListener() {
//            @Override
//            public void onItemClick(View view, int position) {
//                TextView headtxt = view.findViewById(R.id.home_card_heading);
//                String heading = headtxt.getText().toString();
//                Intent expand = new Intent(getApplicationContext(), Notification_Expand.class);
//                expand.putExtra("heading", heading);
//                startActivity(expand);
//            }
//        }));

    }

    public void getData() {
        loadToast.show();

        AndroidNetworking.post(Urls.NOTIFICATION_URL)
                //.addBodyParameter("APPKEY", getString(R.string.APPKEY))
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        loadToast.success();
                        parsedata(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        loadToast.error();
                        Log.d("error", "" + anError);
                        Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                    }
                });
    }

    public void parsedata(JSONArray array) {
        int j = array.length();
        for (int i = j - 1; i >= 0; i--) {
            NotificationsModel model = new NotificationsModel();
            JSONObject json;
            try {
                json = array.getJSONObject(i);

                if (!json.has("AppKeyError")) {

                    if (!json.has("NoNotifications")) {

                        json = array.getJSONObject(i);
                        model.setImageurl(json.getString("Profileimg"));
                        model.setHeading(json.getString("Heading"));
                        model.setTimestamp(json.getString("Timestamp"));
                        model.setTag(json.getString("Tag"));
                        list.add(model);

                    } else {
                        Toast.makeText(getApplicationContext(), "No Notifications", Toast.LENGTH_SHORT).show();
                        recyclerView.setVisibility(View.GONE);
                      //  nonotifi.setVisibility(View.VISIBLE);
                        recyclerView.setVisibility(View.GONE);
                    }

                } else {
                    Toast.makeText(getApplicationContext(), "please try after sometime", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "please try after sometime", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
            }
        }

        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        adapter = new Notifications_Adapter(list, this);
        recyclerView.setAdapter(adapter);


    }
}

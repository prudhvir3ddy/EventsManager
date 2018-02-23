package com.eventsmanager.root.eventsmanager.Activities;

import android.app.Notification;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.eventsmanager.root.eventsmanager.Adapters.HomeAdapter;
import com.eventsmanager.root.eventsmanager.Models.Home_Model;
import com.eventsmanager.root.eventsmanager.R;
import com.eventsmanager.root.eventsmanager.utils.Connection;
import com.eventsmanager.root.eventsmanager.utils.SharedPrefs;
import com.eventsmanager.root.eventsmanager.utils.Urls;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import net.steamcrafted.loadtoast.LoadToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype.Fall;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;
    LoadToast loadToast;
    Connection connection;
    SwipeRefreshLayout swipeRefreshLayout;
    HomeAdapter adapter;
    List<Home_Model> list;
    NiftyDialogBuilder dialogBuilder;
    TextView nonotifi;
    SharedPrefs sharedPrefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sharedPrefs=new SharedPrefs(this);
        loadToast = new LoadToast(this);
        loadToast.setText("refreshing..");
        swipeRefreshLayout=(SwipeRefreshLayout)findViewById(R.id.swipe);
        recyclerView = (RecyclerView) findViewById(R.id.home_recyclerview);
        dialogBuilder=new NiftyDialogBuilder(this);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        connection = new Connection(getApplicationContext());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                getData();

            }
        });
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        Boolean checkinternet = (connection.isInternet());
        if (checkinternet) {
            Log.d("TAG", "online");

            getData();


        } else {
            Toast.makeText(getApplicationContext(), "no internet connection", Toast.LENGTH_SHORT).show();
        }


    }



    public void getData()
    {
        loadToast.show();
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.post(Urls.eventsmain)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("response",""+response);
                        list = new ArrayList<>();
                        parsedata(response);
                    }

                    @Override
                    public void onError(ANError anError) {
                        loadToast.error();
                        Log.d("error", "" + anError);
                        Toast.makeText(getApplicationContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                });
    }

public void parsedata(JSONArray array)
{
    loadToast.success();
    int j = array.length();
    for (int i = j - 1; i >= 0; i--) {
        Home_Model model = new Home_Model();
        JSONObject json;
        try {
            json = array.getJSONObject(i);

            if (!json.has("AppKeyError")) {

                if (!json.has("NoNotifications")) {

                    json = array.getJSONObject(i);
                    model.setEventimg(json.getString("eventimg"));
                    model.setEventname(json.getString("eventname"));
                    model.setEventurl(json.getString("eventlink"));
                    list.add(model);

                } else {
                    Toast.makeText(getApplicationContext(), "No Notifications", Toast.LENGTH_SHORT).show();
                    recyclerView.setVisibility(View.GONE);
                    nonotifi.setVisibility(View.VISIBLE);
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
    adapter = new HomeAdapter(list, this);
    recyclerView.setAdapter(adapter);
    swipeRefreshLayout.setRefreshing(false);

}
//
//    public void getdata() {
//        AndroidNetworking.initialize(getApplicationContext());
//        AndroidNetworking.post(Urls.)
//                .addBodyParameter("roll",sharedPrefs.getRollno())
//                .setPriority(Priority.MEDIUM)
//                .build()
//                .getAsJSONArray(new JSONArrayRequestListener() {
//                    @Override
//                    public void onResponse(JSONArray response) {
//                        Log.d("response",""+response);
//                        int j=response.length();
//
//                        for (int i = j - 1; i >= 0; i--) {
//
//                            JSONObject json;
//                            try {
//                                json=response.getJSONObject(i);
//
//                                if(!json.has("NoEvents"))
//                                {
//
//                                    Log.d("list",""+list);
//                                }
//                                else {
//
//                                }
//                            } catch (JSONException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//                    }
//
//                    @Override
//                    public void onError(ANError anError) {
//                    Log.d("error",""+anError);
//                    }
//                });
//    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            sharedPrefs.clearlogin();
            sharedPrefs.clearprefs();
            startActivity(new Intent(HomeActivity.this,StudentLogin.class));
            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profile) {
            startActivity(new Intent(HomeActivity.this,Profile.class));

        } else if (id == R.id.participation) {
            startActivity(new Intent(HomeActivity.this,ParticipatedEvents.class));

        } else if (id == R.id.prizeevents) {
            startActivity(new Intent(HomeActivity.this,PrizeEvents.class));
        } else if (id == R.id.intrests) {
            startActivity(new Intent(HomeActivity.this,Intrests.class));

        } else if (id == R.id.report) {
            Intent i = new Intent(Intent.ACTION_SENDTO);
            String uriText = "mailto:" + Uri.encode("prudhvireddy.m01@gmail.com") + "?subject=" +
                    Uri.encode("Reporting A Bug/Feedback") + "&body=" + Uri.encode("Hello, Team Events \nI want to report a bug/give feedback corresponding to the app EventManager...\n\n-Your name");
            Uri uri = Uri.parse(uriText);
            i.setData(uri);
            startActivity(Intent.createChooser(i, "Send Email"));


        }else if (id==R.id.notifcations)
        {
            startActivity(new Intent(HomeActivity.this, Notifications.class));
        }
        else if(id==R.id.license)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(HomeActivity.this);
            builder.setTitle(String.format("%1$s", getString(R.string.opensource) + ":"));
            builder.setMessage(getResources().getText(R.string.licenses_text));
            builder.setPositiveButton("OK", null);
            builder.setIcon(R.drawable.license);
            AlertDialog welcomeAlert = builder.create();
            welcomeAlert.show();
            ((TextView) welcomeAlert.findViewById(android.R.id.message)).setMovementMethod(LinkMovementMethod.getInstance());
        }
        else if (id == R.id.nav_send) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/psychot3ch")));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}

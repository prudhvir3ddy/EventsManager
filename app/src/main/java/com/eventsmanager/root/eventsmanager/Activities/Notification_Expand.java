package com.eventsmanager.root.eventsmanager.Activities;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.eventsmanager.root.eventsmanager.R;
import com.eventsmanager.root.eventsmanager.utils.Connection;
import com.eventsmanager.root.eventsmanager.utils.Urls;

import net.steamcrafted.loadtoast.LoadToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Notification_Expand extends AppCompatActivity {

    TextView tag_text, heading_text, content_text;
    ImageView image_text;
    LoadToast loadToast;
    ProgressBar progress;
    Connection connection;
    ScrollView scrollView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_expand);

        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle("Notification Details");
        Bundle bundle=getIntent().getExtras();
        String heading = bundle.getString("heading");
        loadToast= new LoadToast(this);
        loadToast.setText("loading...");
        loadToast.show();
        tag_text = findViewById(R.id.tag_text);
        heading_text = findViewById(R.id.heading_text);
        content_text = findViewById(R.id.content_text);
        image_text = findViewById(R.id.image_text);
        progress= findViewById(R.id.progress);
        connection=new Connection(this);
        scrollView= findViewById(R.id.notif_exp_scrollview);
        scrollView.setVisibility(View.INVISIBLE);
        Boolean checkinternet =(connection.isInternet());
        if (checkinternet) {
            Log.d("TAG","online startted");
            getData( heading);
        } else {
            Toast.makeText(getApplicationContext(),"No Internet Connection",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),HomeActivity.class));
        }
    }

    public void getData(String heading) {
        loadToast.show();


        AndroidNetworking.post(Urls.NOTIFICATION_EXPAND_URL)
                //.addBodyParameter("APPKEY",getString(R.string.APPKEY))
                .addBodyParameter("Heading",heading)
                .setPriority(Priority.MEDIUM)

                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("response:",""+response);
                        loadToast.success();
                        parsedata(response);
                    }
                    @Override
                    public void onError(ANError anError) {
                        loadToast.error();
                        Toast.makeText(getApplicationContext(),"Please check your internet connection",Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(),HomeActivity.class));
                    }
                });
    }

    public void parsedata(JSONArray array) {
        scrollView.setVisibility(View.VISIBLE);
        int j = array.length();

        for (int i=0;i<j;i++) {
            JSONObject json;
            try {
                json = array.getJSONObject(i);

                if (!json.has("AppKeyError")) {

                    if (!json.has("NoDataFound")) {

                        json = array.getJSONObject(i);
                        Glide.with(getApplicationContext()).load(json.getString("Contentimg")).diskCacheStrategy(DiskCacheStrategy.SOURCE).error(android.R.drawable.ic_dialog_alert).listener(new RequestListener<String, GlideDrawable>() {
                            @Override
                            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

                                progress.setVisibility(View.GONE);
                                return false;
                            }
                        }).into(image_text);
                        heading_text.setText(json.getString("Heading"));
                        tag_text.setText(json.getString("Tag"));
                        content_text.setText(json.getString("Content"));


                    } else {
                        Toast.makeText(getApplicationContext(), "No Data Found", Toast.LENGTH_SHORT).show();

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

    }
}

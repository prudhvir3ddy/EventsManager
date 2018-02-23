package com.eventsmanager.root.eventsmanager.Activities;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.eventsmanager.root.eventsmanager.utils.SharedPrefs;
import com.eventsmanager.root.eventsmanager.utils.Urls;
import com.like.LikeButton;
import com.like.OnLikeListener;

import net.steamcrafted.loadtoast.LoadToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EventActivity extends AppCompatActivity {
 ImageView eventpic;
 ProgressBar progressBar;
 TextView t1;
 SharedPrefs sharedPrefs;
 public String eventlink;
 Button participate;
 LikeButton intrested;
 LoadToast loadToast;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
        loadToast=new LoadToast(this);
        final Bundle bundle = getIntent().getExtras();
        final String eventname = bundle.getString("eventname");
        final boolean likestatus=bundle.getBoolean("bstatus");
        ActionBar actionBar=getSupportActionBar();
        actionBar.setTitle(eventname);
        sharedPrefs=new SharedPrefs(this);
        eventpic=(ImageView)findViewById(R.id.eventimg);
        progressBar=(ProgressBar)findViewById(R.id.progress);
        t1=(TextView)findViewById(R.id.eventdescription);
        intrested=(LikeButton)findViewById(R.id.b1);
        participate=(Button)findViewById(R.id.participate);
        if(likestatus){
            intrested.setLiked(true);
        }
        else
        {
            intrested.setLiked(false);
        }
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.post(Urls.eventsub)
                .addBodyParameter("name",eventname)
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.d("response",""+response);
                        JSONObject jsonObject;
                        int j=response.length();
                        for (int i = j - 1; i >= 0; i--) {
                            try {
                                jsonObject=response.getJSONObject(i);
                                Glide.with(getApplicationContext()).load(jsonObject.getString("eventpic")).diskCacheStrategy(DiskCacheStrategy.SOURCE).error(android.R.drawable.ic_dialog_alert).listener(new RequestListener<String, GlideDrawable>() {
                                    @Override
                                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                                        return false;
                                    }

                                    @Override
                                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                                        progressBar.setVisibility(View.GONE);
                                        return false;
                                    }
                                }).into(eventpic);

                                String eventdesc=jsonObject.getString("eventdescription");
                                String time=jsonObject.getString("time");
                               eventlink=jsonObject.getString("eventlink");
                               t1.setText(eventdesc);

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        }

                        }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("error",""+anError);

                    }
                });


      intrested.setOnLikeListener(new OnLikeListener() {
          @Override
          public void liked(LikeButton likeButton) {
              AndroidNetworking.initialize(getApplicationContext());
              AndroidNetworking.post(Urls.like)
                      .addBodyParameter("rollno", sharedPrefs.getRollno())
                      .addBodyParameter("eventname", eventname)
                      .setPriority(Priority.MEDIUM)
                      .build()
                      .getAsJSONArray(new JSONArrayRequestListener() {
                          @Override
                          public void onResponse(JSONArray response) {
                              Log.d("response", "" + response);
                              intrested.setLiked(true);
                              loadToast.success();
                          }

                          @Override
                          public void onError(ANError anError) {
                              Log.d("error", "" + anError);
                              loadToast.error();
                          }
                      });
          }

          @Override
          public void unLiked(LikeButton likeButton) {
              AndroidNetworking.initialize(getApplicationContext());
              AndroidNetworking.post(Urls.unlike)
                      .addBodyParameter("rollno", sharedPrefs.getRollno())
                      .addBodyParameter("eventname", eventname)
                      .setPriority(Priority.MEDIUM)
                      .build()
                      .getAsJSONArray(new JSONArrayRequestListener() {
                          @Override
                          public void onResponse(JSONArray response) {
                              Log.d("response", "" + response);
                              intrested.setLiked(false);
                              loadToast.success();
                          }

                          @Override
                          public void onError(ANError anError) {
                              Log.d("error", "" + anError);
                              loadToast.error();
                          }
                      });

          }
      });

        participate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(eventlink)));
            }
        });
    }
}

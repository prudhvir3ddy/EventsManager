package com.eventsmanager.root.eventsmanager.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.eventsmanager.root.eventsmanager.Activities.EventActivity;
import com.eventsmanager.root.eventsmanager.Models.Home_Model;
import com.eventsmanager.root.eventsmanager.R;
import com.eventsmanager.root.eventsmanager.utils.SharedPrefs;
import com.eventsmanager.root.eventsmanager.utils.Urls;
import com.like.LikeButton;
import com.like.OnLikeListener;

import net.steamcrafted.loadtoast.LoadToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by root on 13/1/18.
 */

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    List<Home_Model> list;
    Context context;
    SharedPrefs sharedPrefs;
    LoadToast loadToast;

    public HomeAdapter(List<Home_Model> list,Context context) {
        this.list=list;
        this.context=context;
    }

    @Override
    public HomeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_card_model,parent,false);
        HomeViewHolder homeViewHolder = new HomeViewHolder(v);
        return homeViewHolder;
    }

    @Override
    public void onBindViewHolder(HomeViewHolder holder, int position) {
        Home_Model home_model=list.get(position);

        String eventname=home_model.getEventname();
        String eventurl=home_model.getEventurl();

        String likedevents=home_model.getLikedevents();
        Log.d("eventname",""+eventname);
        Log.d("likedevents",""+likedevents);
      handlelikes(holder,eventname);

        holder.eventurl=eventurl;
        holder.eventname=eventname;
        Glide.with(context).load(home_model.getEventimg()).diskCacheStrategy(DiskCacheStrategy.SOURCE).error(R.drawable.ic_error).into(holder.cat_img);
        holder.progressBar.setVisibility(View.GONE);

    }

    private void handlelikes(final HomeViewHolder holder, final String eventname) {
        AndroidNetworking.initialize(context);
        AndroidNetworking.post(Urls.intrests)
                .addBodyParameter("rollno",sharedPrefs.getRollno())
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        int j=response.length();
                        for (int i = j - 1; i >= 0; i--) {
                            JSONObject json;
                            try {
                                json=response.getJSONObject(i);
                                if(!json.has("NoNotifications")){
                                    Log.d(""+eventname,""+json.getString("eventname"));
                                    if (eventname.equals(json.getString("eventname")))
                                    {
                                        holder.b1.setLiked(true);
                                    }
                                    else
                                    {

                                    }

                                }
                                else {

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class HomeViewHolder extends RecyclerView.ViewHolder {
        Button b2;
       LikeButton b1;
        ImageView cat_img;
        ProgressBar progressBar;
        String eventname;
        String eventurl;
        public HomeViewHolder(View itemView) {
            super(itemView);
            sharedPrefs=new SharedPrefs(context);
            loadToast=new LoadToast(context);

            b1=(LikeButton)itemView.findViewById(R.id.b1);
            b2=(Button)itemView.findViewById(R.id.b2);
            cat_img=(ImageView)itemView.findViewById(R.id.home_imgview);

            progressBar=(ProgressBar)itemView.findViewById(R.id.progress);
            cat_img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    context.startActivity(new Intent(context, EventActivity.class));
                    Intent i=new Intent(context,EventActivity.class);
                    i.putExtra("eventname",eventname);
                    i.putExtra("bstatus",b1.isLiked());
                    Log.d("status",""+b1.isLiked());
                    context.startActivity(i);
                }
            });
//            b1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    loadToast.show();
//                    if(b1.getText().equals("Intrested")) {
//                        Log.d("rollno", "" + sharedPrefs.getRollno());
//                        AndroidNetworking.initialize(context);
//                        AndroidNetworking.post(Urls.like)
//                                .addBodyParameter("rollno", sharedPrefs.getRollno())
//                                .addBodyParameter("eventname", eventname)
//                                .setPriority(Priority.MEDIUM)
//                                .build()
//                                .getAsJSONArray(new JSONArrayRequestListener() {
//                                    @Override
//                                    public void onResponse(JSONArray response) {
//                                        Log.d("response", "" + response);
//                                        b1.setText("Not Intrested");
//                                        loadToast.success();
//                                    }
//
//                                    @Override
//                                    public void onError(ANError anError) {
//                                        Log.d("error", "" + anError);
//                                        loadToast.error();
//                                    }
//                                });
//                    }
//                    else
//                    {
//                        Log.d("rollno", "" + sharedPrefs.getRollno());
//                        AndroidNetworking.initialize(context);
//                        AndroidNetworking.post(Urls.unlike)
//                                .addBodyParameter("rollno", sharedPrefs.getRollno())
//                                .addBodyParameter("eventname", eventname)
//                                .setPriority(Priority.MEDIUM)
//                                .build()
//                                .getAsJSONArray(new JSONArrayRequestListener() {
//                                    @Override
//                                    public void onResponse(JSONArray response) {
//                                        Log.d("response", "" + response);
//                                        b1.setText("Intrested");
//                                        loadToast.success();
//                                    }
//
//                                    @Override
//                                    public void onError(ANError anError) {
//                                        Log.d("error", "" + anError);
//                                        loadToast.error();
//                                    }
//                                });
//
//
//                    }
//                }
//            });
            b1.setOnLikeListener(new OnLikeListener() {
                @Override
                public void liked(LikeButton likeButton) {
                    AndroidNetworking.initialize(context);
                    AndroidNetworking.post(Urls.like)
                            .addBodyParameter("rollno", sharedPrefs.getRollno())
                            .addBodyParameter("eventname", eventname)
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONArray(new JSONArrayRequestListener() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    Log.d("response", "" + response);
                                    b1.setLiked(true);
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
                    AndroidNetworking.initialize(context);
                    AndroidNetworking.post(Urls.unlike)
                            .addBodyParameter("rollno", sharedPrefs.getRollno())
                            .addBodyParameter("eventname", eventname)
                            .setPriority(Priority.MEDIUM)
                            .build()
                            .getAsJSONArray(new JSONArrayRequestListener() {
                                @Override
                                public void onResponse(JSONArray response) {
                                    Log.d("response", "" + response);
                                    b1.setLiked(false);
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
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(eventurl)));
                }
            });
        }
    }
}

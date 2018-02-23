package com.eventsmanager.root.eventsmanager.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.eventsmanager.root.eventsmanager.Activities.Notification_Expand;
import com.eventsmanager.root.eventsmanager.Models.NotificationsModel;
import com.eventsmanager.root.eventsmanager.R;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

/**
 * Created by root on 31/1/18.
 */


    public class Notifications_Adapter extends RecyclerView.Adapter<Notifications_Adapter.NotificationsViewHolder> {
        List<NotificationsModel> list;
        Context context;

        public Notifications_Adapter(List<NotificationsModel> list,
                                     Context context){
            this.context=context;
            this.list=list;
        }

        @Override
        public NotificationsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notifications_card_model,parent,false);
            NotificationsViewHolder notificationsViewHolder = new NotificationsViewHolder(v);
            return notificationsViewHolder;
        }

        @Override
        public void onBindViewHolder(final NotificationsViewHolder holder, int position) {

            final NotificationsModel model = list.get(position);
            holder.heading.setText(String.valueOf(model.getHeading()));
            holder.timestamp.setText(String.valueOf(model.getTimestamp()));
            holder.tag.setText(String.valueOf(model.getTag()));

            if (model.getImageurl()==null || model.getImageurl().isEmpty() || model.getImageurl().length()==0){
                Glide.with(context).load(android.R.drawable.ic_dialog_alert).into(holder.img);
            }else {
                Glide.with(context).load(model.getImageurl()).diskCacheStrategy(DiskCacheStrategy.SOURCE).error(android.R.drawable.ic_dialog_alert).listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {

                        holder.progressBar.setVisibility(View.GONE);
                        return false;
                    }
                }).into(holder.img);
            }
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(context, Notification_Expand.class);
                    intent.putExtra("heading",String.valueOf(model.getHeading()));
                    context.startActivity(intent);
                }
            });

        }

        @Override
        public int getItemCount() {
            return list.size();
        }


        public class NotificationsViewHolder extends RecyclerView.ViewHolder {

            CardView cardView;
            TextView heading,timestamp,tag,content,contentimg;
            CircularImageView img;
            ProgressBar progressBar;
            public NotificationsViewHolder(View itemView) {
                super(itemView);
                cardView=itemView.findViewById(R.id.cardview);
                heading= itemView.findViewById(R.id.home_card_heading);
                timestamp= itemView.findViewById(R.id.home_card_timestamp);
                tag= itemView.findViewById(R.id.home_card_tag);
                img= itemView.findViewById(R.id.home_card_img);
                progressBar = itemView.findViewById(R.id.progress);



            }
        }


    }


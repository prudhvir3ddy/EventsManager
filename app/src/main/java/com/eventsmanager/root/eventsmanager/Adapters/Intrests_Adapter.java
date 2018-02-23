package com.eventsmanager.root.eventsmanager.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eventsmanager.root.eventsmanager.Models.Intrests_Model;
import com.eventsmanager.root.eventsmanager.R;
import com.eventsmanager.root.eventsmanager.utils.SharedPrefs;

import java.util.List;

/**
 * Created by root on 23/1/18.
 */

public class Intrests_Adapter extends RecyclerView.Adapter<Intrests_Adapter.IntrestViewHolder>  {
    List<Intrests_Model> list;
    Context context;
    SharedPrefs sharedPrefs;

    public Intrests_Adapter(List<Intrests_Model> list,Context context)
    {
        this.list=list;
        this.context=context;
    }
    @Override
    public IntrestViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.prize_card_model,parent,false);
        IntrestViewHolder viewHolder=new IntrestViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(IntrestViewHolder holder, int position) {
        Intrests_Model intrests_model=list.get(position);
        String eventname=intrests_model.getEventname();
        String time=intrests_model.getTime();

        holder.heading.setText(eventname);
        holder.time.setText(time);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class IntrestViewHolder extends RecyclerView.ViewHolder
    {
        TextView heading,time;

        CardView cardView;

        public IntrestViewHolder(View itemView) {
            super(itemView);
            cardView=(CardView)itemView.findViewById(R.id.cardview);
            heading=(TextView)itemView.findViewById(R.id.home_card_heading);
            time=(TextView)itemView.findViewById(R.id.home_card_timestamp);
        }
    }
}

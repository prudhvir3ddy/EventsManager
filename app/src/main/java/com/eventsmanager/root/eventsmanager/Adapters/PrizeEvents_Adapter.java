package com.eventsmanager.root.eventsmanager.Adapters;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eventsmanager.root.eventsmanager.Models.PrizeEventsModel;
import com.eventsmanager.root.eventsmanager.R;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.util.List;

import static com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype.Fall;

/**
 * Created by root on 21/1/18.
 */

public class PrizeEvents_Adapter extends RecyclerView.Adapter<PrizeEvents_Adapter.PrizeViewHolder>  {

    List<PrizeEventsModel> list;
    NiftyDialogBuilder dialogBuilder;
    Context context;

    public PrizeEvents_Adapter(List<PrizeEventsModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public PrizeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.prize_card_model,parent,false);
        PrizeViewHolder prizeViewHolder=new PrizeViewHolder(v);
        return prizeViewHolder;
    }

    @Override
    public void onBindViewHolder(PrizeViewHolder holder, int position) {
        PrizeEventsModel prizeEventsModel=list.get(position);
        final String eventname=prizeEventsModel.getEventname();
        final String eventdesc=prizeEventsModel.getEventdesc();
        dialogBuilder= NiftyDialogBuilder.getInstance(context);
        String time=prizeEventsModel.getTime();
        holder.heading.setText(eventname);
        holder.time.setText(time);
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("clicked","clicked");
                dialogBuilder
                        .withTitle(eventname)
                        .withTitleColor("#FFFFFF")
                        .withDividerColor("#11000000")
                        .withMessage(eventdesc)
                        .withMessageColor("#FFFFFFFF")
                        .withDialogColor("#455a64")
                        .withIcon(R.drawable.participation)
                        .withDuration(500)
                        .withButton1Text("well done")
                        .withEffect(Fall)
                        .isCancelableOnTouchOutside(true)
                        .setButton1Click(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogBuilder.cancel();
                            }
                        })
                        .show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class PrizeViewHolder extends RecyclerView.ViewHolder
    {
        TextView heading,time;

        CardView cardView;

        public PrizeViewHolder(View itemView) {
            super(itemView);
            cardView=(CardView)itemView.findViewById(R.id.cardview);
            heading=(TextView)itemView.findViewById(R.id.home_card_heading);
            time=(TextView)itemView.findViewById(R.id.home_card_timestamp);

        }
    }
}

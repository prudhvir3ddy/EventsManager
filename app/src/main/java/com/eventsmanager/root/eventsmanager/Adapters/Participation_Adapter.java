package com.eventsmanager.root.eventsmanager.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eventsmanager.root.eventsmanager.Models.Participate_Model;
import com.eventsmanager.root.eventsmanager.R;
import com.gitonway.lee.niftymodaldialogeffects.lib.NiftyDialogBuilder;

import java.util.List;

import static com.gitonway.lee.niftymodaldialogeffects.lib.Effectstype.Fall;

/**
 * Created by root on 20/1/18.
 */

public class Participation_Adapter extends RecyclerView.Adapter<Participation_Adapter.participationViewHolder> {

    List<Participate_Model> list;
    NiftyDialogBuilder dialogBuilder;
    Context context;
    public Participation_Adapter(List<Participate_Model> list,Context context)
    {
        this.list=list;
        this.context=context;
    }
    @Override
    public participationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.participate_card_model,parent,false);
        participationViewHolder participationViewHolder = new participationViewHolder(v);
        return participationViewHolder;
    }

    @Override
    public void onBindViewHolder(final participationViewHolder holder, int position) {
        Participate_Model participate_model=list.get(position);
        final String eventname=participate_model.getEventname();
            final String eventdesc=participate_model.getEventdesc();
        dialogBuilder= NiftyDialogBuilder.getInstance(context);
        String time=participate_model.getTime();
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

    public class participationViewHolder extends RecyclerView.ViewHolder {
        TextView heading,time;

        CardView cardView;
        public participationViewHolder(View itemView) {
            super(itemView);

            cardView=(CardView)itemView.findViewById(R.id.cardview);
            heading=(TextView)itemView.findViewById(R.id.home_card_heading);
            time=(TextView)itemView.findViewById(R.id.home_card_timestamp);

        }
    }
}

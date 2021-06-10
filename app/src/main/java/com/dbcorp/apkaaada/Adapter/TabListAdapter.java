package com.dbcorp.apkaaada.Adapter;

/**
 * Created by Bhupesh Sen on 26-01-2021.
 */

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dbcorp.apkaaada.R;
import com.google.android.material.card.MaterialCardView;

public class TabListAdapter extends RecyclerView.Adapter<TabListAdapter.MyViewHolder> {

    private String arrItems[];
    private final OnMeneuClickListnser onMenuListClicklistener;
    static int  getPosition;
    private int row_index;
    Context mContext;
    int select;

    public TabListAdapter(String menuList[], OnMeneuClickListnser onLiveTestClickListener, Context context) {
        this.arrItems = menuList;
        this.onMenuListClicklistener = onLiveTestClickListener;

        this.mContext=context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;


        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tabmenu, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String menuName=arrItems[position];

        holder.nameText.setText(menuName);
        //holder.number.setText(ticket.getTicketNumber());


        holder.openpanel.setOnClickListener(v -> {
            select=position;
            onMenuListClicklistener.onOptionClick(menuName,position);
             notifyDataSetChanged();
        });
if(select==position){
    holder.nameText.setTextColor(Color.WHITE);

    holder.openpanel.setCardBackgroundColor(Color.parseColor("#F41F1F"));
}else{
    holder.nameText.setTextColor(Color.BLACK);
    holder.openpanel.setCardBackgroundColor(Color.parseColor("#FFFFFF"));
}

    }

    @Override
    public int getItemCount() {
        return arrItems.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView number,nameText;
        MaterialCardView openpanel;
        View viewBorder;
        MyViewHolder(View view) {
            super(view);

            openpanel=view.findViewById(R.id.openpanel);
            viewBorder=view.findViewById(R.id.border);
            nameText=view.findViewById(R.id.name);



        }
    }
    public interface OnMeneuClickListnser{
        void onOptionClick(String liveTest,int pos);
    }


 }



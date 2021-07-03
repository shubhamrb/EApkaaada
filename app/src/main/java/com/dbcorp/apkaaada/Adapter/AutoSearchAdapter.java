package com.dbcorp.apkaaada.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.model.AutoSearch;
import com.dbcorp.apkaaada.model.DroupDownModel;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;
import java.util.List;

public class AutoSearchAdapter extends RecyclerView.Adapter<AutoSearchAdapter.MyViewHolder> implements Filterable {

    private List<AutoSearch> menuClassList;
    private final OnMeneuClickListnser onMenuListClicklistener;
    static int getPosition;
    private int row_index;
    Context mContext;
    private List<AutoSearch> searchArray;

    public AutoSearchAdapter(List<AutoSearch> menuClassList, OnMeneuClickListnser onLiveTestClickListener, Context context) {
        this.menuClassList = menuClassList;
        this.onMenuListClicklistener = onLiveTestClickListener;
        this.searchArray = new ArrayList<>();
        this.searchArray.addAll(menuClassList);
        this.mContext = context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;


        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.searchviewlayout, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AutoSearch ticket = menuClassList.get(position);

        holder.nameText.setText(ticket.getName().toUpperCase());
        //holder.number.setText(ticket.getTicketNumber());

        holder.nameText.setTextColor(Color.parseColor("#000000"));
        holder.openpanel.setCardBackgroundColor(Color.parseColor("#FFFFFF"));

        holder.openpanel.setOnClickListener(v -> {
            onMenuListClicklistener.onOptionClick(menuClassList.get(position));
            // notifyDataSetChanged();
        });


    }

    void add(AutoSearch ticket) {
        this.menuClassList.add(ticket);
    }

    public void addAll(List<AutoSearch> tickets) {
        this.menuClassList.addAll(tickets);
    }

    @Override
    public int getItemCount() {
        return menuClassList.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView number, nameText;
        MaterialCardView openpanel;

        MyViewHolder(View view) {
            super(view);

            openpanel = view.findViewById(R.id.openpanel);
            nameText = view.findViewById(R.id.name);


        }
    }

    public interface OnMeneuClickListnser {
        void onOptionClick(AutoSearch liveTest);
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String filterString = charSequence.toString().toLowerCase();
                //arraylist1.clear();
                FilterResults results = new FilterResults();
                int count = searchArray.size();
                final List<AutoSearch> list = new ArrayList<>();

                AutoSearch filterableString;

                for (int i = 0; i < count; i++) {
                    filterableString = searchArray.get(i);
                    if (searchArray.get(i).getName().toLowerCase().contains(filterString)) {
                        list.add(filterableString);
                    }


                }

                results.values = list;
                results.count = list.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                menuClassList = (List<AutoSearch>) filterResults.values;
                notifyDataSetChanged();
            }
        };


    }
}


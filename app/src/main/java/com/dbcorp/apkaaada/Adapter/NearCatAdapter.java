package com.dbcorp.apkaaada.Adapter;

/**
 * Created by Bhupesh Sen on 26-01-2021.
 */
 
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbcorp.apkaaada.R;

public class NearCatAdapter extends RecyclerView.Adapter<NearCatAdapter.MyViewHolder> {

    HomeItemAdapter homeItemAdapter;
    NearCatAdapter listner;
    private String arrItems[];
    private String arrItemslist[]={"a","b","c"};
    private final OnMeneuClickListnser onMenuListClicklistener;
    static int  getPosition;
    private int row_index;
    Context mContext;
    int select;

    public NearCatAdapter(String menuList[], OnMeneuClickListnser onLiveTestClickListener, Context context) {
        this.arrItems = menuList;
        this.onMenuListClicklistener = onLiveTestClickListener;

        this.mContext=context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;


        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item_shop, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String menuName=arrItems[position];

        holder.openCard.setOnClickListener(v->{
            onMenuListClicklistener.catServiceClick("nj",position);
        });

    }

    @Override
    public int getItemCount() {
        return arrItems.length;
    }



    class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayoutCompat openCard;
          MyViewHolder(View view) {
            super(view);
              openCard=view.findViewById(R.id.openCard);



         }
    }
    public interface OnMeneuClickListnser{
        void catServiceClick(String liveTest, int pos);
    }


 }



package com.dbcorp.apkaaada.Adapter;

/**
 * Created by Bhupesh Sen on 26-01-2021.
 */
 
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dbcorp.apkaaada.R;

public class HomeItemAdapter extends RecyclerView.Adapter<HomeItemAdapter.MyViewHolder> {

    private String arrItems[];
    private final OnMeneuClickListnser onMenuListClicklistener;
    static int  getPosition;
    private int row_index;
    Context mContext;
    int select;

    int getPos;
    public HomeItemAdapter(int pos,String menuList[], OnMeneuClickListnser onLiveTestClickListener, Context context) {
        this.arrItems = menuList;
        this.onMenuListClicklistener = onLiveTestClickListener;
        this.getPos=pos;
        this.mContext=context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;


        if(getPos==0) itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offer_shop, parent, false);
       else  if(getPos==1) itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item_shop, parent, false);
        else  if(getPos==2) itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item_shop, parent, false);
        else itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_shop, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String menuName=arrItems[position];



    }

    @Override
    public int getItemCount() {
        return arrItems.length;
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
         MyViewHolder(View view) {
            super(view);



        }
    }
    public interface OnMeneuClickListnser{
        void onOptionClick(String liveTest, int pos);
    }


 }



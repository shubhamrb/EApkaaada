package com.dbcorp.apkaaada.Adapter;

/**
 * Created by Bhupesh Sen on 26-01-2021.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.model.MyComplain;
import com.dbcorp.apkaaada.model.shopview.Product;
import com.dbcorp.apkaaada.network.ApiService;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class MyComplainAdapter extends RecyclerView.Adapter<MyComplainAdapter.MyViewHolder> implements Filterable {

    private  ArrayList<MyComplain> listData;

    private ArrayList<MyComplain> searchArray;

    private final OnMeneuClickListnser onMenuListClicklistener;
    Context mContext;
    private static String productList;
    private static ArrayList<Integer> productQuantity;

    public MyComplainAdapter(ArrayList<MyComplain> list, OnMeneuClickListnser onLiveTestClickListener, Context context) {
        this.listData = list;
        this.searchArray = new ArrayList<>();
        this.searchArray.addAll(list);
        this.productQuantity=new ArrayList<Integer>(list.size());
        this.onMenuListClicklistener = onLiveTestClickListener;
        this.mContext = context;
    }
    int quantity=0;



    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_complain, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        MyComplain data = listData.get(position);
        holder.tvName.setText(data.getName());

        holder.tvOrderNo.setText(data.getOrderNumber());
        holder.tvVenName.setText(data.getVendorName());
        holder.tvComplain.setText(data.getComplaint());




        Glide.with(mContext)
                .load(ApiService.COMPLAINT_IMG_URL+data.getComplaintPhoto())
                .into(holder.img);





    }

    @Override
    public int getItemCount() {
        return listData.size();
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
                final ArrayList<MyComplain> list = new ArrayList<>();

                MyComplain filterableString;

                for (int i = 0; i < count; i++) {
                    filterableString = searchArray.get(i);
                    if (searchArray.get(i).getName().toLowerCase().contains(filterString) ) {
                        list.add(filterableString);
                    }


                }

                results.values = list;
                results.count = list.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listData = (ArrayList<MyComplain>) filterResults.values;
                notifyDataSetChanged();
            }
        };


    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        MaterialTextView tvOrderNo,tvVenName,tvName,tvComplain;
        ShapeableImageView img;

        MyViewHolder(View view) {
            super(view);

            img=view.findViewById(R.id.img);
            tvName=view.findViewById(R.id.tvName);
            tvOrderNo=view.findViewById(R.id.tvOrderNo);
            tvVenName=view.findViewById(R.id.tvVenName);
            tvComplain=view.findViewById(R.id.tvComplain);

        }
    }

    public interface OnMeneuClickListnser {
     }


}



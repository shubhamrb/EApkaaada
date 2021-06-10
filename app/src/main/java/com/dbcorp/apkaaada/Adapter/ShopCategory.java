package com.dbcorp.apkaaada.Adapter;

/**
 * Created by Bhupesh Sen on 26-01-2021.
 */
 
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.model.home.Category;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

import static com.dbcorp.apkaaada.network.ApiService.BASE_IMG_CAT_URL;


public class ShopCategory extends RecyclerView.Adapter<ShopCategory.MyViewHolder> {

    HomeItemAdapter homeItemAdapter;
    ShopCategory listner;
    private String arrItems[];
    private String arrItemslist[]={"a","b","c"};
    private final OnMeneuClickListnser onMenuListClicklistener;
    static int  getPosition;
    private int row_index;
    Context mContext;
    int select;

    ArrayList<Category> listCategory;

    String pageView;
    public ShopCategory(String pageView,ArrayList<Category> getlistCategory, OnMeneuClickListnser onLiveTestClickListener, Context context) {
        this.listCategory = getlistCategory;
        this.pageView=pageView;
        this.onMenuListClicklistener = onLiveTestClickListener;
        this.mContext=context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;

        if(pageView.equalsIgnoreCase("home")){
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item_shop_two, parent, false);

        }else{
            itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cat_item_shop, parent, false);

        }


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Category menuName=listCategory.get(position);
        Glide.with(mContext)
                .load(BASE_IMG_CAT_URL+menuName.getPhoto())
                .into(holder.shapeableImageView);
        holder.tvName.setText(menuName.getName());

        holder.showProduct.setOnClickListener(v->{
            onMenuListClicklistener.catServiceClick(menuName,position,"");
        });

    }

    @Override
    public int getItemCount() {
        return listCategory.size();
    }



    class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayoutCompat showProduct;
        ShapeableImageView shapeableImageView;
        MaterialTextView tvName;
          MyViewHolder(View view) {
            super(view);
              showProduct=view.findViewById(R.id.openCard);
              tvName=view.findViewById(R.id.tvName);
              shapeableImageView=view.findViewById(R.id.imageView);
         }
    }
    public interface OnMeneuClickListnser{
        void catServiceClick(Category liveTest, int pos,String type);
    }


 }



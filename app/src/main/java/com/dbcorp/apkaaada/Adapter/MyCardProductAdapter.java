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
import com.dbcorp.apkaaada.model.shopview.Product;
import com.dbcorp.apkaaada.network.ApiService;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class MyCardProductAdapter extends RecyclerView.Adapter<MyCardProductAdapter.MyViewHolder> implements Filterable {

    private  ArrayList<Product> listData;

    private ArrayList<Product> searchArray;

    private final OnMeneuClickListnser onMenuListClicklistener;
    Context mContext;
    private static String productList;
    private static ArrayList<Integer> productQuantity;

    public MyCardProductAdapter(ArrayList<Product> list, OnMeneuClickListnser onLiveTestClickListener, Context context) {
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
        itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Product data = listData.get(position);
        holder.tvName.setText(data.getName());
        int cartQuantity= Integer.parseInt(data.getCartQuantity());
        int productPrice= Integer.parseInt(data.getPrice());
        int totalPrice=cartQuantity*productPrice;
holder.TvRemove.setVisibility(View.VISIBLE);
holder.TvRemove.setOnClickListener(v->{
    onMenuListClicklistener.productRemove(data,position);
});
        holder.tvName.setText(data.getName());
        holder.tvVariantValue.setText(data.getValueName().equalsIgnoreCase("N/A") ? "": data.getValueName());


        holder.tvPrice.setText(data.getCartQuantity()+"X"+data.getPrice()+" : "+String.format(" ₹ %d", totalPrice));
        holder.tvDescription.setText(data.getDescription());

        holder.catName.setText(data.getCategoryName());
        holder.subCat.setText(data.getSubCategoryName());
        holder.subtosubCat.setText(data.getSubSubCategoryName());


        Glide.with(mContext)
                .load(ApiService.PRODUCT_IMG_URL+data.getPhoto())
                .into(holder.img);
        //  holder.tv_quantity.setText("787");

//        if(data.getVariantId().equalsIgnoreCase("no")){
//
//
//
//            holder.addBtn.setVisibility(View.VISIBLE);
//            holder.tvLayout.setVisibility(View.VISIBLE);
//
//        }else{
//            holder.addBtn.setVisibility(View.GONE);
//            holder.tvLayout.setVisibility(View.GONE);
//
//
//            //holder.addBtn.setVisibility(View.VISIBLE);
//           // holder.tvLayout.setVisibility(View.VISIBLE);
//
//        }




        holder.button_add.setOnClickListener(v->{
            quantity= Integer.parseInt(listData.get(position).getCartQuantity());
            quantity++;
            if(quantity>=0){
                listData.get(position).setCartQuantity(String.valueOf(quantity));
                notifyDataSetChanged();
                onMenuListClicklistener.addCartClick(data,position,"1",listData,holder.showProduct);
            }
        });
        holder.button_subtract.setOnClickListener(v->{
            if(listData.get(position).getCartQuantity().equalsIgnoreCase("1")){

            }else{
                quantity= Integer.parseInt(listData.get(position).getCartQuantity());
                quantity--;
                if(quantity>=0){
                    listData.get(position).setCartQuantity(String.valueOf(quantity));
                    notifyDataSetChanged();
                    onMenuListClicklistener.addCartClick(data,position,"0",listData,holder.showProduct);
                }
            }


        });
//        if(listData.get(position).getVariantStatus().equalsIgnoreCase("Add Card")){
//            holder.addBtn.setVisibility(View.VISIBLE);
//            holder.addCart.setVisibility(View.GONE);
//            holder.tvLayout.setVisibility(View.VISIBLE);
//            holder.showProduct.setOnClickListener(view -> {
//
//            });
//        }else{
//            holder.addCart.setVisibility(View.VISIBLE);
//            holder.addCart.setText("View Varriant");
//            holder.addBtn.setVisibility(View.GONE);
//
//            holder.tvLayout.setVisibility(View.VISIBLE);
//            holder.showProduct.setOnClickListener(view -> {
//                onMenuListClicklistener.productClick(data,position);
//            });
//        }


        if(cartQuantity>0 && listData.get(position).getVariantStatus().equalsIgnoreCase("Add Card")){
            holder.addBtn.setVisibility(View.VISIBLE);
            holder.addCartProduct.setVisibility(View.GONE);
            holder.addCart.setVisibility(View.GONE);

            holder.showProduct.setOnClickListener(view -> {
                //onMenuListClicklistener.productClick(data,position);
            });
        }else if(cartQuantity==0 && listData.get(position).getVariantStatus().equalsIgnoreCase("Add Card")){
            holder.addBtn.setVisibility(View.GONE);
            holder.addCart.setVisibility(View.GONE);
            onMenuListClicklistener.productRemove(data,position);
            holder.addCartProduct.setVisibility(View.VISIBLE);

            holder.addCartProduct.setOnClickListener(view -> {
                quantity= Integer.parseInt(listData.get(position).getCartQuantity());
                quantity++;
                if(quantity>=0){
                    listData.get(position).setCartQuantity(String.valueOf(quantity));
                    notifyDataSetChanged();
                    onMenuListClicklistener.addCartClick(data,position,"-",listData,holder.showProduct);
                }
            });

            holder.showProduct.setOnClickListener(view -> {
                // onMenuListClicklistener.productClick(data,position);
            });
        }else {
            holder.showProduct.setOnClickListener(view -> {
                // onMenuListClicklistener.productClick(data,position);
            });
        }

     //   else if(listData.get(position).getVariantStatus().equalsIgnoreCase("View More")){
//            holder.addCart.setVisibility(View.VISIBLE);
//            holder.addCart.setText("View Varriant");
//            holder.addBtn.setVisibility(View.GONE);
//            holder.addCartProduct.setVisibility(View.GONE);
//
//            holder.showProduct.setOnClickListener(view -> {
//                onMenuListClicklistener.productClick(data,position);
//            });
//        }

        holder.tv_quantity.setText(data.getCartQuantity());
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
                final ArrayList<Product> list = new ArrayList<>();

                Product filterableString;

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
                listData = (ArrayList<Product>) filterResults.values;
                notifyDataSetChanged();
            }
        };


    }

    class MyViewHolder extends RecyclerView.ViewHolder {

        MaterialCardView showProduct;
        LinearLayoutCompat tvLayout,addBtn;

        MaterialTextView tvVariantValue,addCartProduct,catName,subCat,subtosubCat,tvName,tv_quantity,tvPrice,tvDescription,addCart;
        ShapeableImageView img;
        AppCompatImageView button_add,button_subtract,TvRemove;
        MyViewHolder(View view) {
            super(view);
            showProduct = view.findViewById(R.id.showProduct);
            img=view.findViewById(R.id.img);
            tvName=view.findViewById(R.id.tvName);
            catName=view.findViewById(R.id.catName);
            subCat=view.findViewById(R.id.subCat);
            subtosubCat=view.findViewById(R.id.subtosubCat);
            button_add=view.findViewById(R.id.button_add);
            button_subtract=view.findViewById(R.id.button_subtract);
            tv_quantity=view.findViewById(R.id.tv_quantity);
            addBtn=view.findViewById(R.id.addBtn);
            tvLayout=view.findViewById(R.id.tvLayout);
            addCart=view.findViewById(R.id.addCart);
            tvPrice=view.findViewById(R.id.tvPrice);
            tvDescription=view.findViewById(R.id.tvDescription); showProduct = view.findViewById(R.id.showProduct);
            img=view.findViewById(R.id.img);
            tvName=view.findViewById(R.id.tvName);
            catName=view.findViewById(R.id.catName);
            TvRemove=view.findViewById(R.id.tvRemove);
            addCartProduct=view.findViewById(R.id.addCartProduct);
            subCat=view.findViewById(R.id.subCat);
            tvVariantValue=view.findViewById(R.id.tvVariantValue);
            subtosubCat=view.findViewById(R.id.subtosubCat);
            button_add=view.findViewById(R.id.button_add);
            button_subtract=view.findViewById(R.id.button_subtract);
            tv_quantity=view.findViewById(R.id.tv_quantity);
            addBtn=view.findViewById(R.id.addBtn);
            tvLayout=view.findViewById(R.id.tvLayout);
            addCart=view.findViewById(R.id.addCart);
            tvPrice=view.findViewById(R.id.tvPrice);
            tvDescription=view.findViewById(R.id.tvDescription);
        }
    }

    public interface OnMeneuClickListnser {
        void productRemove(Product liveTest, int pos);

        void productClick(Product liveTest, int pos);
        void addCartClick(Product liveTest, int pos,String subAdd,ArrayList<Product> arrayList,View showProduct);
    }


}



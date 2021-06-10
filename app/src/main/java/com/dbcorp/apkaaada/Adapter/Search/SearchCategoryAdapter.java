package com.dbcorp.apkaaada.Adapter.Search;

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

import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.model.SearchByProduct;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;

public class SearchCategoryAdapter extends RecyclerView.Adapter<SearchCategoryAdapter.MyViewHolder> implements Filterable {

    private ArrayList<SearchByProduct> listData;
    private final OnMeneuClickListnser onMenuListClicklistener;
    static int  getPosition;
    private int row_index;
    Context mContext;
    private ArrayList<SearchByProduct> searchArray;
    public SearchCategoryAdapter(ArrayList<SearchByProduct> menuClassList, OnMeneuClickListnser onLiveTestClickListener, Context context) {
        this.listData = menuClassList;
        this.onMenuListClicklistener = onLiveTestClickListener;
        this.searchArray=new ArrayList<>();
        this.searchArray.addAll(menuClassList);
        this.mContext=context;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = null;


    itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);


        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        SearchByProduct ticket=listData.get(position);

        holder.tvName.setText(ticket.getName());
        holder.tvPrice.setText("Price: \u20B9"+ticket.getPrice());
        holder.addBtn.setVisibility(View.VISIBLE);
        holder.addCart.setVisibility(View.GONE);
        holder.tvLayout.setVisibility(View.VISIBLE);

holder.openpanel.setOnClickListener(v -> {
    onMenuListClicklistener.onOptionClick(listData.get(position));
   // notifyDataSetChanged();
});


      }
//    void add(SearchProduct ticket) {
//        this.menuClassList.add(ticket);
//    }
//   public void addAll(ArrayList<SearchProduct> tickets) {
//        this.listData.addAll(tickets);
//    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        MaterialCardView openpanel;
        LinearLayoutCompat tvLayout,addBtn;

        MaterialTextView catName,subCat,subtosubCat,tvName,tv_quantity,tvPrice,tvDescription,addCart;
        ShapeableImageView img;
        AppCompatImageView button_add,button_subtract;
        MyViewHolder(View view) {
            super(view);

            openpanel = view.findViewById(R.id.showProduct);
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
            tvDescription=view.findViewById(R.id.tvDescription);



        }
    }
    public interface OnMeneuClickListnser{
        void onOptionClick(SearchByProduct liveTest);
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
                final ArrayList<SearchByProduct> list = new ArrayList<>();

                SearchByProduct filterableString;

                for (int i = 0; i < count; i++) {
                    filterableString = searchArray.get(i);
                    if (searchArray.get(i).getName().toLowerCase().contains(filterString)
                            ||searchArray.get(i).getValueName().toLowerCase().contains(filterString)
                            || searchArray.get(i).getVendorName().toLowerCase().contains(filterString)) {
                        list.add(filterableString);
                    }


                }

                results.values = list;
                results.count = list.size();

                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                listData = (ArrayList<SearchByProduct>) filterResults.values;
                notifyDataSetChanged();
            }
        };


    }
}


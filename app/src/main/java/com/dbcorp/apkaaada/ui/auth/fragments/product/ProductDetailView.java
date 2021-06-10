package com.dbcorp.apkaaada.ui.auth.fragments.product;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.dbcorp.apkaaada.Adapter.ShopProduct;
import com.dbcorp.apkaaada.Adapter.SliderAdapter;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.model.Carousel;
import com.dbcorp.apkaaada.model.OTP;
import com.dbcorp.apkaaada.model.shopview.Product;
import com.dbcorp.apkaaada.network.InternetConnection;
import com.dbcorp.apkaaada.network.RestClient;
import com.dbcorp.apkaaada.ui.auth.Home.HomeActivity;
import com.dbcorp.apkaaada.ui.auth.fragments.ShopDetails;
import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailView extends Fragment implements ShopProduct.OnMeneuClickListnser{

    Context mContext;
    ProductDetailView listner;

    public static ProductDetailView getInstance(String data) {
        ProductDetailView myExamFragment = new ProductDetailView();
        Bundle bundle = new Bundle();
        bundle.putSerializable("data", data);
        myExamFragment.setArguments(bundle);
        return myExamFragment;
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        this.mContext = context;
    }


    String arrItems[] = new String[]{"One", "One", "One", "One"};
    String imageUrl[] = new String[]{"One", "One", "One", "One"};
    ShopProduct shopProduct;
    View view;
    RecyclerView listItem;
    private SliderAdapter sliderAdapter;
    private List<Carousel> carouselList;
    private ViewPager viewPager;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.product_variant_view, container, false);
        this.listner=this;
        init();
        return view;
    }

    private void init() {



        viewPager = view.findViewById(R.id.viewPager);
        TabLayout indicator = view.findViewById(R.id.indicator);

        carouselList = new ArrayList<>();
        sliderAdapter = new SliderAdapter(mContext, carouselList);

        viewPager.setAdapter(sliderAdapter);
        indicator.setupWithViewPager(viewPager, true);

        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new SliderTimer(), 4000, 6000);

        for(int i=0;i<=5;i++){
            Carousel item = new Carousel();
            item.setId(String.valueOf(i));
            item.setTitle("bhs");
            item.setImage("http://top10india.in/upload/product/265fc96f4351ec5b63a205bdab00685f.jpg");

            carouselList.add(item);
        }
 sliderAdapter.notifyDataSetChanged();
        listItem = view.findViewById(R.id.list);
        listItem.setHasFixedSize(true);
        //listItem.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        listItem.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
//        shopProduct = new ShopProduct(arrItems, listner, mContext);
  //      listItem.setAdapter(shopProduct);

    }



    public void getHomeData() {
        if (InternetConnection.checkConnection(mContext)) {

            Map<String, String> params = new HashMap<>();
            params.put("number", "7974430255");

            RestClient.post().getOtp(params).enqueue(new Callback<OTP>() {
                @Override
                public void onResponse(@NotNull Call<OTP> call, Response<OTP> response) {

                    OTP obj = response.body();
                    assert obj != null;
                    Log.e("data", obj.getMessage());
                    //otpWindow(obj.getTitle());

                }

                @Override
                public void onFailure(@NotNull Call<OTP> call, @NotNull Throwable t) {

                    try {
                        t.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

        }
    }


    @Override
    public void productClick(Product liveTest, int pos) {

    }

    @Override
    public void addCartClick(Product liveTest, int pos,String addSub,ArrayList<Product> listProduct,View carv) {

    }

    private class SliderTimer extends TimerTask {
        @Override
        public void run() {
            ((ShopDetails)mContext).runOnUiThread(() -> {
                if (viewPager.getCurrentItem() < carouselList.size() - 1) {
                    viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                } else {
                    viewPager.setCurrentItem(0);
                }
            });
        }
    }

}

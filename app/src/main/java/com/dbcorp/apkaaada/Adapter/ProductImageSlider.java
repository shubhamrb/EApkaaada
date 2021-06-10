package com.dbcorp.apkaaada.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.model.Carouseloffer;

import java.util.List;

import static com.dbcorp.apkaaada.network.ApiService.PRODUCT_IMG_URL;


public class ProductImageSlider extends PagerAdapter {
    private Context context;
    private List<String> carouselList;

    public ProductImageSlider(Context context, List<String> carouselList) {
        this.context = context;
        this.carouselList = carouselList;
    }

    @Override
    public int getCount() {
        return carouselList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
       // LayoutInflater inflater = (context).getLayoutInflater();
        View view = LayoutInflater.from(context).inflate(R.layout.item_slider, null) ;
        ImageView imageView = view.findViewById(R.id.imageView);

        Glide.with(context.getApplicationContext())
                .load( PRODUCT_IMG_URL+carouselList.get(position))
                .into(imageView);
        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);

        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);
    }
}
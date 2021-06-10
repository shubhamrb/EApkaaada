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
import com.dbcorp.apkaaada.MainActivity;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.model.Carousel;
import com.dbcorp.apkaaada.network.ApiService;
import com.dbcorp.apkaaada.ui.auth.Home.HomeActivity;
import com.dbcorp.apkaaada.ui.auth.fragments.ShopDetails;

import java.util.List;



public class SliderAdapter extends PagerAdapter {
    private Context context;
    private List<Carousel> carouselList;

    public SliderAdapter(Context context, List<Carousel> carouselList) {
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
        LayoutInflater inflater = ((ShopDetails)context).getLayoutInflater();
        View view = inflater.inflate(R.layout.item_slider, container, false);

        ImageView imageView = view.findViewById(R.id.imageView);

        Carousel item = carouselList.get(position);

        Glide.with(context.getApplicationContext())
                .load("http://top10india.in/upload/offer/a93cbacb6f5b6f1a7c5feb7a67bf877e.jpg")
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
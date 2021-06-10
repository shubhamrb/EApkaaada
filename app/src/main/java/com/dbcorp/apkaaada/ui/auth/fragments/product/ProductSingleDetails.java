package com.dbcorp.apkaaada.ui.auth.fragments.product;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;

import com.dbcorp.apkaaada.R;
import com.google.android.material.textview.MaterialTextView;

public class ProductSingleDetails extends AppCompatActivity {
    LinearLayoutCompat layoutCard, addBtn,addLayout,layoutProceed;

    MaterialTextView addCartProduct, btnProceed, tvProductName, addToCard, tv_totalPrice, tvItemCount, tvDescription, tvPrice, tv_quantity, tvLeftProduct;
    AppCompatImageView img, button_add, button_subtract;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_single_details);
    }
}
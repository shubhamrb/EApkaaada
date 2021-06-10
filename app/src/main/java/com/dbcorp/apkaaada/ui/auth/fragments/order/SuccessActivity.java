package com.dbcorp.apkaaada.ui.auth.fragments.order;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.ui.auth.Home.HomeActivity;
import com.google.android.material.textview.MaterialTextView;

public class SuccessActivity extends AppCompatActivity {

    Intent g;
    String orderNo,orderAmount;
    MaterialTextView tvOrderPrice,tvOrderNo;

    MaterialTextView proceedToPay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.success_layout);
        tvOrderNo=findViewById(R.id.tvOrderNo);
        proceedToPay=findViewById(R.id.proceedToPay);
        tvOrderPrice=findViewById(R.id.tvOrderPrice);
        g=getIntent();

        tvOrderNo.setText(g.getStringExtra("order_number"));
        tvOrderPrice.setText("Rs ."+g.getStringExtra("amount"));
        proceedToPay.setOnClickListener(v->{
            Intent mv=new Intent(SuccessActivity.this, HomeActivity.class);
            startActivity(mv);
            finish();
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent mv=new Intent(SuccessActivity.this, HomeActivity.class);
        startActivity(mv);
        finish();
    }
}
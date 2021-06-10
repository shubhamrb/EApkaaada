package com.dbcorp.apkaaada.ui.auth.searchbyslider;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.model.UserDetails;
import com.dbcorp.apkaaada.model.home.Category;

public class SliderByCategory extends AppCompatActivity {

    Intent g;
    String categoryName="";
    UserDetails userDetails;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_by_cat);
        userDetails=new SqliteDatabase(this).getLogin();
        g=getIntent();
        categoryName=g.getStringExtra("query");

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Category Search");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
    public void getData(){

    }
}
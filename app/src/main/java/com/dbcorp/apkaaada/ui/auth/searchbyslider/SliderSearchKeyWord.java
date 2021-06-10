package com.dbcorp.apkaaada.ui.auth.searchbyslider;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.model.UserDetails;

public class SliderSearchKeyWord extends AppCompatActivity {
    Intent g;
    String categoryName="";
    UserDetails userDetails;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider_search_key_word);
        userDetails=new SqliteDatabase(this).getLogin();
        g=getIntent();
        categoryName=g.getStringExtra("query");
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Search By Key");
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
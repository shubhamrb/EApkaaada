package com.dbcorp.apkaaada;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.UserDetails;
import com.dbcorp.apkaaada.ui.auth.Home.HomeActivity;
import com.dbcorp.apkaaada.ui.auth.Login;


public class SplashScreen extends AppCompatActivity {

    UserDetails login;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalce_screen);
        mContext = this;
        login = new SqliteDatabase(this).getLogin();




        new MyThread().start();

    }


    class MyThread extends Thread {


        public void run() {
            try {
                Thread.sleep(6000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //Log.e("status=====>>>>>>", String.valueOf(session.isLogin()));

            try {
                if (login == null) {
                    Intent intent = new Intent(mContext, Login.class);
                    startActivity(intent);
                    finish();
                } else {
                    Intent intent = new Intent(mContext, HomeActivity.class);
                    startActivity(intent);
                    finish();
                }
            } catch (Exception e) {
                Util.show(mContext,"Error : "+e.getMessage());
            }
        }
    }
}

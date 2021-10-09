package com.dbcorp.apkaaada.helper;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dbcorp.apkaaada.Adapter.DroupdownMenuAdapter;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.model.DroupDownModel;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textview.MaterialTextView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import de.hdodenhof.circleimageview.BuildConfig;

public class Util {
    private static Dialog dialog;
    private  static AlertDialog alertDialog;
    public static void show(Context context, String message){
        Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
    }

    public static void showDialog(String dialogMessage, Context context) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.overlay_process);
//        MaterialTextView textView = dialog.findViewById(R.id.textView);
//        AppCompatImageView close=dialog.findViewById(R.id.close);
//
//        textView.setText(dialogMessage);
//        close.setOnClickListener(v->{
//            dialog.cancel();
//            dialog.dismiss();
//        });
        dialog.setCancelable(false);
        dialog.show();
    }

    public static void showDialogAlert(String title,String message, Context context) {
        dialog = new Dialog(context);
        dialog.setContentView(R.layout.alert_dialog);
        MaterialTextView TvTittle = dialog.findViewById(R.id.TvTittle);
        MaterialTextView TvMessage = dialog.findViewById(R.id.TvMessage);
        AppCompatImageView close=dialog.findViewById(R.id.close);
        AppCompatButton btnOk=dialog.findViewById(R.id.BtnOk);
        btnOk.setOnClickListener(v->{
            dialog.cancel();
            dialog.dismiss();
        });
        TvTittle.setText(title);
        TvMessage.setText(message);

        dialog.setCancelable(false);
        dialog.show();
    }
    public static void openSettings(Context context) {
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", "com.dbcorp.apkaaada", null));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
    public static double distance(double clat1, double clon1, double lat2, double lon2, String unit) {
        double theta = clon1 - lon2;
        double dist = Math.sin(deg2rad(clat1)) * Math.sin(deg2rad(lat2)) + Math.cos(deg2rad(clat1)) * Math.cos(deg2rad(lat2)) * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
//        if (unit == "K") {
            //dist = dist * 1.609344;
//        } else if (unit == "N") {
//            dist = dist * 0.8684;
//        }


        return (dist);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts decimal degrees to radians             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    public static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    /*::  This function converts radians to decimal degrees             :*/
    /*:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::*/
    public static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }
    public static String convertTime(String time) {
        String inputPattern = "HH:mm";
        String outputPattern = "hh:mm a";
        SimpleDateFormat inputFormat = new SimpleDateFormat(inputPattern, Locale.ENGLISH);
        SimpleDateFormat outputFormat = new SimpleDateFormat(outputPattern, Locale.ENGLISH);

        String str = null;

        try {
            Date date = inputFormat.parse(time);
            assert date != null;
            str = outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return str;
    }
    public static void UpdateApk(Context context) {
//        AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
//        builder2.setCancelable(false);
//        View view2 = LayoutInflater.from(context).inflate(R.layout.updatealertlayout, null);
//        MaterialButton updateBtn = view2.findViewById(R.id.updateBtn);
//        updateBtn.setOnClickListener(v -> {
//            String url = "https://play.google.com/store/apps/details?id=com.dbcorp.noi";
//             Intent i = new Intent(Intent.ACTION_VIEW);
//            i.setData(Uri.parse(url));
//             context.startActivity(i);
//        });
//        builder2.setView(view2);
//
//        alertDialog = builder2.create();
//        alertDialog.show();
    }
    public static void showDropDown(ArrayList<DroupDownModel> droupDownModelslist, String tittle, Context context , DroupdownMenuAdapter.OnMeneuClickListnser dropContext) {

        AlertDialog.Builder builder2 = new AlertDialog.Builder(context);
        builder2.setCancelable(false);
        View view2 = LayoutInflater.from(context).inflate(R.layout.droup_down_layout, null);
        AppCompatImageView closeImg=view2.findViewById(R.id.tvClose);
        TextView tittleName=view2.findViewById(R.id.tittleName);
        EditText inputSearch=view2.findViewById(R.id.search);
        tittleName.setVisibility(View.VISIBLE);
        tittleName.setText(tittle);
        RecyclerView listCountryData2 = view2.findViewById(R.id.listView);

        listCountryData2.setHasFixedSize(true);
        //syllabus_type_recyclerview.setLayoutManager(new GridLayoutManager(HomeActivity.this, 3));
        listCountryData2.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));

        DroupdownMenuAdapter optionList1 = new DroupdownMenuAdapter(droupDownModelslist, dropContext, context);
        listCountryData2.setAdapter(optionList1);

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                String text = inputSearch.getText().toString().toLowerCase(Locale.getDefault());
                Log.e("bhs==>>", text);


                optionList1.getFilter().filter(cs);
                optionList1.notifyDataSetChanged();

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });
        builder2.setView(view2);
        closeImg.setOnClickListener(v->{
            alertDialog.cancel();
            alertDialog.dismiss();
        });

        alertDialog = builder2.create();
        alertDialog.show();
    }



    public static void hideDropDown() {


        alertDialog.cancel();
        alertDialog.dismiss();
    }

    public static String md5(final String s) {
        final String MD5 = "MD5";
        try {
            // Create MD5 Hash
            MessageDigest digest = MessageDigest.getInstance(MD5);
            digest.update(s.getBytes());
            byte[] messageDigest = digest.digest();

            // Create Hex String
            StringBuilder hexString = new StringBuilder();
            for (byte aMessageDigest : messageDigest) {
                String h = Integer.toHexString(0xFF & aMessageDigest);
                while (h.length() < 2)
                    h = String.format("0%s", h);
                hexString.append(h);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
    public static void hideDialog() {
        dialog.dismiss();
        dialog.cancel();
    }



}

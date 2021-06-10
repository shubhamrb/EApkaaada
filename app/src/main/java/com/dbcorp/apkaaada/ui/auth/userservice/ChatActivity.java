package com.dbcorp.apkaaada.ui.auth.userservice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.dbcorp.apkaaada.Adapter.ChatCustomerAdapter;
import com.dbcorp.apkaaada.Adapter.chat.ChatingRequestAdapter;
import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.UserDetails;
import com.dbcorp.apkaaada.model.UserMessage;
import com.dbcorp.apkaaada.model.chat.BookingServiceChat;
import com.dbcorp.apkaaada.model.chat.ChatBooking;
import com.dbcorp.apkaaada.network.ApiService;
import com.dbcorp.apkaaada.network.InternetConnection;
import com.dbcorp.apkaaada.network.RestClient;
import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChatActivity extends AppCompatActivity implements ChatCustomerAdapter.OnMeneuClickListnser {
    RecyclerView list;
    View view;
    Context mContext;
    ChatCustomerAdapter chatCustomerAdapter;
    UserDetails loginDetails;
    ArrayList<ChatBooking> chatBookings;
    ArrayList<ChatBooking> chatBookingsNewList;

    ChatActivity listener;
    BookingServiceChat bookingChat;
    String lastChatId="";
    String firstEnter="yes";
    TextInputEditText tvMsg;
    AppCompatImageView ImgSend,refresh;
    Intent g;
    String type="red";
    LinearLayoutCompat chatLayout,refreshLayout;
CircleImageView vendImg;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        loginDetails=new SqliteDatabase(this).getLogin();
        listener=this;
        mContext=this;
        bookingChat= (BookingServiceChat) getIntent().getSerializableExtra("myData");

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(bookingChat.getName());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        chatBookings=new ArrayList<>();
        chatLayout=findViewById(R.id.chatLayout);
        refreshLayout=findViewById(R.id.refreshLayout);
        g=getIntent();
       if(bookingChat.getStatus().equalsIgnoreCase("2") || bookingChat.getStatus().equalsIgnoreCase("0")){
           chatLayout.setVisibility(View.VISIBLE);
           refreshLayout.setVisibility(View.GONE);
       }else if(bookingChat.getStatus().equalsIgnoreCase("1")){
           chatLayout.setVisibility(View.GONE);
           refreshLayout.setVisibility(View.VISIBLE);
       }
       init();
    }

    @Override
    protected void onResume() {
        init();
        getChatRequest();
        super.onResume();
    }

    private static String getTimeStamp() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date());
    }
    private void sendMessage() {
        if (InternetConnection.checkConnection(mContext)) {

            ChatBooking m = new ChatBooking(loginDetails.getUserId(), Objects.requireNonNull(tvMsg.getText()).toString(), getTimeStamp(), bookingChat.getService_chat_booking_id());
            chatBookings.add(m);
            chatCustomerAdapter.notifyDataSetChanged();
            scrollToBottom();
            Map<String, String> params = new HashMap<>();
            params.put("chat_by", loginDetails.getUserId());
            params.put("chat_to", bookingChat.getVendor_id());
            params.put("service_chat_booking_id", bookingChat.getService_chat_booking_id());
            params.put("message", tvMsg.getText().toString());
            // Calling JSON
            Log.e("bhs",params.toString());
            Call<String> call = RestClient.post().addMessage("1234", loginDetails.getSk(), params);

            // Enqueue Callback will be call when get response...
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NonNull Call<String> call, @NonNull Response<String> response) {
                    if (response.isSuccessful()) {
                        assert response.body() != null;
                        try {

                            Gson gson = new Gson();
                            tvMsg.getText().clear();
                            JSONObject object = new JSONObject(response.body());
                            if(object.getBoolean("status")){
                                chatLayout.setVisibility(View.GONE);
                                refreshLayout.setVisibility(View.VISIBLE);
                            }else{
                                Util.show(mContext,"");
                            }

//

                        } catch (Exception e) {

                            e.printStackTrace();
                        }

                    } else {
                        try {
                            assert response.errorBody() != null;
                            Toast.makeText(mContext, "error message" + response.errorBody().string(), Toast.LENGTH_SHORT).show();

                        } catch (IOException e) {
                            Toast.makeText(mContext, "error message" + response.errorBody().toString(), Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }

                    }
                }

                @Override
                public void onFailure(@NonNull Call<String> call, @NonNull Throwable t) {
                    Toast.makeText(mContext, R.string.string_some_thing_wrong, Toast.LENGTH_SHORT).show();
                }

            });
        } else {

            Toast.makeText(mContext, R.string.string_internet_connection_not_available, Toast.LENGTH_SHORT).show();
        }
    }


    private void scrollToBottom() {
        chatCustomerAdapter.notifyDataSetChanged();
        if (chatCustomerAdapter.getItemCount() > 1)
            list.getLayoutManager().smoothScrollToPosition(list, null, chatCustomerAdapter.getItemCount() - 1);
    }


    public void init() {
        chatBookings=new ArrayList<>();
        tvMsg=findViewById(R.id.tvMsg);
        list = findViewById(R.id.list);
        ImgSend=findViewById(R.id.ImgSend);
        refresh=findViewById(R.id.refresh);
        vendImg=findViewById(R.id.vendImg);

        Glide.with(mContext)
                .load(ApiService.PRODUCT_IMG_URL+bookingChat.getPhoto())
                .into(vendImg);
        list.setHasFixedSize(true);
        list.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));

        ImgSend.setOnClickListener(v->{
            sendMessage();
        });

        refresh.setOnClickListener(v->{
            getLastMessage();
            tvMsg.setText("");
        });
        getChatRequest();
        getLastMessage();
        chatCustomerAdapter = new ChatCustomerAdapter(chatBookings, listener, mContext);
        list.setAdapter(chatCustomerAdapter);
        scrollToBottom();

    }

    private  void getChatRequest(){
        if (InternetConnection.checkConnection(mContext)) {

            Map<String, String> params = new HashMap<>();
            params.put("service_chat_booking_id",  bookingChat.getService_chat_booking_id());


            RestClient.post().getMyChat(ApiService.APP_DEVICE_ID,loginDetails.getSk(),params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    JSONObject object = null;
                    try {

                        object = new JSONObject(response.body());
                        Log.e("message",object.getString("message"));

                        if (object.getBoolean("status")) {



                            Type type = new TypeToken<ArrayList<ChatBooking>>() {
                            }.getType();
                            chatBookings = gson.fromJson(object.getJSONArray("listData").toString(), type);
                            //Collections.sort(list,ChatBooking.compareTo(chatBookings));
                            Collections.sort(chatBookings, ChatBooking.byChatId);

                            chatCustomerAdapter = new ChatCustomerAdapter(chatBookings, listener, mContext);
                            list.setAdapter(chatCustomerAdapter);
                            scrollToBottom();
                            if(object.getString("chatStatus").equalsIgnoreCase("2")){
                                chatLayout.setVisibility(View.VISIBLE);
                                refreshLayout.setVisibility(View.GONE);
                            }else if(bookingChat.getStatus().equalsIgnoreCase("1")){
                                chatLayout.setVisibility(View.GONE);
                                refreshLayout.setVisibility(View.VISIBLE);
                            }
                        } else {
                           // Util.show(mContext, "something is wrong");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {

                    try {
                        t.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

        }
    }
    private  void getLastMessage(){
        chatBookingsNewList=new ArrayList<>();
        if (InternetConnection.checkConnection(mContext)) {
            Map<String, String> params = new HashMap<>();
            params.put("service_chat_booking_id",bookingChat.getService_chat_booking_id());
            params.put("chat_to_id",bookingChat.getVendor_id());

            Log.e("bhslast",params.toString());
            RestClient.post().getLastMessage(ApiService.APP_DEVICE_ID,loginDetails.getSk(),params).enqueue(new Callback<String>() {
                @Override
                public void onResponse(@NotNull Call<String> call, Response<String> response) {
                    Gson gson = new Gson();
                    JSONObject object = null;
                    try {
                        object = new JSONObject(response.body());
                        Log.e("message",object.getString("message"));
                        if (object.getBoolean("status")) {
                            Type type = new TypeToken<ArrayList<ChatBooking>>() {
                            }.getType();
                            if(object.getString("chatStatus").equalsIgnoreCase("2")){
                                chatLayout.setVisibility(View.VISIBLE);
                                refreshLayout.setVisibility(View.GONE);
                            }else if(bookingChat.getStatus().equalsIgnoreCase("1")){
                                chatLayout.setVisibility(View.GONE);
                                refreshLayout.setVisibility(View.VISIBLE);
                            }
                            chatBookingsNewList = gson.fromJson(object.getJSONArray("listData").toString(), type);
                            Collections.sort(chatBookingsNewList, ChatBooking.byChatId);

                            if(firstEnter.equalsIgnoreCase("yes")){
                                firstEnter="no";

                                lastChatId=chatBookingsNewList.get(chatBookingsNewList.size()-1).getChatId();
                                //Util.show(mContext,lastChatId);
                                chatBookings.addAll(chatBookingsNewList);
                                chatCustomerAdapter.notifyDataSetChanged();
                            }else{
                                // Util.show(mContext,"bhs"+chatBookingsNewList.get(chatBookingsNewList.size()-1).getChatId());
                                if(lastChatId.equalsIgnoreCase(chatBookingsNewList.get(chatBookingsNewList.size()-1).getChatId())){

                                }else{
                                    lastChatId=chatBookingsNewList.get(chatBookingsNewList.size()-1).getChatId();
                                    chatBookings.addAll(chatBookingsNewList);
                                    chatCustomerAdapter.notifyDataSetChanged();
                                }
                            }



                            //if(lastChatId.equalsIgnoreCase(chatBookingsNewList.get(chatBookingsNewList.size()-1).getChatId())){
                            //  Util.show(mContext, "bhs"+lastChatId);
                            //}else{
                            //chat_to_id=chatBookingsNewList.get(chatBookingsNewList.size()-1).getChatId();

                            //}
                            //chatCustomerAdapter = new ChatCustomerAdapter(chatBookings, listener, mContext);
                            // list.setAdapter(chatCustomerAdapter);
                            scrollToBottom();
                        } else {
                            //Util.show(mContext, "Start with your first message");
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }

                @Override
                public void onFailure(@NotNull Call<String> call, @NotNull Throwable t) {

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
    public void onOptionClick(ChatBooking liveTest, int pos) {

    }
}
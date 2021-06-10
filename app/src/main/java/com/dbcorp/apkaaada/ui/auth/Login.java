package com.dbcorp.apkaaada.ui.auth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.dbcorp.apkaaada.R;
import com.dbcorp.apkaaada.database.SqliteDatabase;
import com.dbcorp.apkaaada.helper.Util;
import com.dbcorp.apkaaada.model.OTP;
import com.dbcorp.apkaaada.model.UserDetails;
import com.dbcorp.apkaaada.network.InternetConnection;
import com.dbcorp.apkaaada.network.RestClient;
import com.dbcorp.apkaaada.ui.auth.Home.HomeActivity;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.textview.MaterialTextView;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.dbcorp.apkaaada.network.ApiService.APP_DEVICE_ID;
import static com.dbcorp.apkaaada.network.ApiService.APP_USER_TYPE;

public class Login extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener {

    TextInputLayout edit_mobile, edit_password;
    MaterialButton submit_btn;

    Context context;
    MaterialTextView singUp, gmail_login,forget_txt;
    SignInButton signInButton;
    GoogleSignInOptions gso;
    GoogleSignInClient mGoogleSignInClient;
    GoogleSignInAccount account;
    int RC_SIGN_IN = 123;
    GoogleApiClient googleApiClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_login);
        init();
        context = this;
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

    }


    public void init() {
        edit_mobile = findViewById(R.id.edit_mobile);
        singUp = findViewById(R.id.singUp);
        edit_password = findViewById(R.id.edit_password);
        submit_btn = findViewById(R.id.submit_btn);
        gmail_login = findViewById(R.id.gmail_login);
        forget_txt=findViewById(R.id.forget_txt);

        gmail_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
        singUp.setOnClickListener(v -> {
            Intent intent = new Intent(context, Register.class);
            startActivity(intent);
            finish();


        });
        forget_txt.setOnClickListener(v->{
            Intent intent = new Intent(context, ForgetActivity.class);
            startActivity(intent);
            finish();
        });
        submit_btn.setOnClickListener(v -> {

            Log.e("bhs", "bhs");
            if(edit_mobile.getEditText().getText().toString().length()==0){
                Util.show(context,"Please enter mobile");
                return;
            }
            if(edit_mobile.getEditText().getText().toString().length()==0){
                Util.show(context,"Please enter mobile");
                return;
            }
            login();
        });
    }

    private void login() {
        if (InternetConnection.checkConnection(context)) {
            Util.showDialog("Please wait..",context);
            Map<String, String> params = new HashMap<>();
            params.put("number", edit_mobile.getEditText().getText().toString());
            params.put("password", edit_password.getEditText().getText().toString());
            params.put("type", APP_USER_TYPE);
            RestClient.post().getLogin(APP_DEVICE_ID, params).enqueue(new Callback<UserDetails>() {
                @Override
                public void onResponse(Call<UserDetails> call, Response<UserDetails> response) {


                    UserDetails obj = response.body();

                    if (obj.getStatus()) {
                        Util.hideDialog();
                        new SqliteDatabase(context).addLogin(obj);
                        Intent intent = new Intent(context, HomeActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Util.show(context, "wrong");
                        Util.hideDialog();
                    }


                }

                @Override
                public void onFailure(Call<UserDetails> call, Throwable t) {

                    try {
                        t.printStackTrace();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    Util.hideDialog();
                }
            });

        }
    }


    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==RC_SIGN_IN){
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }
    private void handleSignInResult(GoogleSignInResult result){
        if(result.isSuccess()){
           // gotoProfile();
            Util.show(getApplicationContext(),"Login SuccessFully");
        }else{
            Toast.makeText(getApplicationContext(),"Sign in cancel",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

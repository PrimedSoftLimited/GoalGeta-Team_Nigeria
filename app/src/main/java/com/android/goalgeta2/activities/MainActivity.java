package com.android.goalgeta2.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ImageSwitcher;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.android.goalgeta2.R;
import com.android.goalgeta2.api.RetrofitClient;
import com.android.goalgeta2.models.ResponseObb;
import com.android.goalgeta2.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Context mContext;
    private ViewFlipper mViewFlipper;
    private ImageSwitcher imageSwitcher;
    final Context context = this;

    //objects of all edit fields
    private AutoCompleteTextView mEmail, myEmail;
    private EditText mUsername, myPassword, mPhoneNo, mPassword, mCnfPassword;
    private ProgressBar myProgress, mProgress;

////    Array of images used for image switcher
//    int imageId[] = {R.drawable.planner, R.drawable.i_can, R.drawable.achieve};
//    int count = imageId.length;
//    int currentIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

////        Handles the background images
//        initializeImageSwitcher();
//        start(null);
//        imageSwitcher = (ImageSwitcher) findViewById(R.id.image_switcher);
//        imageSwitcher.setImageResource(imageId[currentIndex]);

        //Initialize all edit fields
        mUsername = (EditText) findViewById(R.id.signup_username);
        mEmail = (AutoCompleteTextView) findViewById(R.id.signup_email);
        mPhoneNo = (EditText) findViewById(R.id.signup_phone);
        mPassword = (EditText) findViewById(R.id.signup_password);
        mCnfPassword = (EditText) findViewById(R.id.signup_cnfPassword);

        myEmail = (AutoCompleteTextView) findViewById(R.id.signin_email);
        myPassword = (EditText) findViewById(R.id.signin_password);

        //        login dialog
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.activity_login);
        dialog.setTitle("Ready to Begin?");

        //Initialize clickable buttons and text
        findViewById(R.id.signup).setOnClickListener(this);
        findViewById(R.id.signin).setOnClickListener(this);

//        Handles the background slideshow images
        mContext = this;
        mViewFlipper = (ViewFlipper)this.findViewById(R.id.view_flipper);
        mViewFlipper.setInAnimation(this, android.R.anim.fade_in);
        mViewFlipper.setOutAnimation(this, android.R.anim.fade_out);
        mViewFlipper.setAutoStart(true);
        mViewFlipper.setFlipInterval(6000);
        mViewFlipper.startFlipping();


    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.signup:
                userSignUp();
                break;
            case R.id.signin:
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
//                //        login dialog
//                final Dialog dialog = new Dialog(context);
//                dialog.setContentView(R.layout.activity_login);
//                dialog.setTitle("Ready to Begin?");
//
//                Button dialogButton = (Button) dialog.findViewById(R.id.signin_btn);
//                dialogButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        userSignin();
//                    }
//                });
                break;
            case R.id.TOC:
                break;
        }
    }

    private void userSignUp() {

        final String username = mUsername.getText().toString().trim();
        String email = mEmail.getText().toString().trim();
        String phoneNo = mPhoneNo.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        String cnfPassword = mCnfPassword.getText().toString().trim();

        //Validations for username fields required
        if (username.isEmpty()){
            mUsername.setError("Username is required");
            mUsername.requestFocus();
            return;
        }

        //Validation for email
        if (email.isEmpty()){
            mEmail.setError("Email is required");
            mEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            mEmail.setError("Incorrect Email");
            mEmail.requestFocus();
            return;
        }

        //Validation for phone number
        if (phoneNo.isEmpty()){
            mPhoneNo.setError("Phone number is required");
            mPhoneNo.requestFocus();
            return;
        }
        if (phoneNo.length()<11 || phoneNo.length()>11){
            mPhoneNo.setError("Enter a correct phone number");
            mPhoneNo.requestFocus();
            return;
        }

        //Validation for Password
        if (password.isEmpty()){
            mPassword.setError("Password is required");
            mPassword.requestFocus();
            return;
        }

        //Validation for confirm password
        if (cnfPassword.isEmpty()){
            mCnfPassword.setError("Password is required");
            mCnfPassword.requestFocus();
            return;
        }
        if (!cnfPassword.equals(password)){
            mCnfPassword.setError("Password does not match");
            mCnfPassword.requestFocus();
            return;
        }

        myProgress = findViewById(R.id.signup_progress);
        myProgress.setVisibility(View.VISIBLE);
        Call<ResponseObb> call = RetrofitClient.getInstance().getApi().register(username, email, phoneNo, password, cnfPassword);
        call.enqueue(new Callback<ResponseObb>() {
            @Override
            public void onResponse(Call<ResponseObb> call, retrofit2.Response<ResponseObb> response) {

                ResponseObb loginResponse = response.body();
                if (response.code() == 200){
                    Toast.makeText(MainActivity.this, "Account created successfully", Toast.LENGTH_LONG).show();

                    SharedPrefManager.getInstance(getApplicationContext()).userLogin(response.body().getData().getUser());
                    String token = loginResponse.getData().getToken();

                    Intent dashboard = new Intent(MainActivity.this, LoginActivity.class);
                    dashboard.putExtra("token",token);
                    startActivity(dashboard);

                    myProgress.setVisibility(View.INVISIBLE);

                } else {
                    Toast.makeText(MainActivity.this, "User already exist", Toast.LENGTH_LONG).show();
                    myProgress.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseObb> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
                myProgress.setVisibility(View.INVISIBLE);
            }
        });
    }

//    private void userSignin() {
//
//        String email = myEmail.getText().toString().trim();
//        String password = myPassword.getText().toString().trim();
//
//        mProgress = findViewById(R.id.login_progress);
//        mProgress.setVisibility(View.VISIBLE);
//
//            Call<ResponseObb> call = RetrofitClient.getInstance().getApi().login(email, password);
//            call.enqueue(new Callback<ResponseObb>() {
//                @Override
//                public void onResponse(Call<ResponseObb> call, Response<ResponseObb> response) {
//
//                    ResponseObb loginResponse = response.body();
//                    if (response.code() == 200) {
////                    proceed with login
//                        Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_LONG).show();
//                        String token = loginResponse.getData().getToken();
//
//                        Intent dashboard = new Intent(MainActivity.this, Dashboard.class);
//                        dashboard.putExtra("token", token);
//                        startActivity(dashboard);
//                        mProgress.setVisibility(View.INVISIBLE);
//
//                        //     startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
//                        //     token = response.body().getToken().header("Authorization", token);
//
////                    Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
////                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//
//                    } else {
//                        Toast.makeText(getApplicationContext(), String.valueOf(loginResponse.getData().getSuccess()), Toast.LENGTH_LONG).show();
//                        mProgress.setVisibility(View.INVISIBLE);
//                    }
//                }
//
//                @Override
//                public void onFailure(Call<ResponseObb> call, Throwable t) {
//                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
//                    mProgress.setVisibility(View.INVISIBLE);
//                }
//            });
//        }

}

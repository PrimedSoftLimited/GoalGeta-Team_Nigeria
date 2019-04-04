package com.android.goalgeta2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.goalgeta2.R;
import com.android.goalgeta2.api.RetrofitClient;
import com.android.goalgeta2.models.ResponseObb;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity {

    String token;
    TextView profUserName, profEMail, profPhoneNo;
    Button updateProf, deleteProf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        ActionBar actionBar = this.getSupportActionBar();
//        if (actionBar != null){
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }

//      get token from login
        token = getIntent().getStringExtra("token");

//        Identify the various views to modify
        profUserName = (TextView) findViewById(R.id.username_text);
        profEMail = (TextView) findViewById(R.id.email_text);
        profPhoneNo = (TextView) findViewById(R.id.phone_text);
        updateProf = findViewById(R.id.edit_prof);
        deleteProf = findViewById(R.id.delete_prof);

        updateProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent profIntent = new Intent(Profile.this, UpdateUser.class);
                profIntent.putExtra("token", token);
                startActivity(profIntent);
            }
        });

        fullProfile();
    }


    private void fullProfile(){

        Call<ResponseObb> call = RetrofitClient.getInstance().getApi().profile(token);
        call.enqueue(new Callback<ResponseObb>() {
            @Override
            public void onResponse(Call<ResponseObb> call, Response<ResponseObb> response) {

                ResponseObb detailed = response.body();

                profUserName.setText(detailed.getData().getUser().getName());
                profEMail.setText(detailed.getData().getUser().getEmail());
                profPhoneNo.setText(detailed.getData().getUser().getPhone_number());
            }

            @Override
            public void onFailure(Call<ResponseObb> call, Throwable t) {
                Toast.makeText(Profile.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

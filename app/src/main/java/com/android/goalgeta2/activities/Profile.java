package com.android.goalgeta2.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.goalgeta2.R;
import com.android.goalgeta2.api.RetrofitClient;
import com.android.goalgeta2.models.ResponseObb;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile extends AppCompatActivity implements View.OnClickListener {

    String token;
    TextView profUserName, profEMail, profPhoneNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//      get token from login
        token = getIntent().getStringExtra("token");

//        Identify the various views to modify
        profUserName = (TextView) findViewById(R.id.username_text);
        profEMail = (TextView) findViewById(R.id.email_text);
        profPhoneNo = (TextView) findViewById(R.id.phone_text);

        fullProfile();
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.username_edit:
                break;
            case R.id.email_edit:
                break;
            case R.id.phone_edit:
                break;
            case R.id.delete_prof:
                break;
        }
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

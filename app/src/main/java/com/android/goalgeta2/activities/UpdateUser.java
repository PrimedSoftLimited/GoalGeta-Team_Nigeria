package com.android.goalgeta2.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.goalgeta2.R;
import com.android.goalgeta2.api.RetrofitClient;
import com.android.goalgeta2.models.ResponseObb;
import com.android.goalgeta2.models.User;
import com.android.goalgeta2.storage.SharedPrefManager;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateUser extends AppCompatActivity {

    private EditText editTextUsername, editTextEmail, editTextPhone;
    private Button saveChanges;
    String token;
    private ProgressBar updateProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_user);

        //        Initialize Edittext
        editTextUsername = findViewById(R.id.update_editusername);
        editTextEmail = findViewById(R.id.update_editemail);
        editTextPhone = findViewById(R.id.update_editphone);
        saveChanges = findViewById(R.id.save_profile_btn);

        saveChanges.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfile();
            }
        });

    }

    private void updateProfile(){
        final String username = editTextUsername.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String phoneNo = editTextPhone.getText().toString().trim();

        //Validations for username fields required
        if (username.isEmpty()){
            editTextUsername.setError("Username is required");
            editTextUsername.requestFocus();
            return;
        }

        //Validation for email
        if (email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Incorrect Email");
            editTextEmail.requestFocus();
            return;
        }

        //Validation for phone number
        if (phoneNo.isEmpty()){
            editTextPhone.setError("Phone number is required");
            editTextPhone.requestFocus();
            return;
        }

        User user = SharedPrefManager.getInstance(getParent()).getUser();

        updateProgress = (ProgressBar) findViewById(R.id.update_profile_progress);
        updateProgress.setVisibility(View.VISIBLE);
        token = getIntent().getStringExtra("token");
        Call<ResponseObb> call = RetrofitClient.getInstance().getApi().profile(token, username, email, phoneNo);
        call.enqueue(new Callback<ResponseObb>() {
            @Override
            public void onResponse(Call<ResponseObb> call, Response<ResponseObb> response) {
                if (response.code() == 200){

//                    SharedPrefManager.getInstance(getParent()).
                    token = response.body().getData().getToken();
                    Toast.makeText(UpdateUser.this, "Profile Updated", Toast.LENGTH_LONG).show();
                    updateProgress.setVisibility(View.INVISIBLE);
                } else {
                    Toast.makeText(UpdateUser.this, "Unable to update profile", Toast.LENGTH_LONG).show();
                    updateProgress.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<ResponseObb> call, Throwable t) {
                Toast.makeText(UpdateUser.this, t.getMessage(), Toast.LENGTH_LONG).show();
                updateProgress.setVisibility(View.INVISIBLE);
            }
        });
    }
}

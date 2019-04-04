package com.android.goalgeta2.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.goalgeta2.R;
import com.android.goalgeta2.api.RetrofitClient;
import com.android.goalgeta2.models.GoalResponse;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateGoals extends AppCompatActivity {

    private EditText customGoal_titles, customGoal_descriptions, customGoal_starts, customGoal_finishes, goal_completions;
    private Button customCommit_btns;
    private DatePickerDialog.OnDateSetListener mDateSetListener, myDateSetListener;
    private static final String TAG = "CreateGoals";
    String token;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_goals);

        customGoal_titles = findViewById(R.id.customGoal_title);
        customGoal_descriptions = findViewById(R.id.customGoal_description);
        customCommit_btns = findViewById(R.id.customCommit_goals);
        customGoal_starts = findViewById(R.id.customGoal_start);
        customGoal_finishes = findViewById(R.id.customGoal_end);

        goal_completions = findViewById(R.id.customGoal_completion_status);
        progressBar = findViewById(R.id.create_goal_progress);

        customGoal_starts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cale = Calendar.getInstance();
                int year = cale.get(Calendar.YEAR);
                int month = cale.get(Calendar.MONTH);
                int day = cale.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        CreateGoals.this,
                        android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                        myDateSetListener,
                        year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
                datePickerDialog.show();

            }
        });

        myDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d(TAG, "onDataSet: mm/dd/yyyy: " + month + "/" + dayOfMonth + "/" + year);

                String start_date = month + "/" + dayOfMonth + "/" + year;
                customGoal_starts.setText(start_date);
            }
        };

        customGoal_finishes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        CreateGoals.this,
                        android.R.style.Theme_DeviceDefault_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.DKGRAY));
                datePickerDialog.show();

            }
        });

        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                month = month + 1;
                Log.d(TAG, "onDataSet: mm/dd/yyyy: " + month + "/" + dayOfMonth + "/" + year);

                String date = month + "/" + dayOfMonth + "/" + year;
                customGoal_finishes.setText(date);
            }
        };

        token = getIntent().getStringExtra("token");

        customCommit_btns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createCustomGoals();
            }
        });
    }

    public void createCustomGoals(){

        final String custom_goal_title = customGoal_titles.getText().toString().trim();
        final String custom_goal_description = customGoal_descriptions.getText().toString().trim();
        final String custom_goal_start = customGoal_starts.getText().toString().trim();
        final String custom_goal_finish = customGoal_finishes.getText().toString().trim();
        final String custom_goal_completion = goal_completions.getText().toString().trim();


        //Validations for goals title and description fields required
        if (custom_goal_title.isEmpty()){
            customGoal_titles.setError("Enter a goal title");
            customGoal_titles.requestFocus();
            return;
        }

        if (custom_goal_description.isEmpty()){
            customGoal_descriptions.setError("Enter a goal description");
            customGoal_descriptions.requestFocus();
            return;
        }

        if (custom_goal_start.isEmpty()){
            customGoal_starts.setError("Enter start date");
            customGoal_starts.requestFocus();
            return;
        }

        if (custom_goal_finish.isEmpty()){
            customGoal_finishes.setError("Enter due date");
            customGoal_finishes.requestFocus();
            return;
        }

        if (custom_goal_completion.isEmpty()){
            goal_completions.setError("Your completion for status should be 0");
            goal_completions.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);
        Call<GoalResponse> call = RetrofitClient.getInstance().getApi().goals(token, custom_goal_title, custom_goal_description,
                custom_goal_completion, custom_goal_start, custom_goal_finish);
        call.enqueue(new Callback<GoalResponse>() {
            @Override
            public void onResponse(Call<GoalResponse> call, Response<GoalResponse> response) {
                GoalResponse goalResponse = response.body();
                if(response.code() == 200){
                    Toast.makeText(CreateGoals.this, goalResponse.getData().getMessage(), Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);

                    Intent dashgoal = new Intent(CreateGoals.this, Dashboard.class);
                    dashgoal.putExtra("token", token);
                    startActivity(dashgoal);

                } else{
                    Toast.makeText(CreateGoals.this, "Unable to create goals, try again", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onFailure(Call<GoalResponse> call, Throwable t) {
                Toast.makeText(CreateGoals.this, t.getMessage(), Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}

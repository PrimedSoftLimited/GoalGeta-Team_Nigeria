package com.android.goalgeta2.activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.android.goalgeta2.storage.SharedPrefManager;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditGoals extends AppCompatActivity {

    private EditText editTextGoal, editTextDescription, editTextProgress, editTextStart, editTextFinish;
    private Button saveChangedGoals;
    SharedPrefManager sharedPrefManager;
    SharedPreferences preferences;
    String token;
    private ProgressBar updateGoalsProgress;
    private static final String TAG = "UpdateGoals";
    private DatePickerDialog.OnDateSetListener myDateSetListener, mDateSetListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_goals);

//        Initialize EditText
        editTextGoal = (EditText) findViewById(R.id.edit_goalTitle);
        editTextDescription = (EditText) findViewById(R.id.edit_goalDescription);
        editTextProgress = (EditText) findViewById(R.id.edit_goalCompletion_status);
        editTextStart = (EditText) findViewById(R.id.edit_startGoal);
        editTextFinish = (EditText) findViewById(R.id.edit_endGoal);
        updateGoalsProgress = findViewById(R.id.edit_goal_progress);

        saveChangedGoals = (Button) findViewById(R.id.save_goal_btn);

        editTextStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EditGoals.this,
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
                editTextStart.setText(start_date);
            }
        };

        editTextFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        EditGoals.this,
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
                editTextFinish.setText(date);
            }
        };

        saveChangedGoals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateGoals();
            }
        });
    }

    private void updateGoals() {

        final String edit_goal_title = editTextGoal.getText().toString().trim();
        final String edit_goal_description = editTextDescription.getText().toString().trim();
        final String edit_goal_start = editTextStart.getText().toString().trim();
        final String edit_goal_finish = editTextFinish.getText().toString().trim();
        final String edit_goal_completion = editTextProgress.getText().toString().trim();


        //Validations for goals title and description fields required
        if (edit_goal_title.isEmpty()){
            editTextGoal.setError("Enter a goal title");
            editTextGoal.requestFocus();
            return;
        }

        if (edit_goal_description.isEmpty()){
            editTextDescription.setError("Enter a goal description");
            editTextDescription.requestFocus();
            return;
        }

        if (edit_goal_start.isEmpty()){
            editTextStart.setError("Enter start date");
            editTextStart.requestFocus();
            return;
        }

        if (edit_goal_finish.isEmpty()){
            editTextFinish.setError("Enter due date");
            editTextFinish.requestFocus();
            return;
        }

        if (edit_goal_completion.isEmpty()){
            editTextProgress.setError("Your completion for status should be 0");
            editTextProgress.requestFocus();
            return;
        }

        updateGoalsProgress.setVisibility(View.VISIBLE);
        token = sharedPrefManager.getUser().getToken();
        Call<GoalResponse> call = RetrofitClient.getInstance().getApi().updateGoals(token, edit_goal_title, edit_goal_description,
                edit_goal_start, edit_goal_finish, edit_goal_completion);

        call.enqueue(new Callback<GoalResponse>() {
            @Override
            public void onResponse(Call call, Response response) {
               if (response.code() == 200){
                   Toast.makeText(EditGoals.this, response.message(), Toast.LENGTH_LONG).show();
                   updateGoalsProgress.setVisibility(View.INVISIBLE);
                   Intent backIntent = new Intent(EditGoals.this, Dashboard.class);
                   startActivity(backIntent);
               } else {
                   Toast.makeText(EditGoals.this, "Unable to Update goal", Toast.LENGTH_LONG).show();
                   updateGoalsProgress.setVisibility(View.INVISIBLE);
               }

            }

            @Override
            public void onFailure(Call call, Throwable t) {
                Toast.makeText(EditGoals.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                updateGoalsProgress.setVisibility(View.INVISIBLE);
            }
        });
    }
}

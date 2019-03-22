package com.android.goalgeta2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

;
import com.android.goalgeta2.R;
import com.android.goalgeta2.api.RetrofitClient;
import com.android.goalgeta2.models.ResponseObb;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Dashboard extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    TextView userName, eMail;
    String token;
    Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        get token from login
        token = getIntent().getStringExtra("token");

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

//        For the create button
        create = findViewById(R.id.create_btn);
//        create.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent goals = new Intent(Dashboard.this, Goals.class);
//                goals.putExtra("token",token);
//                startActivity(goals);
//            }
//        });


//        Handles navigation header text
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView = navigationView.getHeaderView(0);
        userName = (TextView)hView.findViewById(R.id.nav_username);
        eMail = (TextView)hView.findViewById(R.id.nav_email);

        navigationView.setNavigationItemSelectedListener(this);

//        Calls the method used to parse in data with token
        userProfile();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            // Handle the camera action
        } else if (id == R.id.nav_profile) {
            Intent profile = new Intent(Dashboard.this, Profile.class);
            profile.putExtra("token",token);
            startActivity(profile);
        } else if (id == R.id.nav_notification) {

        } else if (id == R.id.nav_settings) {

        } else if (id == R.id.nav_about) {

        } else if (id == R.id.nav_logout) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void userProfile(){

        Call<ResponseObb> call = RetrofitClient.getInstance().getApi().profile(token);
        call.enqueue(new Callback<ResponseObb>() {
            @Override
            public void onResponse(Call<ResponseObb> call, Response<ResponseObb> response) {

                ResponseObb details = response.body();

                userName.setText(details.getData().getUser().getName());
                eMail.setText(details.getData().getUser().getEmail());
            }

            @Override
            public void onFailure(Call<ResponseObb> call, Throwable t) {
                Toast.makeText(Dashboard.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
}

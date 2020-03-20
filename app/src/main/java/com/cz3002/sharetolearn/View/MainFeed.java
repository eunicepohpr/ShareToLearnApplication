package com.cz3002.sharetolearn.View;

import android.content.Intent;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.View.Authentication.SignIn;
import com.cz3002.sharetolearn.models.User;
import com.cz3002.sharetolearn.viewModel.MainUserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainFeed extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private FloatingActionButton fab;
    private ImageView nhProfileIV;
    private TextView nhNameTV, nhEmailTV;
    private MainUserViewModel mainUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_feed);
        mainUserViewModel = ViewModelProviders.of(this).get(MainUserViewModel.class);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Create post", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        hideFloatingActionButton();

        // Load user details in side nav header
        nhProfileIV = header.findViewById(R.id.nh_imageView);
        nhNameTV = header.findViewById(R.id.nh_name);
        nhEmailTV = header.findViewById(R.id.nh_email);
        mainUserViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                nhNameTV.setText(user.getName());
                nhEmailTV.setText(user.getEmail());
                if (user.getImageURL() != "")
                    Glide.with(getApplicationContext()).load(user.getImageURL()).apply(RequestOptions.circleCropTransform()).into(nhProfileIV);
                else
                    Glide.with(getApplicationContext()).load(R.mipmap.ic_launcher_round).apply(RequestOptions.circleCropTransform()).into(nhProfileIV);
            }
        });

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(R.id.nav_home, R.id.nav_profile,
                R.id.nav_courses, R.id.nav_course_review).setDrawerLayout(drawer).build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    public void showFloatingActionButton() {
        fab.show();
    }

    public void hideFloatingActionButton() {
        fab.hide();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_feed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
                Toast.makeText(getApplicationContext(), "Successfully logged out", Toast.LENGTH_LONG).show();
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainFeed.this, SignIn.class);
                startActivity(intent);
                finish(); // to stop it from rerunning
                return true;
//            case R.id.action_courseconfig:
//                PypFragment fragment = new PypFragment();
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                FrameLayout fl = (FrameLayout) findViewById(R.id.nav_host_fragment);
//                fl.removeAllViews();
//                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                fragmentTransaction.add(R.id.nav_host_fragment, fragment);
//                fragmentTransaction.commit();
//                //Sharing
//                *//*Intent shareIntent = new Intent (Intent.ACTION_SEND);
//                shareIntent.setType("text/plain");
//                String shareBody = "your body here";
//                String shareSub = "Your subject here";
//                shareIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
//                shareIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
//                startActivity(Intent.createChooser(shareIntent, "Share App Locker"));*//*
//                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}

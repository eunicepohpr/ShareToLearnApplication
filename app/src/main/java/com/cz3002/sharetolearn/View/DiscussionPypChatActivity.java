package com.cz3002.sharetolearn.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;


import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.models.Course;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DiscussionPypChatActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener,
                                                                            BottomNavigationView.OnNavigationItemReselectedListener{
    private Course course;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_pyp_chat);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        BottomNavigationView navigationView = findViewById(R.id.nav_view);
        course = (Course) getIntent().getExtras().get("course");
        loadFragment(new DiscussionFragment(course));
        navigationView.setOnNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment selectedFragment = null;
        switch (menuItem.getItemId()){
            case R.id.nav_discussion:
                selectedFragment = new DiscussionFragment(course);
                break;
            case R.id.nav_pyp:
                selectedFragment = new PypFragment(course);
                break;
            case R.id.nav_chat:
                selectedFragment = new ChatFragment(course);
                break;
        }
        return loadFragment(selectedFragment);
    }

    @Override
    public void onNavigationItemReselected(@NonNull MenuItem menuItem) {

    }

    private boolean loadFragment(Fragment fragment){
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_content, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        return true;
    }

}

package com.cz3002.sharetolearn.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SearchView;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.ui.courses.UnsubscribedCoursesViewModel;

import java.util.ArrayList;

public class AddCourseActivity extends AppCompatActivity {
    private UnsubscribedCoursesViewModel unsubscribedCoursesViewModel;
    private ListView courseListView;
    private CourseAdapter courseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void onResume() {
        super.onResume();
        courseListView = findViewById(R.id.course_list);

        unsubscribedCoursesViewModel = ViewModelProviders.of(this).get(UnsubscribedCoursesViewModel.class);
        ArrayList<String> courseList = unsubscribedCoursesViewModel.getCourseList().getValue();
        courseAdapter = new CourseAdapter(getApplicationContext(), courseList);
        courseListView.setAdapter(courseAdapter);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.options_menu, menu);
//
//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
//        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
//        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//        searchView.setIconifiedByDefault(false);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}

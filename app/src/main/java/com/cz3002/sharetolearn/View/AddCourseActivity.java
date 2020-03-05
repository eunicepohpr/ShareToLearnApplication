package com.cz3002.sharetolearn.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.adapter.CourseAdapter;
import com.cz3002.sharetolearn.models.Course;
import com.cz3002.sharetolearn.viewModel.CourseViewModel;
//import com.cz3002.sharetolearn.viewModel.UnsubscribedCoursesViewModel;

import java.util.ArrayList;

public class AddCourseActivity extends AppCompatActivity {
    //private UnsubscribedCoursesViewModel unsubscribedCoursesViewModel;
    private ListView courseListView;
    private CourseAdapter courseAdapter;
    private ImageView courseSelected;
    private CourseViewModel courseViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        courseViewModel.getCourseList().observe(this, new Observer<ArrayList<Course>>() {
            @Override
            public void onChanged(ArrayList<Course> courses) {
                courseAdapter = new CourseAdapter(getApplicationContext(), courses);
                courseListView.setAdapter(courseAdapter);
                courseAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        courseListView = findViewById(R.id.course_list);
        courseSelected = findViewById(R.id.listitem_courseselected);

        //unsubscribedCoursesViewModel = ViewModelProviders.of(this).get(UnsubscribedCoursesViewModel.class);
        //ArrayList<String> courseList = unsubscribedCoursesViewModel.getCourseList().getValue();
        //courseAdapter = new CourseAdapter(getApplicationContext(), courseList);
        //courseListView.setAdapter(courseAdapter);

        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                String msg = adapterView.getItemAtPosition(position).toString();
                Toast toast = Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT);
                toast.show();

                //courseSelected.setVisibility(View.VISIBLE);
            }
        });
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

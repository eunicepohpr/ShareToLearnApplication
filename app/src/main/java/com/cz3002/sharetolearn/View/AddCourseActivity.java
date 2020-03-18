package com.cz3002.sharetolearn.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.adapter.AddCourseAdapter;
import com.cz3002.sharetolearn.adapter.CourseAdapter;
import com.cz3002.sharetolearn.models.Course;
import com.cz3002.sharetolearn.models.User;
import com.cz3002.sharetolearn.viewModel.CourseViewModel;
import com.cz3002.sharetolearn.viewModel.MainUserViewModel;
//import com.cz3002.sharetolearn.viewModel.UnsubscribedCoursesViewModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

public class AddCourseActivity extends AppCompatActivity {
    //private UnsubscribedCoursesViewModel unsubscribedCoursesViewModel;
    private ListView courseListView;
    private AddCourseAdapter courseAdapter;
    private ImageView courseSelected;
    private CourseViewModel courseViewModel;
    private MainUserViewModel mainUserViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_course);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        mainUserViewModel = ViewModelProviders.of(this).get(MainUserViewModel.class);
        courseListView = findViewById(R.id.course_list);
        courseAdapter = new AddCourseAdapter(this, new ArrayList<Course>(), new HashSet<String>(), "");
        courseListView.setAdapter(courseAdapter);
        courseViewModel.getCourseList().observe(this, new Observer<ArrayList<Course>>() {
            @Override
            public void onChanged(ArrayList<Course> courses) {
                if (courses == null) return;
                Collections.sort(courses, new Comparator<Course>() {
                    @Override
                    public int compare(Course c1, Course c2) {
                        return c1.getCourseCode().compareTo(c2.getCourseCode());
                    }
                });
                if (mainUserViewModel.getUser().getValue() != null) {
                    User mainUser = mainUserViewModel.getUser().getValue();
                    courseAdapter.updateData(getApplicationContext(), courses, mainUser.getRegisteredCourseKeys(), mainUser.getKey());
                }
            }
        });
        mainUserViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                List<Course> courses;
                if (courseViewModel.getCourseList().getValue() == null) return;
                else courses = courseViewModel.getCourseList().getValue();
                Collections.sort(courses, new Comparator<Course>() {
                    @Override
                    public int compare(Course c1, Course c2) {
                        return c1.getCourseCode().compareTo(c2.getCourseCode());
                    }
                });
                if (user != null) {
                    courseAdapter.updateData(getApplicationContext(), courses, user.getRegisteredCourseKeys(), user.getKey());
                }
            }
        });
        final SearchView searchView = findViewById(R.id.search_view);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchView.setIconified(false);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                List<Course> courses = courseAdapter.getCourses();
                List<Course> displayCourses = new ArrayList<>();
                for (Course course: courses){
                    String displayString = course.getCourseCode()+" "+course.getTitle();
                    if (displayString.toLowerCase().contains(s.toLowerCase())){
                        displayCourses.add(course);
                    }
                }
                courseAdapter.setDisplayedCourses(displayCourses);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                courseAdapter.resetDisplayedCourses();
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        courseSelected = findViewById(R.id.listitem_courseselected);


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

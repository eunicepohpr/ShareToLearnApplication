package com.cz3002.sharetolearn.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.adapter.CourseAdapter;
import com.cz3002.sharetolearn.models.Course;
import com.cz3002.sharetolearn.models.User;
import com.cz3002.sharetolearn.viewModel.CourseViewModel;
import com.cz3002.sharetolearn.viewModel.MainUserViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class CoursesFragment extends Fragment {

    private CourseViewModel coursesViewModel;
    private MainUserViewModel mainUserViewModel;
    private ListView courseListView;
    private CourseAdapter courseAdapter;

    public static CoursesFragment newInstance() {
        return new CoursesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        coursesViewModel = ViewModelProviders.of(getActivity()).get(CourseViewModel.class);
        mainUserViewModel = ViewModelProviders.of(getActivity()).get(MainUserViewModel.class);
        View coursesFragmentView = inflater.inflate(R.layout.courses_fragment, container, false);
        ((MainFeed) getActivity()).hideFloatingActionButton();
        courseListView = coursesFragmentView.findViewById(R.id.course_list);
        mainUserViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user != null){
                    List<Course> registeredCourses = new ArrayList<>();
                    Set<String> registeredCourseKeys = user.getRegisteredCourseKeys();
                    for (Course course: coursesViewModel.getCourseList().getValue()){
                        if (registeredCourseKeys.contains(course.getKey()))
                            registeredCourses.add(course);
                        if (registeredCourses.size() == registeredCourseKeys.size()) break;
                    }
                    Collections.sort(registeredCourses, new Comparator<Course>() {
                        @Override
                        public int compare(Course c1, Course c2) {
                            return c1.getCourseCode().compareTo(c2.getCourseCode());
                        }
                    });
                    courseAdapter = new CourseAdapter(getActivity(), registeredCourses);
                    courseListView.setAdapter(courseAdapter);
                    courseAdapter.notifyDataSetChanged();
                }
            }
        });
        coursesViewModel.getCourseList().observe(this, new Observer<ArrayList<Course>>() {
            @Override
            public void onChanged(ArrayList<Course> courses) {
                if (mainUserViewModel.getUser().getValue() != null){
                    Set<String> registeredCourseKeys = mainUserViewModel.getUser().getValue().getRegisteredCourseKeys();
                    List<Course> registeredCourses = new ArrayList<>();
                    for (Course course: courses){
                        if (registeredCourseKeys.contains(course.getKey()))
                            registeredCourses.add(course);
                        if (registeredCourses.size() == registeredCourseKeys.size()) break;
                    }
                    Collections.sort(registeredCourses, new Comparator<Course>() {
                        @Override
                        public int compare(Course c1, Course c2) {
                            return c1.getCourseCode().compareTo(c2.getCourseCode());
                        }
                    });
                    courseAdapter = new CourseAdapter(getActivity(), registeredCourses);
                    courseListView.setAdapter(courseAdapter);
                    courseAdapter.notifyDataSetChanged();
                }
            }
        });

/*        coursesViewModel.getCourseList().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> courseList) {
                courseAdapter = new CourseAdapter(getActivity(), courseList);
                courseListView.setAdapter(courseAdapter);
                courseAdapter.notifyDataSetChanged();
            }
        });*/
        return coursesFragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent(getActivity(), DiscussionPypChatActivity.class);
                intent.putExtra("course", (Course) adapterView.getItemAtPosition(position));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.add_course_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_course:
                startActivity(new Intent(getActivity(), AddCourseActivity.class));
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}

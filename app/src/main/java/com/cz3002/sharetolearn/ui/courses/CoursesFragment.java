package com.cz3002.sharetolearn.ui.courses;

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
import com.cz3002.sharetolearn.ui.AddCourseActivity;
import com.cz3002.sharetolearn.ui.CourseAdapter;
import com.cz3002.sharetolearn.ui.DiscussionPypChatActivity;
import com.cz3002.sharetolearn.ui.MainFeed;

import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class CoursesFragment extends Fragment {

    private SubscribedCoursesViewModel coursesViewModel;
    private ListView courseListView;
    private CourseAdapter courseAdapter;

    public static CoursesFragment newInstance() {
        return new CoursesFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        coursesViewModel = ViewModelProviders.of(this).get(SubscribedCoursesViewModel.class);
        View coursesFragmentView = inflater.inflate(R.layout.courses_fragment, container, false);
        ((MainFeed) getActivity()).hideFloatingActionButton();
        courseListView = coursesFragmentView.findViewById(R.id.course_list);

        coursesViewModel.getCourseList().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> courseList) {
                courseAdapter = new CourseAdapter(getActivity(), courseList);
                courseListView.setAdapter(courseAdapter);
                courseAdapter.notifyDataSetChanged();
            }
        });;
        return coursesFragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String msg = adapterView.getItemAtPosition(position).toString();
                startActivity(new Intent(getActivity(), DiscussionPypChatActivity.class));
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

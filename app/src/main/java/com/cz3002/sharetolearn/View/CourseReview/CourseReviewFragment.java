package com.cz3002.sharetolearn.View.CourseReview;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.View.MainFeed;
import com.cz3002.sharetolearn.adapter.CourseAdapter;
import com.cz3002.sharetolearn.models.Course;
import com.cz3002.sharetolearn.viewModel.CourseReviewViewModel;
import com.cz3002.sharetolearn.viewModel.CourseViewModel;

import java.util.ArrayList;

public class CourseReviewFragment extends Fragment {

    private CourseReviewViewModel courseReviewViewModel;
    private CourseViewModel courseViewModel;
    private ArrayList<Course> coursesList = new ArrayList<>();
    private ListView courseListView;
    private CourseAdapter courseAdapter;
    private EditText search;

    public static CourseReviewFragment newInstance() {
        return new CourseReviewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ((MainFeed) getActivity()).hideFloatingActionButton();
        courseReviewViewModel = ViewModelProviders.of(this).get(CourseReviewViewModel.class);
        //courseViewModel = ViewModelProviders.of(this).get(CourseViewModel.class);
        View courseFragmentView = inflater.inflate(R.layout.courses_fragment, container, false);
        courseListView = courseFragmentView.findViewById(R.id.course_list);

        courseReviewViewModel.getCourseList().observe(this, new Observer<ArrayList<Course>>() {
            @Override
            public void onChanged(ArrayList<Course> courses) {
                coursesList = courses;
                courseAdapter = new CourseAdapter(getActivity(), courses);
                courseListView.setAdapter(courseAdapter);
                courseAdapter.notifyDataSetChanged();
            }
        });
        search = (EditText) courseFragmentView.findViewById(R.id.course_inputSearch);
        return courseFragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Course selectedCourse = (Course) adapterView.getItemAtPosition(position);
//                Course selectedCourse = coursesList.get(position);
                //Toast.makeText(getActivity(), course.getCourseCode(),Toast.LENGTH_SHORT).show();
                Bundle args = new Bundle();
                Intent courseReviewActivity = new Intent(getActivity(), CourseReviewActivity.class);
                args.putSerializable("SELECTEDCOURSE", selectedCourse);
                courseReviewActivity.putExtra("BUNDLE", args);
                startActivity(courseReviewActivity);
                //startActivity(new Intent(getActivity(), CourseReviewActivity.class));
            }
        });

        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                CourseReviewFragment.this.courseAdapter.getFilter().filter(s);
                courseListView.setAdapter(courseAdapter);
                courseAdapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }
}

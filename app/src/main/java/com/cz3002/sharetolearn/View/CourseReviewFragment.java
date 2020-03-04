package com.cz3002.sharetolearn.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.adapter.CourseAdapter;
import com.cz3002.sharetolearn.viewModel.CourseReviewViewModel;

import java.util.ArrayList;

public class CourseReviewFragment extends Fragment {

    private CourseReviewViewModel courseReviewViewModel;
    private ListView courseListView;
    private CourseAdapter courseAdapter;

    public static CourseReviewFragment newInstance() {
        return new CourseReviewFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        ((MainFeed) getActivity()).hideFloatingActionButton();
        courseReviewViewModel = ViewModelProviders.of(this).get(CourseReviewViewModel.class);
        View courseFragmentView = inflater.inflate(R.layout.courses_fragment, container, false);
        courseListView = courseFragmentView.findViewById(R.id.course_list);

        courseReviewViewModel.getCourseList().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> courseList) {
                courseAdapter = new CourseAdapter(getActivity(), courseList);
                courseListView.setAdapter(courseAdapter);
                courseAdapter.notifyDataSetChanged();
            }
        });;
        return courseFragmentView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        courseListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String msg = adapterView.getItemAtPosition(position).toString();
                startActivity(new Intent(getActivity(), CourseReviewActivity.class));
            }
        });
    }
}

package com.cz3002.sharetolearn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.models.Course;
import com.cz3002.sharetolearn.models.CourseReview;

import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends BaseAdapter {

    private Context context;
    private List<Course> courses;
    private static LayoutInflater inflater = null;

    public CourseAdapter(Context context, List<Course> courses) {
        this.context = context;
        this.courses = courses;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return courses.size();
    }

    @Override
    public Object getItem(int i) {
        return courses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Course course = courses.get(i);

        if (view == null) view = inflater.inflate(R.layout.listitem_course, null);
        TextView coursenameTV = view.findViewById(R.id.listitem_coursename);
        String courseName = course.getCourseCode() + " " + course.getTitle();
        coursenameTV.setText(courseName);
        return view;
    }
}

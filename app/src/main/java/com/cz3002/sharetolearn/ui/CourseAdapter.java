package com.cz3002.sharetolearn.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cz3002.sharetolearn.R;

import java.util.ArrayList;

public class CourseAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> courseList;
    private static LayoutInflater inflater = null;

    public CourseAdapter(Context context, ArrayList<String> courseList) {
        this.context = context;
        this.courseList = courseList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String s = courseList.get(position);
        if (convertView == null) convertView = inflater.inflate(R.layout.listitem_course, null);
        TextView courseCodeTV = convertView.findViewById(R.id.listitem_coursecode);
        courseCodeTV.setText(courseList.get(position));
        return convertView;
    }

    @Override
    public int getCount() {
        return courseList.size();
    }

    @Override
    public Object getItem(int position) {
        return courseList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}

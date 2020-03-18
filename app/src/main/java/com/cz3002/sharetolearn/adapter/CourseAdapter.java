package com.cz3002.sharetolearn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.models.Course;
import com.cz3002.sharetolearn.models.CourseReview;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class CourseAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private ArrayList<Course> courses;
    private ArrayList<Course> filteredList;
    private static LayoutInflater inflater = null;

    public CourseAdapter(Context context, ArrayList<Course> courses) {
        this.context = context;
        this.courses = courses;
        this.filteredList = courses;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return filteredList.size();
    }

    @Override
    public Object getItem(int i) {
        return filteredList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        Course course = filteredList.get(i);

        if (view == null) view = inflater.inflate(R.layout.listitem_course, null);
        TextView coursenameTV = view.findViewById(R.id.listitem_coursename);
        String courseName = course.getCourseCode() + " " + course.getTitle();
        coursenameTV.setText(courseName);
        return view;
    }

    @Override
    public Filter getFilter() {
        return new Filter()
        {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence)
            {
                FilterResults results = new FilterResults();
                String charString = charSequence.toString();
                //If there's nothing to filter on, return the original data to list
                if(charSequence == null || charSequence.length() == 0)
                {
                    results.values = courses;
                    results.count = courses.size();
                } else
                {
                    ArrayList<Course> filterResultsData = new ArrayList<Course>();

                    for(Course c : courses)
                    {
                        if(c.getCourseCode().toLowerCase().contains(charString.toLowerCase()))
                        {
                            filterResultsData.add(c);
                        }
                    }

                    results.values = filterResultsData;
                    results.count = filterResultsData.size();
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults)
            {
                filteredList = (ArrayList<Course>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }
}

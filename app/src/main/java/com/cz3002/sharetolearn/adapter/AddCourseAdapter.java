package com.cz3002.sharetolearn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.models.Course;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Set;

public class AddCourseAdapter extends BaseAdapter {
    private Context context;
    private List<Course> courses;
    private Set<String> registeredCourseKeys;
    private String mainUserKey;
    private static LayoutInflater inflater = null;

    public AddCourseAdapter(Context context, List<Course> courses, Set<String> registeredCourseKeys, String mainUserKey) {
        this.context = context;
        this.courses = courses;
        this.registeredCourseKeys = registeredCourseKeys;
        this.mainUserKey = mainUserKey;
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
        final Course course = courses.get(i);
        if (view == null) view = inflater.inflate(R.layout.listitem_course, null);
        TextView coursenameTV = view.findViewById(R.id.listitem_coursename);
        String courseName = course.getCourseCode() + " " + course.getTitle();
        coursenameTV.setText(courseName);
        ImageView selectedCourse = view.findViewById(R.id.listitem_courseselected);
        if (registeredCourseKeys.contains(course.getKey())){
            selectedCourse.setImageResource(R.drawable.minus);
        } else selectedCourse.setImageResource(R.drawable.plus);
        selectedCourse.setVisibility(View.VISIBLE);
        selectedCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                if (registeredCourseKeys.contains(course.getKey())){
                    db.collection("User")
                            .document(mainUserKey)
                            .update("registered", FieldValue.arrayRemove(db.collection("CourseModule").document(course.getKey())));
                    Toast.makeText(context, "unsubscribe course "+course.getCourseCode(), Toast.LENGTH_SHORT).show();
                } else {
                    db.collection("User")
                            .document(mainUserKey)
                            .update("registered", FieldValue.arrayUnion(db.collection("CourseModule").document(course.getKey())));
                    Toast.makeText(context, "subscribe course "+course.getCourseCode(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    public void updateData(Context context, List<Course> courses, Set<String> registeredCourseKeys, String mainUserKey) {
        this.context = context;
        this.courses = courses;
        this.registeredCourseKeys = registeredCourseKeys;
        this.mainUserKey = mainUserKey;
        notifyDataSetChanged();
    }
}

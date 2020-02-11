package com.cz3002.sharetolearn.ui.courses;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UnsubscribedCoursesViewModel extends ViewModel {
    private MutableLiveData<ArrayList<String>> mCourses;
    private ArrayList<String> courseList;

    public UnsubscribedCoursesViewModel(){
        courseList = new ArrayList<String>();
        courseList.add("CZ2006 Software Engineering");
        courseList.add("MH1812 Discrete Mathematics");
        courseList.add("CZ3006 Net Centric Computing");
        mCourses = new MutableLiveData<>();
        mCourses.setValue(courseList);
    }

    public LiveData<ArrayList<String>> getCourseList(){
        return mCourses;
    }
}

package com.cz3002.sharetolearn.ui.courses;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CoursesViewModel extends ViewModel {
    private MutableLiveData<ArrayList<String>> mCourses;
    private ArrayList<String> courseList;

    public CoursesViewModel(){
        courseList = new ArrayList<String>();
        courseList.add("CZ2005 Operating System");
        courseList.add("CZ1011 Engineering Mathematics I");
        courseList.add("CZ3003 Software Design & Analysis");
        mCourses = new MutableLiveData<>();
        mCourses.setValue(courseList);
    }

    public LiveData<ArrayList<String>> getCourseList(){
        return mCourses;
    }
}

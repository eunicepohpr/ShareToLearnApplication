package com.cz3002.sharetolearn.ui.courseconfig;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class CourseConfigViewModel extends ViewModel {

    private MutableLiveData<String> mText;
    private ArrayList<String> courseList;
    private MutableLiveData<ArrayList<String>> mCourseList;

    public CourseConfigViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is course configuration fragment");

        courseList = new ArrayList<>();
        courseList.add("CZ1005");
        courseList.add("CZ1007");
        courseList.add("CZ1011");
        mCourseList = new MutableLiveData<>();
        mCourseList.setValue(courseList);
    }

    public LiveData<String> getText() {
        return mText;
    }

    public LiveData<ArrayList<String>> getCourseList() {
        return mCourseList;
    }
}
/*
package com.cz3002.sharetolearn.viewModel;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SubscribedCoursesViewModel extends ViewModel {
    private MutableLiveData<ArrayList<String>> mCourses;
    private ArrayList<String> courseList;

    public SubscribedCoursesViewModel(){
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
*/

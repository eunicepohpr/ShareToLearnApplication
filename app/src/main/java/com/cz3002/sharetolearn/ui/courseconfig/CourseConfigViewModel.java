package com.cz3002.sharetolearn.ui.courseconfig;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CourseConfigViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public CourseConfigViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is course configuration fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
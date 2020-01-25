package com.cz3002.sharetolearn.ui.discussion;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DiscussionViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DiscussionViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is discussion fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
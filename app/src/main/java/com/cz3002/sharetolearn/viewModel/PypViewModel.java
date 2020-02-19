package com.cz3002.sharetolearn.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PypViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PypViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is PYP fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}
package com.cz3002.sharetolearn.viewModel;

import com.cz3002.sharetolearn.ui.discussion.DiscussionThread;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DiscussionViewModel extends ViewModel {

    private MutableLiveData<List<DiscussionThread>> mDiscussionThreads;

    public DiscussionViewModel() {
        mDiscussionThreads = new MutableLiveData<>();
        ArrayList<DiscussionThread> discussionThreads = new ArrayList<>();
        discussionThreads.add(new DiscussionThread("CZ2005 Help", Arrays.asList("ABC", "XYZ"), 5));
        discussionThreads.add(new DiscussionThread("CZ2005 Tutorial 3", Arrays.asList("QWE", "RTZ"), 10));
        mDiscussionThreads.setValue(discussionThreads);
    }

    public LiveData<List<DiscussionThread>> getDiscussionThreads() {
        return mDiscussionThreads;
    }
}
package com.cz3002.sharetolearn.viewModel;

import com.cz3002.sharetolearn.models.Discussion;
import com.cz3002.sharetolearn.models.DiscussionResponse;
import com.cz3002.sharetolearn.models.User;
import com.google.firebase.Timestamp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DiscussionViewModel extends ViewModel {

    private MutableLiveData<List<Discussion>> mDiscussionThreads;
    private MutableLiveData<List<DiscussionResponse>> mDiscussionResponses;

    public DiscussionViewModel() {
        mDiscussionThreads = new MutableLiveData<>();
        mDiscussionResponses = new MutableLiveData<>();

        List<Discussion> discussionThreads = new ArrayList<>();
        List<DiscussionResponse> discussionResponses = new ArrayList<>();
        Discussion item1 = new Discussion("discussionKey1", "CZ2005", "Differences between threads and processes",
                "user1", "CZ2005 Help", new Date());
        User user1 = new User();
        user1.setKey("user1");
        user1.setName("Lam");
        item1.addResponseKey("response1");
        item1.setPostedBy(user1);
        DiscussionResponse discussionResponse1 = new DiscussionResponse("response1", "discussionKey1", "someUser1",
                "I dont know", new Date());
        User responseUser1 = new User();
        responseUser1.setKey("someUser1");
        responseUser1.setName("Jess");
        discussionResponse1.setPostedBy(responseUser1);
        discussionThreads.add(item1);
        discussionResponses.add(discussionResponse1);

        Discussion item2 = new Discussion("discussionKey2", "CZ2005", "What is a process?",
                "user2", "CZ2005 Tutorial", new Date());
        User user2 = new User();
        user2.setKey("user2");
        user2.setName("John");
        item2.addResponseKey("response2");
        item2.setPostedBy(user2);
        DiscussionResponse discussionResponse2 = new DiscussionResponse("response2", "discussionKey1", "someUser2",
                "please google it", new Date());
        User responseUser2 = new User();
        responseUser2.setKey("someUser2");
        responseUser2.setName("Sean");
        discussionResponse2.setPostedBy(responseUser2);
        discussionThreads.add(item2);
        discussionResponses.add(discussionResponse2);

        mDiscussionThreads.setValue(discussionThreads);
        mDiscussionResponses.setValue(discussionResponses);
    }

    public LiveData<List<Discussion>> getDiscussionThreads() {
        return mDiscussionThreads;
    }

    public LiveData<List<DiscussionResponse>> getDiscussionResponse() { return mDiscussionResponses; }
}
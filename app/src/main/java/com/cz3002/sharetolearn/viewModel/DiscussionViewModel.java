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

    public DiscussionViewModel() {
        mDiscussionThreads = new MutableLiveData<>();
        List<Discussion> discussionThreads = new ArrayList<>();

        Discussion item1 = new Discussion("discussionKey1", "wJo35YneWvYNF5VNh3Mc", "Proof of Dijkstra algorithm",
                "user1", "CZ2001 Help", new Date());
        User user1 = new User();
        user1.setKey("user1");
        user1.setName("Lam");
        item1.addResponseKey("response1");
        item1.setPostedBy(user1);

        discussionThreads.add(item1);

        Discussion item2 = new Discussion("discussionKey2", "YxLTpfzIKMQOk4QifP8u", "How to solve TUT 3 question 1?",
                "user2", "CZ1005 Tutorial", new Date());
        User user2 = new User();
        user2.setKey("user2");
        user2.setName("John");
        item2.addResponseKey("response2");
        item2.setPostedBy(user2);
        item2.addLikeKey("user1");
        discussionThreads.add(item2);
        mDiscussionThreads.setValue(discussionThreads);
    }

    public LiveData<List<Discussion>> getDiscussionThreads() {
        return mDiscussionThreads;
    }

}
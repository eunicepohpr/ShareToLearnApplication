package com.cz3002.sharetolearn.viewModel;

import com.cz3002.sharetolearn.models.DiscussionResponse;
import com.cz3002.sharetolearn.models.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DiscussionResponseViewModel extends ViewModel {
    private MutableLiveData<List<DiscussionResponse>> mDiscussionResponses;

    public DiscussionResponseViewModel(){
        mDiscussionResponses = new MutableLiveData<>();
        List<DiscussionResponse> discussionResponses = new ArrayList<>();

        DiscussionResponse discussionResponse1 = new DiscussionResponse("response1", "discussionKey1", "someUser1",
                "I dont know", new Date());
        User responseUser1 = new User();
        responseUser1.setKey("someUser1");
        responseUser1.setName("Jess");
        discussionResponse1.setPostedBy(responseUser1);
        discussionResponses.add(discussionResponse1);

        DiscussionResponse discussionResponse2 = new DiscussionResponse("response2", "discussionKey1", "someUser2",
                "please google it", new Date());
        User responseUser2 = new User();
        responseUser2.setKey("someUser2");
        responseUser2.setName("Sean");
        discussionResponse2.setPostedBy(responseUser2);
        discussionResponses.add(discussionResponse2);

        mDiscussionResponses.setValue(discussionResponses);
    }

    public LiveData<List<DiscussionResponse>> getDiscussionResponse(){ return mDiscussionResponses; }
}

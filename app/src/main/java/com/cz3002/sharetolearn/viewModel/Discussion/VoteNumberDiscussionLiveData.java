package com.cz3002.sharetolearn.viewModel.Discussion;

import com.cz3002.sharetolearn.models.DiscussionResponse;

import java.util.HashMap;
import java.util.Map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class VoteNumberDiscussionLiveData extends ViewModel {
    private static Map<String, MutableLiveData<Integer>> voteNumbers = new HashMap<>();

    public static LiveData<Integer> getVoteNumber(DiscussionResponse response){
        if (!voteNumbers.containsKey(response.getKey())){
            voteNumbers.put(response.getKey(), new MutableLiveData<Integer>());
            voteNumbers.get(response.getKey()).setValue(response.getUpvoteKeys().size()-response.getDownvoteKeys().size());
        }
        return voteNumbers.get(response.getKey());
    }

    public static void setVoteNumber(String key, int voteNumber) {
        if (!voteNumbers.containsKey(key))
            voteNumbers.put(key, new MutableLiveData<Integer>());
        voteNumbers.get(key).setValue(voteNumber);
    }

    public static void setVoteNumber(DiscussionResponse response){
        if (! voteNumbers.containsKey(response.getKey()))
            voteNumbers.put(response.getKey(), new MutableLiveData<Integer>());
        voteNumbers.get(response.getKey()).setValue(response.getUpvoteKeys().size()-response.getDownvoteKeys().size());
    }
}

package com.cz3002.sharetolearn.viewModel.Pyp;

import com.cz3002.sharetolearn.models.PYPResponse;

import java.util.HashMap;
import java.util.Map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class VoteNumberPypLiveData {
    private static Map<String, MutableLiveData<Integer>> voteNumbers = new HashMap<>();

    public static LiveData<Integer> getVoteNumber(PYPResponse response){
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

    public static void setVoteNumber(PYPResponse response){
        if (! voteNumbers.containsKey(response.getKey()))
            voteNumbers.put(response.getKey(), new MutableLiveData<Integer>());
        voteNumbers.get(response.getKey()).setValue(response.getUpvoteKeys().size()-response.getDownvoteKeys().size());
    }
}

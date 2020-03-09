package com.cz3002.sharetolearn.viewModel;

import com.cz3002.sharetolearn.models.Discussion;

import java.util.HashMap;
import java.util.Map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LikeNumbersViewModel extends ViewModel {
    private static Map<String, MutableLiveData<Integer>> likeNumbers = new HashMap<>();

    public static LiveData<Integer> getLikeNumber(Discussion discussionThread){
        if (!likeNumbers.containsKey(discussionThread.getKey())){
            likeNumbers.put(discussionThread.getKey(), new MutableLiveData<Integer>());
            likeNumbers.get(discussionThread.getKey()).setValue(discussionThread.getLikeKeys().size());
        }
        return likeNumbers.get(discussionThread.getKey());
    }

    public static void setLikeNumber(String key, int likeNumber) {
        if (!likeNumbers.containsKey(key))
            likeNumbers.put(key, new MutableLiveData<Integer>());
        likeNumbers.get(key).setValue(likeNumber);
    }
}

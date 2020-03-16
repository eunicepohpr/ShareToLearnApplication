package com.cz3002.sharetolearn.viewModel;

import com.cz3002.sharetolearn.models.Discussion;

import java.util.HashMap;
import java.util.Map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CommentNumberLiveData extends ViewModel {
    private static Map<String, MutableLiveData<Integer>> commentNumbers = new HashMap<>();

    public static LiveData<Integer> getCommentNumber(Discussion discussionThread){
        if (!commentNumbers.containsKey(discussionThread.getKey())){
            commentNumbers.put(discussionThread.getKey(), new MutableLiveData<Integer>());
            commentNumbers.get(discussionThread.getKey()).setValue(discussionThread.getResponseKeys().size());
        }
        return commentNumbers.get(discussionThread.getKey());
    }

    public static void setCommentNumber(String key, int likeNumber) {
        if (!commentNumbers.containsKey(key))
            commentNumbers.put(key, new MutableLiveData<Integer>());
        commentNumbers.get(key).setValue(likeNumber);
    }

    public static void setCommentNumber(Discussion discussionThread){
        if (! commentNumbers.containsKey(discussionThread.getKey()))
            commentNumbers.put(discussionThread.getKey(), new MutableLiveData<Integer>());
        commentNumbers.get(discussionThread.getKey()).setValue(discussionThread.getResponseKeys().size());
    }
}

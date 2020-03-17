package com.cz3002.sharetolearn.viewModel.Pyp;

import com.cz3002.sharetolearn.models.PYP;

import java.util.HashMap;
import java.util.Map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CommentNumberPypLiveData extends ViewModel {
    private static Map<String, MutableLiveData<Integer>> commentNumbers = new HashMap<>();

    public static LiveData<Integer> getCommentNumber(PYP pyp){
        if (!commentNumbers.containsKey(pyp.getKey())){
            commentNumbers.put(pyp.getKey(), new MutableLiveData<Integer>());
            commentNumbers.get(pyp.getKey()).setValue(pyp.getResponseKeys().size());
        }
        return commentNumbers.get(pyp.getKey());
    }

    public static void setCommentNumber(String key, int commentNumber) {
        if (!commentNumbers.containsKey(key))
            commentNumbers.put(key, new MutableLiveData<Integer>());
        commentNumbers.get(key).setValue(commentNumber);
    }

    public static void setCommentNumber(PYP pyp){
        if (! commentNumbers.containsKey(pyp.getKey()))
            commentNumbers.put(pyp.getKey(), new MutableLiveData<Integer>());
        commentNumbers.get(pyp.getKey()).setValue(pyp.getResponseKeys().size());
    }    
}

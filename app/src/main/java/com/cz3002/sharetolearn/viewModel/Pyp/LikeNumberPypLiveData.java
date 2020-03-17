package com.cz3002.sharetolearn.viewModel.Pyp;

import com.cz3002.sharetolearn.models.PYP;

import java.util.HashMap;
import java.util.Map;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LikeNumberPypLiveData extends ViewModel {
    private static Map<String, MutableLiveData<Integer>> likeNumbers = new HashMap<>();

    public static LiveData<Integer> getLikeNumber(PYP pyp){
        if (!likeNumbers.containsKey(pyp.getKey())){
            likeNumbers.put(pyp.getKey(), new MutableLiveData<Integer>());
            likeNumbers.get(pyp.getKey()).setValue(pyp.getLikeKeys().size());
        }
        return likeNumbers.get(pyp.getKey());
    }

    public static void setLikeNumber(String key, int likeNumber) {
        if (!likeNumbers.containsKey(key))
            likeNumbers.put(key, new MutableLiveData<Integer>());
        likeNumbers.get(key).setValue(likeNumber);
    }

    public static void setLikeNumber(PYP pyp){
        if (! likeNumbers.containsKey(pyp.getKey()))
            likeNumbers.put(pyp.getKey(), new MutableLiveData<Integer>());
        likeNumbers.get(pyp.getKey()).setValue(pyp.getLikeKeys().size());
    }
}

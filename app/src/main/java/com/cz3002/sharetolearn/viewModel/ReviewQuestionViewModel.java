package com.cz3002.sharetolearn.viewModel;

import java.util.ArrayList;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReviewQuestionViewModel extends ViewModel {
    private MutableLiveData<ArrayList<String>> mQuestions;
    private ArrayList<String> questionList;

    public ReviewQuestionViewModel(){
        questionList = new ArrayList<String>();
        questionList.add("How is this course?");
        questionList.add("What would you like this course to improve?");
        mQuestions = new MutableLiveData<>();
        mQuestions.setValue(questionList);
    }

    public LiveData<ArrayList<String>> getQuestionList(){
        return mQuestions;
    }
}

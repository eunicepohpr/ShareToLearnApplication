package com.cz3002.sharetolearn.View;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.viewModel.ReviewQuestionViewModel;

public class ReviewQuestionFragment extends Fragment {

    private ReviewQuestionViewModel mViewModel;

    public static ReviewQuestionFragment newInstance() {
        return new ReviewQuestionFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.question_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(ReviewQuestionViewModel.class);
        // TODO: Use the ViewModel
    }

}

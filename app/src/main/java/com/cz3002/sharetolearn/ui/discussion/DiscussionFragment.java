package com.cz3002.sharetolearn.ui.discussion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cz3002.sharetolearn.R;

public class DiscussionFragment extends Fragment {

    private DiscussionViewModel discussionViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        discussionViewModel =
                ViewModelProviders.of(this).get(DiscussionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_discussion, container, false);
        final TextView textView = root.findViewById(R.id.text_gallery);
        discussionViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}
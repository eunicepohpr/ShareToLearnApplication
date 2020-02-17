package com.cz3002.sharetolearn.ui.discussion;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cz3002.sharetolearn.R;

import java.util.List;

public class DiscussionFragment extends Fragment {

    private DiscussionViewModel discussionViewModel;
    private ListView discussionThreadsListView;
    private DiscussionThreadAdapter discussionThreadAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        discussionViewModel =
                ViewModelProviders.of(this).get(DiscussionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_discussion, container, false);
        discussionThreadsListView = root.findViewById(R.id.discussion_thread_list);
        discussionViewModel.getDiscussionThreads().observe(this, new Observer<List<DiscussionThread>>() {
            @Override
            public void onChanged(@Nullable List<DiscussionThread> discussionThreads) {
                discussionThreadAdapter = new DiscussionThreadAdapter(getActivity(), discussionThreads);
                discussionThreadsListView.setAdapter(discussionThreadAdapter);
                discussionThreadAdapter.notifyDataSetChanged();
            }
        });
        return root;
    }
}
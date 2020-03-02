package com.cz3002.sharetolearn.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.ui.discussion.DiscussionThread;
import com.cz3002.sharetolearn.ui.discussion.DiscussionThreadAdapter;
import com.cz3002.sharetolearn.viewModel.DiscussionViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class DiscussionFragment extends Fragment {

    private DiscussionViewModel discussionViewModel;
    private ListView discussionThreadsListView;
    private DiscussionThreadAdapter discussionThreadAdapter;
    private FloatingActionButton fab;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        discussionViewModel =
                ViewModelProviders.of(this).get(DiscussionViewModel.class);
        View root = inflater.inflate(R.layout.fragment_discussion, container, false);
        discussionThreadsListView = root.findViewById(R.id.discussion_thread_list);

        fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchactivity = new  Intent(getContext(), AddDiscussion.class);
                startActivity(launchactivity);
                /*Snackbar.make(view, "Create post", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        discussionThreadsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                String msg = adapterView.getItemAtPosition(position).toString();
                Intent launchactivity = new  Intent(getContext(), Discussion.class);
                startActivity(launchactivity);
                /*Toast toast = Toast.makeText(view.getContext(), msg, Toast.LENGTH_SHORT);
                toast.show();*/
                //startActivity(new Intent(getActivity(), DiscussionPypChatActivity.class));
            }
        });
    }
}
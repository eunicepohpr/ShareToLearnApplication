package com.cz3002.sharetolearn.View;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.adapter.DiscussionAdapter;
import com.cz3002.sharetolearn.models.Course;
import com.cz3002.sharetolearn.models.Discussion;
import com.cz3002.sharetolearn.viewModel.DiscussionViewModel;
import com.cz3002.sharetolearn.viewModel.MainUserViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DiscussionFragment extends Fragment {
    private MainUserViewModel mainUserViewModel;
    private DiscussionViewModel discussionViewModel;
    private Course course;
    private ListView discussionThreadsListView;
    private DiscussionAdapter discussionAdapter;
    private FloatingActionButton fab;

    public DiscussionFragment(Course course){
        this.course = course;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        discussionViewModel = ViewModelProviders.of(this).get(DiscussionViewModel.class);
        mainUserViewModel = ViewModelProviders.of(this).get(MainUserViewModel.class);
        View root = inflater.inflate(R.layout.fragment_discussion, container, false);
        discussionThreadsListView = root.findViewById(R.id.discussion_thread_list);

        fab = root.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent launchactivity = new  Intent(getContext(), AddDiscussion.class);
                launchactivity.putExtra("course", course);
                startActivity(launchactivity);
                /*Snackbar.make(view, "Create post", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
            }
        });

        discussionViewModel.getDiscussionThreads().observe(this, new Observer<List<Discussion>>() {
            @Override
            public void onChanged(@Nullable List<Discussion> discussionThreads) {
                List<Discussion> discussionList = new ArrayList<>();
                for (Discussion dis: discussionThreads){
                    if (dis.getCourseKey().equals(course.getKey()))
                        discussionList.add(dis);
                }
                Collections.sort(discussionList, new Comparator<Discussion>() {
                    @Override
                    public int compare(Discussion t0, Discussion t1) {
                        return t0.getPostedDateTime().compareTo(t1.getPostedDateTime());
                    }
                });
                discussionAdapter = new DiscussionAdapter(getActivity(), getActivity(),discussionList, mainUserViewModel.getMainUser().getValue());
                discussionThreadsListView.setAdapter(discussionAdapter);
                discussionAdapter.notifyDataSetChanged();
            }
        });
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        discussionThreadsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Discussion discussionThread = (Discussion) adapterView.getItemAtPosition(position);
                Intent launchactivity = new  Intent(getContext(), DiscussionActivity.class);
                DiscussionActivity.discussionThread = discussionThread;
                startActivity(launchactivity);
                /*Toast toast = Toast.makeText(view.getContext(), msg, Toast.LENGTH_SHORT);
                toast.show();*/
                //startActivity(new Intent(getActivity(), DiscussionPypChatActivity.class));
            }
        });
    }
}
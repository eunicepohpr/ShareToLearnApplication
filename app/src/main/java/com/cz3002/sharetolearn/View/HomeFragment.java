package com.cz3002.sharetolearn.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.View.Discussion.DiscussionActivity;
import com.cz3002.sharetolearn.View.Pyp.PypActivity;
import com.cz3002.sharetolearn.adapter.PostAdapter;
import com.cz3002.sharetolearn.models.Discussion;
import com.cz3002.sharetolearn.models.PYP;
import com.cz3002.sharetolearn.models.User;
import com.cz3002.sharetolearn.viewModel.Discussion.DiscussionViewModel;
import com.cz3002.sharetolearn.viewModel.MainUserViewModel;
import com.cz3002.sharetolearn.viewModel.Pyp.PYPViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class HomeFragment extends Fragment {

    private DiscussionViewModel discussionViewModel;
    private PYPViewModel pypViewModel;
    private MainUserViewModel mainUserViewModel;
    private ListView newestPostListView;
    private PostAdapter postAdapter;
    private String mainUserKey;
    private int numOfDisplayed = 6;
    private List<Discussion> discussionList;
    private List<PYP> pypList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        discussionViewModel = ViewModelProviders.of(getActivity()).get(DiscussionViewModel.class);
        pypViewModel = ViewModelProviders.of(getActivity()).get(PYPViewModel.class);
        mainUserViewModel = ViewModelProviders.of(getActivity()).get(MainUserViewModel.class);

        mainUserKey = FirebaseAuth.getInstance().getCurrentUser().getUid();
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        postAdapter = new PostAdapter(getContext(), getActivity(), new ArrayList<Object>(), mainUserKey);
        newestPostListView = root.findViewById(R.id.newest_post);
        newestPostListView.setAdapter(postAdapter);

        discussionViewModel.getDiscussionThreads().observe(this, new Observer<List<Discussion>>() {
            @Override
            public void onChanged(List<Discussion> discussions) {
                discussionList = discussions;
                if (mainUserViewModel.getUser().getValue() == null) {
                    return;
                }
                User user = mainUserViewModel.getUser().getValue();

                List<PYP> pyps = pypViewModel.getpyps().getValue();
                if (pyps == null) pyps = new ArrayList<>();
                pypList = pyps;

                List<Object> newestPosts = new ArrayList<>();

                if (mainUserViewModel.getUser().getValue().getDomain().equals("Student")) {
                    List<Discussion> registeredDiscussion = new ArrayList<>();
                    for (Discussion dis : discussions) {
                        if (user.getRegisteredCourseKeys().contains(dis.getCourseKey()))
                            registeredDiscussion.add(dis);
                    }
                    Collections.sort(registeredDiscussion, new Comparator<Discussion>() {
                        @Override
                        public int compare(Discussion d1, Discussion d2) {
                            return d2.getPostedDateTime().compareTo(d1.getPostedDateTime());
                        }
                    });
                    List<PYP> registeredPYPs = new ArrayList<>();
                    for (PYP pyp : pyps) {
                        if (user.getRegisteredCourseKeys().contains(pyp.getCourseKey())) {
                            registeredPYPs.add(pyp);
                        }
                    }
                    Collections.sort(registeredPYPs, new Comparator<PYP>() {
                        @Override
                        public int compare(PYP p1, PYP p2) {
                            return p1.getPostedDateTime().compareTo(p2.getPostedDateTime());
                        }
                    });
                    for (int i = 0; i < Math.min(registeredDiscussion.size(), numOfDisplayed); i++) {
                        newestPosts.add(registeredDiscussion.get(i));
                    }
                    for (int i = 0; i < Math.min(registeredPYPs.size(), numOfDisplayed); i++) {
                        newestPosts.add(registeredPYPs.get(i));
                    }
                } else {
                    for (int i = 0; i < Math.min(discussionList.size(), numOfDisplayed); i++) {
                        newestPosts.add(discussionList.get(i));
                    }
                    for (int i = 0; i < Math.min(pypList.size(), numOfDisplayed); i++) {
                        newestPosts.add(pypList.get(i));
                    }
                }

                Collections.sort(newestPosts, new Comparator<Object>() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        Date d1, d2;
                        if (o1 instanceof Discussion)
                            d1 = ((Discussion) o1).getPostedDateTime();
                        else d1 = ((PYP) o1).getPostedDateTime();
                        if (o2 instanceof Discussion)
                            d2 = ((Discussion) o2).getPostedDateTime();
                        else d2 = ((PYP) o2).getPostedDateTime();
                        return d2.compareTo(d1);
                    }
                });
                postAdapter.updateData(getActivity(), getActivity(), newestPosts.subList(0, Math.min(newestPosts.size(), numOfDisplayed)), mainUserKey);

            }
        });

        pypViewModel.getpyps().observe(this, new Observer<List<PYP>>() {
            @Override
            public void onChanged(List<PYP> pyps) {
                pypList = pyps;
                if (mainUserViewModel.getUser().getValue() == null) {
                    return;
                }

                User user = mainUserViewModel.getUser().getValue();
                List<Discussion> discussions = discussionViewModel.getDiscussionThreads().getValue();
                if (discussions == null) discussions = new ArrayList<>();
                discussionList = discussions;

                if (pyps == null) pyps = new ArrayList<>();

                List<Object> newestPosts = new ArrayList<>();

                if (mainUserViewModel.getUser().getValue().getDomain().equals("Student")) {
                    List<Discussion> registeredDiscussion = new ArrayList<>();
                    for (Discussion dis : discussions) {
                        if (user.getRegisteredCourseKeys().contains(dis.getCourseKey()))
                            registeredDiscussion.add(dis);
                    }
                    Collections.sort(registeredDiscussion, new Comparator<Discussion>() {
                        @Override
                        public int compare(Discussion d1, Discussion d2) {
                            return d2.getPostedDateTime().compareTo(d1.getPostedDateTime());
                        }
                    });

                    List<PYP> registeredPYPs = new ArrayList<>();
                    for (PYP pyp : pyps) {
                        if (user.getRegisteredCourseKeys().contains(pyp.getCourseKey())) {
                            registeredPYPs.add(pyp);
                        }
                    }
                    Collections.sort(registeredPYPs, new Comparator<PYP>() {
                        @Override
                        public int compare(PYP p1, PYP p2) {
                            return p1.getPostedDateTime().compareTo(p2.getPostedDateTime());
                        }
                    });
                    for (int i = 0; i < Math.min(registeredDiscussion.size(), numOfDisplayed); i++) {
                        newestPosts.add(registeredDiscussion.get(i));
                    }
                    for (int i = 0; i < Math.min(registeredPYPs.size(), numOfDisplayed); i++) {
                        newestPosts.add(registeredPYPs.get(i));
                    }
                } else {
                    for (int i = 0; i < Math.min(discussionList.size(), numOfDisplayed); i++) {
                        newestPosts.add(discussionList.get(i));
                    }
                    for (int i = 0; i < Math.min(pypList.size(), numOfDisplayed); i++) {
                        newestPosts.add(pypList.get(i));
                    }
                }

                Collections.sort(newestPosts, new Comparator<Object>() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        Date d1, d2;
                        if (o1 instanceof Discussion) d1 = ((Discussion) o1).getPostedDateTime();
                        else d1 = ((PYP) o1).getPostedDateTime();
                        if (o2 instanceof Discussion) d2 = ((Discussion) o2).getPostedDateTime();
                        else d2 = ((PYP) o2).getPostedDateTime();
                        return d2.compareTo(d1);
                    }
                });
                postAdapter.updateData(getActivity(), getActivity(), newestPosts.subList(0, Math.min(newestPosts.size(), numOfDisplayed)), mainUserKey);
            }
        });

        mainUserViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if (user == null) {
                    return;
                }
                List<Discussion> discussions = discussionViewModel.getDiscussionThreads().getValue();
                if (discussions == null) discussions = new ArrayList<>();
                discussionList = discussions;

                List<PYP> pyps = pypViewModel.getpyps().getValue();
                if (pyps == null) pyps = new ArrayList<>();
                pypList = pyps;

                List<Object> newestPosts = new ArrayList<>();

                if (mainUserViewModel.getUser().getValue().getDomain().equals("Student")) {
                    List<Discussion> registeredDiscussion = new ArrayList<>();
                    for (Discussion dis : discussions) {
                        if (user.getRegisteredCourseKeys().contains(dis.getCourseKey()))
                            registeredDiscussion.add(dis);
                    }
                    Collections.sort(registeredDiscussion, new Comparator<Discussion>() {
                        @Override
                        public int compare(Discussion d1, Discussion d2) {
                            return d2.getPostedDateTime().compareTo(d1.getPostedDateTime());
                        }
                    });

                    List<PYP> registeredPYPs = new ArrayList<>();
                    for (PYP pyp : pyps) {
                        if (user.getRegisteredCourseKeys().contains(pyp.getCourseKey())) {
                            registeredPYPs.add(pyp);
                        }
                    }
                    Collections.sort(registeredPYPs, new Comparator<PYP>() {
                        @Override
                        public int compare(PYP p1, PYP p2) {
                            return p1.getPostedDateTime().compareTo(p2.getPostedDateTime());
                        }
                    });

                    for (int i = 0; i < Math.min(registeredDiscussion.size(), numOfDisplayed); i++) {
                        newestPosts.add(registeredDiscussion.get(i));
                    }
                    for (int i = 0; i < Math.min(registeredPYPs.size(), numOfDisplayed); i++) {
                        newestPosts.add(registeredPYPs.get(i));
                    }
                } else {
                    for (int i = 0; i < Math.min(discussionList.size(), numOfDisplayed); i++) {
                        newestPosts.add(discussionList.get(i));
                    }
                    for (int i = 0; i < Math.min(pypList.size(), numOfDisplayed); i++) {
                        newestPosts.add(pypList.get(i));
                    }
                }

                Collections.sort(newestPosts, new Comparator<Object>() {
                    @Override
                    public int compare(Object o1, Object o2) {
                        Date d1, d2;
                        if (o1 instanceof Discussion) d1 = ((Discussion) o1).getPostedDateTime();
                        else d1 = ((PYP) o1).getPostedDateTime();
                        if (o2 instanceof Discussion) d2 = ((Discussion) o2).getPostedDateTime();
                        else d2 = ((PYP) o2).getPostedDateTime();
                        return d2.compareTo(d1);
                    }
                });
                postAdapter.updateData(getActivity(), getActivity(), newestPosts.subList(0, Math.min(newestPosts.size(), numOfDisplayed)), mainUserKey);
            }
        });

        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        newestPostListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Object post = adapterView.getItemAtPosition(position);
                if (post instanceof Discussion) {
                    Discussion discussionThread = (Discussion) post;
                    Intent launchactivity = new Intent(getContext(), DiscussionActivity.class);
                    launchactivity.putExtra("key", mainUserKey);
                    DiscussionActivity.discussionThread = discussionThread;
                    startActivity(launchactivity);
                } else {
                    PYP pyp = (PYP) post;
                    Intent launchactivity = new Intent(getContext(), PypActivity.class);
                    launchactivity.putExtra("key", mainUserKey);
                    PypActivity.pyp = pyp;
                    startActivity(launchactivity);
                }

                /*Toast toast = Toast.makeText(view.getContext(), msg, Toast.LENGTH_SHORT);
                toast.show();*/
                //startActivity(new Intent(getActivity(), DiscussionPypChatActivity.class));
            }
        });
    }
}
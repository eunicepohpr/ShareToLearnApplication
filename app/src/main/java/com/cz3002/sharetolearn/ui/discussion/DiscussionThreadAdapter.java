package com.cz3002.sharetolearn.ui.discussion;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.cz3002.sharetolearn.R;

import java.util.List;

public class DiscussionThreadAdapter extends BaseAdapter {
    private Context context;
    private List<DiscussionThread> discussionThreads;
    private static LayoutInflater inflater = null;

    public DiscussionThreadAdapter(Context context, List<DiscussionThread> discussionThreads) {
        this.context = context;
        this.discussionThreads = discussionThreads;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return discussionThreads.size();
    }

    @Override
    public Object getItem(int i) {
        return discussionThreads.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        DiscussionThread discussionThread = discussionThreads.get(i);
        String topic = discussionThread.getTopic();
        int commentNumber = discussionThread.getComments().size();
        int likes = discussionThread.getLikes();

        if (view == null) view = inflater.inflate(R.layout.listitem_discussion_thread, null);
        TextView topicTextView = view.findViewById(R.id.topic);
        topicTextView.setText(topic);
        TextView commentNumberTextView = view.findViewById(R.id.comment_number);
        commentNumberTextView.setText(Integer.toString(commentNumber));
        TextView likedTextView = view.findViewById(R.id.liked_number);
        likedTextView.setText(Integer.toString(likes));
        return view;
    }
}

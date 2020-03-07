package com.cz3002.sharetolearn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.models.Discussion;
import com.cz3002.sharetolearn.models.DiscussionResponse;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class DiscussionReponseAdapter extends BaseAdapter {
    private Context context;
    private static LayoutInflater inflater = null;
    List<DiscussionResponse> discussionResponses;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.US);

    public DiscussionReponseAdapter(Context context, List<DiscussionResponse> discussionResponses){
        this.context = context;
        this.discussionResponses = discussionResponses;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return discussionResponses.size();
    }

    @Override
    public Object getItem(int i) {
        return discussionResponses.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final DiscussionResponse discussionResponse = discussionResponses.get(i);
        if (view == null) view = inflater.inflate(R.layout.listitem_comment, null);
        TextView commentTextView = view.findViewById(R.id.discussion_comment);
        commentTextView.setText((discussionResponse.getAnswer()));
        TextView postDetailsView = view.findViewById(R.id.postDetails);
        postDetailsView.setText("Posted by "+discussionResponse.getPostedBy().getName()+" on "+dateFormat.format(discussionResponse.getPostedDateTime().toDate()));
        TextView voteTextView = view.findViewById(R.id.comment_vote);
        voteTextView.setText(Integer.toString(discussionResponse.getUpvoteKeys().size()));
        ImageView upVoteImageView = view.findViewById(R.id.up_vote);
        ImageView downVoteImageView = view.findViewById(R.id.down_vote);
        View.OnClickListener actionListener = new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    switch (view.getId()){
                        case R.id.up_vote:
//                            if (!discussionResponse.getUpvoteKeys().contains(mainUser.key){
//                                if (discussionResponse.getDownvoteKeys().contains(mainUser.key)){
//                                    discussionResponse.removeDownvoteKey(mainUser.key);
//                                }
//                                discussionResponse.addUpvoteKey(mainUser.key);
//                            }
                            Toast.makeText(context, "up_vote "+discussionResponse.getAnswer(), Toast.LENGTH_SHORT).show();
                            break;
                        case R.id.down_vote:
//                            if (!discussionResponse.getDownvoteKeys().contains(mainUser.key){
//                                if (discussionResponse.getUpvoteKeys().contains(mainUser.key)){
//                                    discussionResponse.removeUpvoteKey(mainUser.key);
//                                }
//                                discussionResponse.addDownvoteKey(mainUser.key);
//                            }
                            Toast.makeText(context, "down_vote "+discussionResponse.getAnswer(), Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            };
        upVoteImageView.setOnClickListener(actionListener);
        downVoteImageView.setOnClickListener(actionListener);
        return view;
    }
}

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
import com.cz3002.sharetolearn.models.DiscussionResponse;
import com.cz3002.sharetolearn.models.User;
import com.cz3002.sharetolearn.viewModel.Discussion.DiscussionResponseViewModel;
import com.cz3002.sharetolearn.viewModel.Discussion.VoteNumberDiscussionLiveData;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;

public class DiscussionReponseAdapter extends BaseAdapter {
    private Map<String, User> userMap;
    private String mainUserKey;
    private Context context;
    private FragmentActivity activity;
    private static LayoutInflater inflater = null;
    List<DiscussionResponse> discussionResponses;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.US);

    public DiscussionReponseAdapter(Context context, FragmentActivity activity, List<DiscussionResponse> discussionResponses, Map<String, User> userMap, String mainUserKey){
        this.context = context;
        this.discussionResponses = discussionResponses;
        this.userMap = userMap;
        this.mainUserKey = mainUserKey;
        this.activity = activity;
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
        if (discussionResponse == null){
            return view;
        }
        TextView commentTextView = view.findViewById(R.id.discussion_comment);
        commentTextView.setText(discussionResponse.getAnswer());
        TextView postDetailsView = view.findViewById(R.id.postDetails);
        String name;
        if (mainUserKey.equals(discussionResponse.getPostedByKey())){
            name = "you";
        } else name = userMap.get(discussionResponse.getPostedByKey()).getName();
        postDetailsView.setText("Posted by "+name+" on "+dateFormat.format(discussionResponse.getPostedDateTime()));
        final TextView voteTextView = view.findViewById(R.id.comment_vote);
        voteTextView.setText(Integer.toString(discussionResponse.getUpvoteKeys().size()));
        VoteNumberDiscussionLiveData.getVoteNumber(discussionResponse).observe(this.activity, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                voteTextView.setText(Integer.toString(integer));
            }
        });
        ImageView upVoteImageView = view.findViewById(R.id.up_vote);
        ImageView downVoteImageView = view.findViewById(R.id.down_vote);
        View.OnClickListener actionListener = new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    switch (view.getId()){
                        case R.id.up_vote:
                            if (!discussionResponse.getUpvoteKeys().contains(mainUserKey)){
                                if (discussionResponse.getDownvoteKeys().contains(mainUserKey)){
                                    discussionResponse.removeDownvoteKey(mainUserKey);
                                }
                                discussionResponse.addUpvoteKey(mainUserKey);
                                Toast.makeText(context, "You have upvoted this answer", Toast.LENGTH_SHORT).show();
                            } else {
                                discussionResponse.removeUpvoteKey(mainUserKey);
                                Toast.makeText(context, "You have undone your upvote for this answer", Toast.LENGTH_SHORT).show();
                            }
                            DiscussionResponseViewModel.updateDiscussionResponseFireStore(context, discussionResponse);
                            break;
                        case R.id.down_vote:
                            if (!discussionResponse.getDownvoteKeys().contains(mainUserKey)){
                                if (discussionResponse.getUpvoteKeys().contains(mainUserKey)){
                                    discussionResponse.removeUpvoteKey(mainUserKey);
                                }
                                discussionResponse.addDownvoteKey(mainUserKey);
                                Toast.makeText(context, "You have downvoted this answer", Toast.LENGTH_SHORT).show();
                            } else {
                                discussionResponse.removeDownvoteKey(mainUserKey);
                                Toast.makeText(context, "You have undone your downvote for this answer", Toast.LENGTH_SHORT).show();
                            }
                            DiscussionResponseViewModel.updateDiscussionResponseFireStore(context, discussionResponse);
                            break;
                    }
                }
            };
        upVoteImageView.setOnClickListener(actionListener);
        downVoteImageView.setOnClickListener(actionListener);
        return view;
    }

    public void updateData(Context context, FragmentActivity activity, List<DiscussionResponse> discussionResponses, Map<String, User> userMap, String mainUserKey){
        this.context = context;
        this.discussionResponses = discussionResponses;
        this.userMap = userMap;
        this.mainUserKey = mainUserKey;
        this.activity = activity;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        notifyDataSetChanged();
    }
}

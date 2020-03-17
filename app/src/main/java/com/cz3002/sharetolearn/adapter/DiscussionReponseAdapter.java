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
import com.cz3002.sharetolearn.viewModel.UserViewModel;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;

public class DiscussionReponseAdapter extends BaseAdapter {
    private String mainUserKey;
    private Context context;
    private UserViewModel userViewModel;
    private FragmentActivity activity;
    private static LayoutInflater inflater = null;
    List<DiscussionResponse> discussionResponses;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.US);

    public DiscussionReponseAdapter(Context context, FragmentActivity activity, List<DiscussionResponse> discussionResponses, UserViewModel userViewModel, String mainUserKey){
        this.context = context;
        this.discussionResponses = discussionResponses;
        this.userViewModel = userViewModel;
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
        final DiscussionResponse response = discussionResponses.get(i);
        if (view == null) view = inflater.inflate(R.layout.listitem_comment, null);
        if (response == null){
            return view;
        }
        TextView commentTextView = view.findViewById(R.id.discussion_comment);
        commentTextView.setText(response.getAnswer());
        final TextView postDetailsView = view.findViewById(R.id.postDetails);
        if (mainUserKey.equals(response.getPostedByKey())) {
            postDetailsView.setText("Posted by you on "+dateFormat.format(response.getPostedDateTime()));
        } else {
            userViewModel.getUsers().observe(activity, new Observer<HashMap<String, User>>() {
                @Override
                public void onChanged(HashMap<String, User> userMap) {
                    String name = "...";
                    if(userMap.containsKey(response.getPostedByKey())) {
                        name = userMap.get(response.getPostedByKey()).getName();
                    }
                    postDetailsView.setText("Posted by " + name + " on " + dateFormat.format(response.getPostedDateTime()));
                }
            });
        }
        final TextView voteTextView = view.findViewById(R.id.comment_vote);
        voteTextView.setText(Integer.toString(response.getUpvoteKeys().size()));
        VoteNumberDiscussionLiveData.getVoteNumber(response).observe(this.activity, new Observer<Integer>() {
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
                            if (!response.getUpvoteKeys().contains(mainUserKey)){
                                if (response.getDownvoteKeys().contains(mainUserKey)){
                                    response.removeDownvoteKey(mainUserKey);
                                }
                                response.addUpvoteKey(mainUserKey);
                                Toast.makeText(context, "You have upvoted this answer", Toast.LENGTH_SHORT).show();
                            } else {
                                response.removeUpvoteKey(mainUserKey);
                                Toast.makeText(context, "You have undone your upvote for this answer", Toast.LENGTH_SHORT).show();
                            }
                            DiscussionResponseViewModel.updateDiscussionResponseFireStore(context, response);
                            break;
                        case R.id.down_vote:
                            if (!response.getDownvoteKeys().contains(mainUserKey)){
                                if (response.getUpvoteKeys().contains(mainUserKey)){
                                    response.removeUpvoteKey(mainUserKey);
                                }
                                response.addDownvoteKey(mainUserKey);
                                Toast.makeText(context, "You have downvoted this answer", Toast.LENGTH_SHORT).show();
                            } else {
                                response.removeDownvoteKey(mainUserKey);
                                Toast.makeText(context, "You have undone your downvote for this answer", Toast.LENGTH_SHORT).show();
                            }
                            DiscussionResponseViewModel.updateDiscussionResponseFireStore(context, response);
                            break;
                    }
                }
            };
        upVoteImageView.setOnClickListener(actionListener);
        downVoteImageView.setOnClickListener(actionListener);
        return view;
    }

    public void updateData(Context context, FragmentActivity activity, List<DiscussionResponse> discussionResponses, UserViewModel userViewModel, String mainUserKey){
        this.context = context;
        this.discussionResponses = discussionResponses;
        this.userViewModel = userViewModel;
        this.mainUserKey = mainUserKey;
        this.activity = activity;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        notifyDataSetChanged();
    }
}

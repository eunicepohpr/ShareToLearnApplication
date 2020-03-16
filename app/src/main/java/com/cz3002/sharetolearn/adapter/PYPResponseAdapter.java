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
import com.cz3002.sharetolearn.models.PYPResponse;
import com.cz3002.sharetolearn.models.User;
import com.cz3002.sharetolearn.viewModel.Pyp.PYPResponseViewModel;
import com.cz3002.sharetolearn.viewModel.Pyp.VoteNumberPypLiveData;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;

public class PYPResponseAdapter extends BaseAdapter {
    private Map<String, User> userMap;
    private String mainUserKey;
    private Context context;
    private FragmentActivity activity;
    private static LayoutInflater inflater = null;
    List<PYPResponse> pyps;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.US);

    public PYPResponseAdapter(Context context, FragmentActivity activity, List<PYPResponse> pyps, Map<String, User> userMap, String mainUserKey){
        this.context = context;
        this.pyps = pyps;
        this.userMap = userMap;
        this.mainUserKey = mainUserKey;
        this.activity = activity;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return pyps.size();
    }

    @Override
    public Object getItem(int i) {
        return pyps.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final PYPResponse pyp = pyps.get(i);
        if (view == null) view = inflater.inflate(R.layout.listitem_comment, null);
        if (pyp == null){
            return view;
        }
        TextView commentTextView = view.findViewById(R.id.discussion_comment);
        commentTextView.setText(pyp.getAnswer());
        TextView postDetailsView = view.findViewById(R.id.postDetails);
        String name;
        if (mainUserKey.equals(pyp.getPostedByKey())){
            name = "you";
        } else name = userMap.get(pyp.getPostedByKey()).getName();
        postDetailsView.setText("Posted by "+name+" on "+dateFormat.format(pyp.getPostedDateTime()));
        final TextView voteTextView = view.findViewById(R.id.comment_vote);
        voteTextView.setText(Integer.toString(pyp.getUpvoteKeys().size()));
        VoteNumberPypLiveData.getVoteNumber(pyp).observe(this.activity, new Observer<Integer>() {
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
                        if (!pyp.getUpvoteKeys().contains(mainUserKey)){
                            if (pyp.getDownvoteKeys().contains(mainUserKey)){
                                pyp.removeDownvoteKey(mainUserKey);
                            }
                            pyp.addUpvoteKey(mainUserKey);
                            Toast.makeText(context, "You have upvoted this answer", Toast.LENGTH_SHORT).show();
                        } else {
                            pyp.removeUpvoteKey(mainUserKey);
                            Toast.makeText(context, "You have undone your upvote for this answer", Toast.LENGTH_SHORT).show();
                        }
                        PYPResponseViewModel.updatePYPResponseFireStore(context, pyp);
                        break;
                    case R.id.down_vote:
                        if (!pyp.getDownvoteKeys().contains(mainUserKey)){
                            if (pyp.getUpvoteKeys().contains(mainUserKey)){
                                pyp.removeUpvoteKey(mainUserKey);
                            }
                            pyp.addDownvoteKey(mainUserKey);
                            Toast.makeText(context, "You have downvoted this answer", Toast.LENGTH_SHORT).show();
                        } else {
                            pyp.removeDownvoteKey(mainUserKey);
                            Toast.makeText(context, "You have undone your downvote for this answer", Toast.LENGTH_SHORT).show();
                        }
                        PYPResponseViewModel.updatePYPResponseFireStore(context, pyp);
                        break;
                }
            }
        };
        upVoteImageView.setOnClickListener(actionListener);
        downVoteImageView.setOnClickListener(actionListener);
        return view;
    }

    public void updateData(Context context, FragmentActivity activity, List<PYPResponse> pyps, Map<String, User> userMap, String mainUserKey){
        this.context = context;
        this.pyps = pyps;
        this.userMap = userMap;
        this.mainUserKey = mainUserKey;
        this.activity = activity;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        notifyDataSetChanged();
    }
}

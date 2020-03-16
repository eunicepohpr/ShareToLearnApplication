package com.cz3002.sharetolearn.View.Discussion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.adapter.DiscussionReponseAdapter;
import com.cz3002.sharetolearn.models.Discussion;
import com.cz3002.sharetolearn.models.DiscussionResponse;
import com.cz3002.sharetolearn.viewModel.Discussion.CommentNumberDiscussionLiveData;
import com.cz3002.sharetolearn.viewModel.Discussion.DiscussionResponseViewModel;
import com.cz3002.sharetolearn.viewModel.Discussion.DiscussionViewModel;
import com.cz3002.sharetolearn.viewModel.Discussion.LikeNumbersDiscussionLiveData;
import com.cz3002.sharetolearn.viewModel.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class DiscussionActivity extends AppCompatActivity {
    public static  Discussion discussionThread;
    String mainUserKey;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.US);
    private DiscussionResponseViewModel discussionResponseViewModel;
    private UserViewModel userViewModel;
    private DiscussionReponseAdapter discussionReponseAdapter;
    private ListView commentsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        discussionResponseViewModel = ViewModelProviders.of(this).get(DiscussionResponseViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mainUserKey = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final FragmentActivity activity = this;
        TextView topicView = findViewById(R.id.topic_title);
        topicView.setText(discussionThread.getTitle());
        TextView postDetailsView = findViewById(R.id.postDetails);
        String name;
        if (mainUserKey.equals(discussionThread.getPostedByKey())){
            name = "you";
        } else name = discussionThread.getPostedBy().getName();
        postDetailsView.setText("Posted by "+name+" on "+dateFormat.format(discussionThread.getPostedDateTime()));
        TextView questionView = findViewById(R.id.question);
        questionView.setText(discussionThread.getQuestion());
        final TextView commentNumberView = findViewById(R.id.comment_number);
        CommentNumberDiscussionLiveData.getCommentNumber(discussionThread).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer commentNumber) {
                commentNumberView.setText(Integer.toString(commentNumber));
            }
        });
        final TextView likedNumberView = findViewById(R.id.liked_number);
        LikeNumbersDiscussionLiveData.getLikeNumber(discussionThread).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer likeNumber) {
                likedNumberView.setText(likeNumber.toString());
            }
        });
        commentsListView = findViewById(R.id.discussion_comment_list);
        discussionReponseAdapter = new DiscussionReponseAdapter(getApplicationContext(), activity, new ArrayList<DiscussionResponse>(), userViewModel.getUsers().getValue(), mainUserKey);
        commentsListView.setAdapter(discussionReponseAdapter);
        discussionResponseViewModel.getDiscussionResponse().observe(this, new Observer<HashMap<String, DiscussionResponse>>() {
            @Override
            public void onChanged(@Nullable HashMap<String, DiscussionResponse> discussionResponsesMap) {
                List<DiscussionResponse>  discussionResponses = new ArrayList<>();
                for (Map.Entry<String, DiscussionResponse> item: discussionResponsesMap.entrySet()){
                    if (item.getValue().getDiscussionKey().equals(discussionThread.getKey()))
                        discussionResponses.add(item.getValue());
                }

                Collections.sort(discussionResponses, new Comparator<DiscussionResponse>() {
                    @Override
                    public int compare(DiscussionResponse t0, DiscussionResponse t1) {
                        if (t0.getUpvoteKeys().size() != t1.getUpvoteKeys().size()){
                            return t1.getUpvoteKeys().size() - t0.getUpvoteKeys().size();
                        } else if (t0.getDownvoteKeys().size() != t1.getDownvoteKeys().size()){
                            return t0.getDownvoteKeys().size() - t1.getDownvoteKeys().size();
                        } else {
                            return t1.getPostedDateTime().compareTo(t0.getPostedDateTime());
                        }
                    }
                });
                discussionReponseAdapter.updateData(getApplicationContext(), activity, discussionResponses, userViewModel.getUsers().getValue(), mainUserKey);

            }
        });

        ImageView likedImageView = findViewById(R.id.like_image);
        //initialize like image
        if (discussionThread.getLikeKeys().contains(mainUserKey)){
            likedImageView.setImageResource(R.drawable.liked);
        } else {
            likedImageView.setImageResource(R.drawable.unliked);
        }
        likedImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //liked , unliked action
                if (discussionThread.getLikeKeys().contains(mainUserKey)){
                    discussionThread.removeLikeKey(mainUserKey);
                    DiscussionViewModel.updateDiscussion(getBaseContext(), discussionThread);
                    ((ImageView) view).setImageResource(R.drawable.unliked);
                } else {
                    discussionThread.addLikeKey(mainUserKey);
                    DiscussionViewModel.updateDiscussion(getBaseContext(), discussionThread);
                    ((ImageView) view).setImageResource(R.drawable.liked);
                }
            }
        });

        final EditText commentEditText = findViewById(R.id.comment);
        ImageButton postButton = findViewById(R.id.post_anwser);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DiscussionResponse response = new DiscussionResponse();
                String answer = commentEditText.getText().toString();
                if (answer.trim().equals("")){
                    Toast.makeText(getBaseContext(), "Invalid text", Toast.LENGTH_SHORT);
                    return;
                }
                Toast.makeText(getBaseContext(), "Posting answer", Toast.LENGTH_SHORT);
                response.setAnswer(answer);
                response.setDiscussionKey(discussionThread.getKey());
                response.setPostedByKey(mainUserKey);
                response.setPostedDateTime(new Date());
                DiscussionResponseViewModel.addDiscussionResponse(getBaseContext(), discussionThread, response);
                commentEditText.setText("");
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

package com.cz3002.sharetolearn.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.adapter.DiscussionReponseAdapter;
import com.cz3002.sharetolearn.models.Discussion;
import com.cz3002.sharetolearn.models.DiscussionResponse;
import com.cz3002.sharetolearn.models.User;
import com.cz3002.sharetolearn.viewModel.CommentNumberLiveData;
import com.cz3002.sharetolearn.viewModel.DiscussionResponseViewModel;
import com.cz3002.sharetolearn.viewModel.LikeNumbersLiveData;
import com.cz3002.sharetolearn.viewModel.MainUserViewModel;
import com.cz3002.sharetolearn.viewModel.UserViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class DiscussionActivity extends AppCompatActivity {
    public static  Discussion discussionThread;
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

        final User mainUser = (User) getIntent().getExtras().get("mainUser");

        TextView topicView = findViewById(R.id.topic_title);
        topicView.setText(discussionThread.getTitle());
        TextView postDetailsView = findViewById(R.id.postDetails);
        String name;
        if (mainUser.getKey().equals(discussionThread.getPostedByKey())){
            name = "you";
        } else name = discussionThread.getPostedBy().getName();
        postDetailsView.setText("Posted by "+name+" on "+dateFormat.format(discussionThread.getPostedDateTime()));
        TextView questionView = findViewById(R.id.question);
        questionView.setText(discussionThread.getQuestion());
        final TextView commentNumberView = findViewById(R.id.comment_number);
        CommentNumberLiveData.getCommentNumber(discussionThread).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer commentNumber) {
                commentNumberView.setText(Integer.toString(commentNumber));
            }
        });
        final TextView likedNumberView = findViewById(R.id.liked_number);
        LikeNumbersLiveData.getLikeNumber(discussionThread).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer likeNumber) {
                likedNumberView.setText(likeNumber.toString());
            }
        });
        commentsListView = findViewById(R.id.discussion_comment_list);

        discussionResponseViewModel.getDiscussionResponse().observe(this, new Observer<HashMap<String, DiscussionResponse>>() {
            @Override
            public void onChanged(@Nullable HashMap<String, DiscussionResponse> discussionResponsesMap) {
                List<DiscussionResponse>  discussionResponses = new ArrayList<>();
                for (String responseKey: discussionThread.getResponseKeys()){
                    discussionResponses.add(discussionResponsesMap.get(responseKey));
                }
                Collections.sort(discussionResponses, new Comparator<DiscussionResponse>() {
                    @Override
                    public int compare(DiscussionResponse t0, DiscussionResponse t1) {
                        if (t0.getUpvoteKeys().size() != t1.getUpvoteKeys().size()){
                            return t0.getUpvoteKeys().size() - t1.getUpvoteKeys().size();
                        } else if (t0.getDownvoteKeys().size() != t1.getDownvoteKeys().size()){
                            return t1.getDownvoteKeys().size() - t0.getDownvoteKeys().size();
                        } else {
                            return t0.getPostedDateTime().compareTo(t1.getPostedDateTime());
                        }
                    }
                });
                discussionReponseAdapter = new DiscussionReponseAdapter(getApplicationContext(), discussionResponses);
                commentsListView.setAdapter(discussionReponseAdapter);
                discussionReponseAdapter.notifyDataSetChanged();
            }
        });

        commentsListView.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                DiscussionResponse comment = (DiscussionResponse) adapterView.getItemAtPosition(i);

            }
        });

        ImageView likedImageView = findViewById(R.id.like_image);
        //initialize like image
        if (discussionThread.getLikeKeys().contains(mainUser.getKey())){
            likedImageView.setImageResource(R.drawable.liked);
        } else {
            likedImageView.setImageResource(R.drawable.unliked);
        }
        likedImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //liked , unliked action
                if (discussionThread.getLikeKeys().contains(mainUser.getKey())){
                    discussionThread.removeLikeKey(mainUser.getKey());
                    LikeNumbersLiveData.setLikeNumber(discussionThread.getKey(), discussionThread.getLikeKeys().size());
                    ((ImageView) view).setImageResource(R.drawable.unliked);
                } else {
                    discussionThread.addLikeKey(mainUser.getKey());
                    LikeNumbersLiveData.setLikeNumber(discussionThread.getKey(), discussionThread.getLikeKeys().size());
                    ((ImageView) view).setImageResource(R.drawable.liked);
                }
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

package com.cz3002.sharetolearn.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.TextView;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.adapter.DiscussionAdapter;
import com.cz3002.sharetolearn.adapter.DiscussionReponseAdapter;
import com.cz3002.sharetolearn.models.Discussion;
import com.cz3002.sharetolearn.models.DiscussionResponse;
import com.cz3002.sharetolearn.viewModel.DiscussionViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

public class DiscussionActivity extends AppCompatActivity {
    public static Discussion discussionThread;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.US);
    public DiscussionViewModel discussionViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);
        discussionViewModel = ViewModelProviders.of(this).get(DiscussionViewModel.class);
        TextView topicView = findViewById(R.id.topic_title);
        topicView.setText(discussionThread.getTitle());
        TextView postDetailsView = findViewById(R.id.postDetails);
        postDetailsView.setText("Posted by "+discussionThread.getPostedBy().getName()+" on "+dateFormat.format(discussionThread.getPostedDateTime().toDate()));
        TextView questionView = findViewById(R.id.question);
        questionView.setText(discussionThread.getQuestion());
        TextView commentNumberView = findViewById(R.id.comment_number);
        commentNumberView.setText(Integer.toString(discussionThread.getResponseKeys().size()));
        TextView likedNumberView = findViewById(R.id.liked_number);
        likedNumberView.setText(Integer.toString(discussionThread.getLikeKeys().size()));
        ListView commentsListView = findViewById(R.id.discussion_comment_list);
        List<DiscussionResponse>  discussionResponses = new ArrayList<>();
        for (String responseKey: discussionThread.getResponseKeys()){
            for (DiscussionResponse response: discussionViewModel.getDiscussionResponse().getValue()){
                if (response.getKey().equals(responseKey)){
                    discussionResponses.add(response);
                }
            }
        }

        commentsListView.setAdapter(new DiscussionReponseAdapter(getApplicationContext(), discussionResponses));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

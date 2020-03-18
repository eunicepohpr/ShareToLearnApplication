package com.cz3002.sharetolearn.View.Discussion;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
import com.cz3002.sharetolearn.models.User;
import com.cz3002.sharetolearn.viewModel.Discussion.CommentNumberDiscussionLiveData;
import com.cz3002.sharetolearn.viewModel.Discussion.DiscussionResponseViewModel;
import com.cz3002.sharetolearn.viewModel.Discussion.DiscussionViewModel;
import com.cz3002.sharetolearn.viewModel.Discussion.LikeNumbersDiscussionLiveData;
import com.cz3002.sharetolearn.viewModel.MainUserViewModel;
import com.cz3002.sharetolearn.viewModel.UserViewModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

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
    private MainUserViewModel mainUserViewModel;
    private DiscussionReponseAdapter discussionReponseAdapter;
    private ListView commentsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        discussionResponseViewModel = ViewModelProviders.of(this).get(DiscussionResponseViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mainUserViewModel = ViewModelProviders.of(this).get(MainUserViewModel.class);
        mainUserKey = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final FragmentActivity activity = this;
        TextView topicView = findViewById(R.id.topic_title);
        topicView.setText(discussionThread.getTitle());
        final TextView postDetailsView = findViewById(R.id.postDetails);
        if (mainUserKey.equals(discussionThread.getPostedByKey())) {
            postDetailsView.setText("Posted by you on "+dateFormat.format(discussionThread.getPostedDateTime()));
        } else userViewModel.getUsers().observe(this, new Observer<HashMap<String, User>>() {
            @Override
            public void onChanged(HashMap<String, User> userMap) {
                String name = "...";
                if(userMap.containsKey(discussionThread.getPostedByKey())) {
                    name = userMap.get(discussionThread.getPostedByKey()).getName();
                }
                postDetailsView.setText("Posted by " + name + " on " + dateFormat.format(discussionThread.getPostedDateTime()));
            }
        });

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
        discussionReponseAdapter = new DiscussionReponseAdapter(getApplicationContext(), activity, new ArrayList<DiscussionResponse>(), userViewModel, mainUserKey);
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
                        return t0.getPostedDateTime().compareTo(t1.getPostedDateTime());
                    }
                });
                discussionReponseAdapter.updateData(getApplicationContext(), activity, discussionResponses, userViewModel, mainUserKey);

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
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                //liked , unliked action
                if (discussionThread.getLikeKeys().contains(mainUserKey)){
                    discussionThread.removeLikeKey(mainUserKey);
                    db.collection("Discussion")
                            .document(discussionThread.getKey())
                            .update("likes", FieldValue.arrayRemove(db.collection("User").document(mainUserKey)));
//                    DiscussionViewModel.updateDiscussion(getBaseContext(), discussionThread);
                    ((ImageView) view).setImageResource(R.drawable.unliked);
                } else {
                    discussionThread.addLikeKey(mainUserKey);
                    db.collection("Discussion")
                            .document(discussionThread.getKey())
                            .update("likes", FieldValue.arrayUnion(db.collection("User").document(mainUserKey)));
//                    DiscussionViewModel.updateDiscussion(getBaseContext(), discussionThread);
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
        final Button deleteButton = findViewById(R.id.delete_button);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setTitle("Confirm!");
                builder.setMessage("Do you want to delete?");
                builder.setCancelable(false);
                builder
                        .setPositiveButton(
                                "Yes",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        Toast.makeText(activity, "Deleting this post", Toast.LENGTH_SHORT);
                                        DiscussionViewModel.removeDiscussionFireStore(activity,discussionThread);
                                        activity.finish();
                                    }
                                })
                        .setNegativeButton(
                                "No",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                }
                        );
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        if (discussionThread.getPostedByKey().equals(mainUserKey)){
            deleteButton.setVisibility(View.VISIBLE);
        } else {
            deleteButton.setVisibility(View.GONE);
            mainUserViewModel.getUser().observe(this, new Observer<User>() {
                @Override
                public void onChanged(User user) {
                    if (user == null) return;
                    if ("Staff".equals(user.getDomain())) deleteButton.setVisibility(View.VISIBLE);
                }
            });
        }

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

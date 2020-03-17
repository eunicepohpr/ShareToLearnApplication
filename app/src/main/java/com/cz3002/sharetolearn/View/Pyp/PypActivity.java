package com.cz3002.sharetolearn.View.Pyp;

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
import com.cz3002.sharetolearn.adapter.PYPResponseAdapter;
import com.cz3002.sharetolearn.models.PYP;
import com.cz3002.sharetolearn.models.PYPResponse;
import com.cz3002.sharetolearn.models.User;
import com.cz3002.sharetolearn.viewModel.Pyp.CommentNumberPypLiveData;
import com.cz3002.sharetolearn.viewModel.Pyp.LikeNumberPypLiveData;
import com.cz3002.sharetolearn.viewModel.Pyp.PYPViewModel;
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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cz3002.sharetolearn.viewModel.Pyp.PYPResponseViewModel;

public class PypActivity extends AppCompatActivity {
    public static PYP pyp;
    String mainUserKey;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.US);
    private PYPResponseViewModel PYPResponseViewModel;
    private UserViewModel userViewModel;
    private PYPResponseAdapter pypResponseAdapter;
    private ListView commentsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pyp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        PYPResponseViewModel = ViewModelProviders.of(this).get(PYPResponseViewModel.class);
        userViewModel = ViewModelProviders.of(this).get(UserViewModel.class);
        mainUserKey = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final FragmentActivity activity = this;
        TextView topicView = findViewById(R.id.topic_title);
        topicView.setText(pyp.getTitle());
        final TextView postDetailsView = findViewById(R.id.postDetails);
        if (mainUserKey.equals(pyp.getPostedByKey())) {
            postDetailsView.setText("Posted by you on "+dateFormat.format(pyp.getPostedDateTime()));
        } else {
            userViewModel.getUsers().observe(this, new Observer<HashMap<String, User>>() {
                @Override
                public void onChanged(HashMap<String, User> userMap) {
                    String name = "...";
                    if(userMap.containsKey(pyp.getPostedByKey())) {
                        name = userMap.get(pyp.getPostedByKey()).getName();
                    }
                    postDetailsView.setText("Posted by " + name + " on " + dateFormat.format(pyp.getPostedDateTime()));
                }
            });
            System.out.println("pyp postedByKey: "+pyp.getPostedByKey());
            for (Map.Entry<String, User> item: userViewModel.getUsers().getValue().entrySet()){
                System.out.println(item.getKey()+": "+item.getValue());
            }
        }

        TextView questionView = findViewById(R.id.question);
        questionView.setText(pyp.getQuestion());
        final TextView commentNumberView = findViewById(R.id.comment_number);
        CommentNumberPypLiveData.getCommentNumber(pyp).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer commentNumber) {
                commentNumberView.setText(Integer.toString(commentNumber));
            }
        });
        final TextView likedNumberView = findViewById(R.id.liked_number);
        LikeNumberPypLiveData.getLikeNumber(pyp).observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer likeNumber) {
                likedNumberView.setText(likeNumber.toString());
            }
        });
        commentsListView = findViewById(R.id.pyp_comment_list);
        pypResponseAdapter = new PYPResponseAdapter(getApplicationContext(), activity, new ArrayList<PYPResponse>(), userViewModel, mainUserKey);
        commentsListView.setAdapter(pypResponseAdapter);
        PYPResponseViewModel.getPYPResponse().observe(this, new Observer<HashMap<String, PYPResponse>>() {
            @Override
            public void onChanged(@Nullable HashMap<String, PYPResponse> PYPResponsesMap) {
                List<PYPResponse> PYPResponses = new ArrayList<>();
                for (Map.Entry<String, PYPResponse> item: PYPResponsesMap.entrySet()){
                    if (item.getValue().getPypKey().equals(pyp.getKey()))
                        PYPResponses.add(item.getValue());
                }

                Collections.sort(PYPResponses, new Comparator<PYPResponse>() {
                    @Override
                    public int compare(PYPResponse t0, PYPResponse t1) {
                        if (t0.getUpvoteKeys().size() != t1.getUpvoteKeys().size()){
                            return t1.getUpvoteKeys().size() - t0.getUpvoteKeys().size();
                        } else if (t0.getDownvoteKeys().size() != t1.getDownvoteKeys().size()){
                            return t0.getDownvoteKeys().size() - t1.getDownvoteKeys().size();
                        } else {
                            return t1.getPostedDateTime().compareTo(t0.getPostedDateTime());
                        }
                    }
                });
                pypResponseAdapter.updateData(getApplicationContext(), activity, PYPResponses, userViewModel, mainUserKey);

            }
        });

        ImageView likedImageView = findViewById(R.id.like_image);
        //initialize like image
        if (pyp.getLikeKeys().contains(mainUserKey)){
            likedImageView.setImageResource(R.drawable.liked);
        } else {
            likedImageView.setImageResource(R.drawable.unliked);
        }
        likedImageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //liked , unliked action
                if (pyp.getLikeKeys().contains(mainUserKey)){
                    pyp.removeLikeKey(mainUserKey);
                    PYPViewModel.updatePYP(getBaseContext(), pyp);
                    ((ImageView) view).setImageResource(R.drawable.unliked);
                } else {
                    pyp.addLikeKey(mainUserKey);
                    PYPViewModel.updatePYP(getBaseContext(), pyp);
                    ((ImageView) view).setImageResource(R.drawable.liked);
                }
            }
        });

        final EditText commentEditText = findViewById(R.id.comment);
        ImageButton postButton = findViewById(R.id.post_anwser);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PYPResponse response = new PYPResponse();
                String answer = commentEditText.getText().toString();
                if (answer.trim().equals("")){
                    Toast.makeText(getBaseContext(), "Invalid text", Toast.LENGTH_SHORT);
                    return;
                }
                Toast.makeText(getBaseContext(), "Posting answer", Toast.LENGTH_SHORT);
                response.setAnswer(answer);
                response.setPypKey(pyp.getKey());
                response.setPostedByKey(mainUserKey);
                response.setPostedDateTime(new Date());
                PYPResponseViewModel.addPYPResponse(getBaseContext(), pyp, response);
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

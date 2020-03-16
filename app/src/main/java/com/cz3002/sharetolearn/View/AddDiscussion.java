package com.cz3002.sharetolearn.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.models.Course;
import com.cz3002.sharetolearn.models.Discussion;
import com.cz3002.sharetolearn.viewModel.Discussion.DiscussionViewModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class AddDiscussion extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_discussion);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Course course = (Course) getIntent().getExtras().get("course");
        final String mainUserKey = (String) getIntent().getExtras().get("mainUserKey");
        final Context context = this;
        Button postDiscussionButton = findViewById(R.id.postDiscussion_button);
        postDiscussionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText titleEditText = findViewById(R.id.add_title);
                EditText questionEditText = findViewById(R.id.add_question);
                String title = titleEditText.getText().toString();
                String question = questionEditText.getText().toString();
                if (title.trim().equals("") || question.trim().equals("")) {
                    Toast.makeText(context, "Invalid input", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(context, "Posting discussion topic", Toast.LENGTH_SHORT).show();
                Discussion discussion = new Discussion();
                discussion.setCourseKey(course.getKey());
                discussion.setPostedByKey(mainUserKey);
                discussion.setTitle(title);
                discussion.setQuestion(question);
                discussion.setPostedDateTime(new Date());
                DiscussionViewModel.addDiscussionFireStore(context, discussion);
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

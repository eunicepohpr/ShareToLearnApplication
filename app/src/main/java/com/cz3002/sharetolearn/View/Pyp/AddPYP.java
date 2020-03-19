package com.cz3002.sharetolearn.View.Pyp;

import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.models.Course;
import com.cz3002.sharetolearn.models.PYP;
import com.cz3002.sharetolearn.viewModel.Pyp.PYPViewModel;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class AddPYP extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_pyp);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Course course = (Course) getIntent().getExtras().get("course");
        final String mainUserKey = (String) getIntent().getExtras().get("mainUserKey");
        final Context context = this;
        final AppCompatActivity activity = this;
        Button postPypButton = findViewById(R.id.postPyp_button);
        postPypButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText titleEditText = findViewById(R.id.add_pyp_title);
                EditText questionEditText = findViewById(R.id.add_pyp_question);
                String title = titleEditText.getText().toString();
                String question = questionEditText.getText().toString();
                if (title.trim().equals("") || question.trim().equals("")) {
                    Toast.makeText(context, "Invalid input", Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(context, "Posting pyp topic", Toast.LENGTH_SHORT).show();
                PYP pyp = new PYP();
                pyp.setCourseKey(course.getKey());
                pyp.setPostedByKey(mainUserKey);
                pyp.setTitle(title);
                pyp.setQuestion(question);
                pyp.setPostedDateTime(new Date());
                PYPViewModel.addPYPFireStore(context, pyp);
                activity.finish();
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

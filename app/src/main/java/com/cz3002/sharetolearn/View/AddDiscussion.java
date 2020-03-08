package com.cz3002.sharetolearn.View;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.models.Course;
import com.google.firebase.firestore.FirebaseFirestore;

public class AddDiscussion extends AppCompatActivity {
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_discussion);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Course course = (Course) getIntent().getExtras().get("course");
        //TODO: save discussion to database

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

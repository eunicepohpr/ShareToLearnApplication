package com.cz3002.sharetolearn.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.ui.question.QuestionViewModel;

import java.util.ArrayList;

public class CourseReviewActivity extends AppCompatActivity implements Button.OnClickListener{
    private QuestionViewModel questionViewModel;
    private ListView questionListView;
    private ReviewQuestionAdapter reviewQuestionAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_review);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        questionListView = findViewById(R.id.question_list);

        questionViewModel = ViewModelProviders.of(this).get(QuestionViewModel.class);
        ArrayList<String> questionList = questionViewModel.getQuestionList().getValue();
        reviewQuestionAdapter = new ReviewQuestionAdapter(getApplicationContext(), questionList);
        questionListView.setAdapter(reviewQuestionAdapter);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        return true;
    }

    @Override
    public void onClick(View view) {
        //Save Answer to database
    }
}

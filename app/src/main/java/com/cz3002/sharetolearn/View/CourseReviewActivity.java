package com.cz3002.sharetolearn.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.adapter.ReviewQuestionAdapter;
import com.cz3002.sharetolearn.viewModel.ReviewQuestionViewModel;

import java.util.ArrayList;

public class CourseReviewActivity extends AppCompatActivity implements Button.OnClickListener{
    private ReviewQuestionViewModel questionViewModel;
    private ListView questionListView;
    private ReviewQuestionAdapter reviewQuestionAdapter;
    private Button postReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_course_review);
        setContentView(R.layout.course_review_fragment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(this);*/

        postReview = findViewById(R.id.writeReview_button);
        postReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_course_review);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        /*questionListView = findViewById(R.id.question_list);

        questionViewModel = ViewModelProviders.of(this).get(ReviewQuestionViewModel.class);
        ArrayList<String> questionList = questionViewModel.getQuestionList().getValue();
        reviewQuestionAdapter = new ReviewQuestionAdapter(getApplicationContext(), questionList);
        questionListView.setAdapter(reviewQuestionAdapter);*/
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

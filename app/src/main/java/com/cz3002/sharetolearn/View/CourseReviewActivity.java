package com.cz3002.sharetolearn.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.adapter.CourseAdapter;
import com.cz3002.sharetolearn.adapter.ReviewQuestionAdapter;
import com.cz3002.sharetolearn.models.Course;
import com.cz3002.sharetolearn.models.CourseReview;
import com.cz3002.sharetolearn.viewModel.CourseReviewViewModel;
import com.cz3002.sharetolearn.viewModel.ReviewQuestionViewModel;

import java.util.ArrayList;

public class CourseReviewActivity extends AppCompatActivity implements Button.OnClickListener{
    private ReviewQuestionViewModel questionViewModel;
    private ListView questionListView;
    private ReviewQuestionAdapter reviewQuestionAdapter;
    private Button postReview;
    private LinearLayout reviewToList;
    private CourseReviewViewModel courseReviewViewModel;
    private int size = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_course_review);
        setContentView(R.layout.course_review_fragment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        /*Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(this);*/


        courseReviewViewModel = ViewModelProviders.of(this).get(CourseReviewViewModel.class);
        courseReviewViewModel.getCourseReviewList().observe(this, new Observer<ArrayList<CourseReview>>() {
            @Override
            public void onChanged(ArrayList<CourseReview> courseReviews) {
                size = courseReviews.size();
            }
        });
/*        courseReviewViewModel.getCourseReviewList().observe(this, new Observer<ArrayList<CourseReview>>() {
            @Override
            public void onChanged(ArrayList<CourseReview> courseReviews) {
                size = courseReviews.size();
            }
        });*/

        postReview = findViewById(R.id.writeReview_button);
        postReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setContentView(R.layout.activity_course_review);
            }
        });

        reviewToList = findViewById(R.id.review_layout);
        reviewToList.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Hello",Toast.LENGTH_LONG).show();
                String msg = Integer.toString(size);
                Toast.makeText(getApplicationContext(), msg,Toast.LENGTH_SHORT).show();
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

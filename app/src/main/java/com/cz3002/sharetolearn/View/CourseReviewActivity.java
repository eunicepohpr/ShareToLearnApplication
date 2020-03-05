package com.cz3002.sharetolearn.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.models.Course;
import com.cz3002.sharetolearn.models.CourseReview;
import com.cz3002.sharetolearn.viewModel.CourseReviewViewModel;

import java.util.ArrayList;

public class CourseReviewActivity extends AppCompatActivity implements Button.OnClickListener{
/*    private ReviewQuestionViewModel questionViewModel;
    private ListView questionListView;
    private ReviewQuestionAdapter reviewQuestionAdapter;*/
    private Button postReview;
    private TextView avgRatingTV;
    private TextView coursenameTV;
    private TextView coursedescTV;
    private TextView courseAssignmentTV;
    private LinearLayout reviewToList;
    private RatingBar ratingbar;
    private ProgressBar progressBar5;
    private ProgressBar progressBar4;
    private ProgressBar progressBar3;
    private ProgressBar progressBar2;
    private ProgressBar progressBar1;
    private double count5, count4, count3, count2, count1;

    private CourseReviewViewModel courseReviewViewModel;
    private ArrayList<CourseReview> reviewList = new ArrayList<>();
    private Course selectedCourse = new Course();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_course_review);
        setContentView(R.layout.course_review_fragment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        selectedCourse = (Course) args.getSerializable("SELECTEDCOURSE");

        /*Button saveButton = findViewById(R.id.save_button);
        saveButton.setOnClickListener(this);*/

        coursenameTV = findViewById(R.id.review_overview_coursename);
        coursenameTV.setText(selectedCourse.getCourseCode()+" "+selectedCourse.getTitle());
        coursedescTV = findViewById(R.id.review_overview_coursedesc);
        coursedescTV.setText(selectedCourse.getDescription());
        courseAssignmentTV = findViewById(R.id.overview_assignmentDesc);
        courseAssignmentTV.setText(selectedCourse.getCourseAssessment());

        avgRatingTV = findViewById(R.id.review_avgRating);
        ratingbar = findViewById(R.id.ratingBar);
        progressBar5 = findViewById(R.id.progressBar5);
        progressBar4 = findViewById(R.id.progressBar4);
        progressBar3 = findViewById(R.id.progressBar3);
        progressBar2 = findViewById(R.id.progressBar2);
        progressBar1 = findViewById(R.id.progressBar1);

        courseReviewViewModel = ViewModelProviders.of(this).get(CourseReviewViewModel.class);
        courseReviewViewModel.getCourseReviewList(selectedCourse).observe(this, new Observer<ArrayList<CourseReview>>() {
            @Override
            public void onChanged(ArrayList<CourseReview> courseReviews) {
                reviewList = courseReviews;
                progressCount(courseReviews);
                //display avg
                String avg = Double.toString(getAvgRating(reviewList));
                avgRatingTV.setText(avg);
                //display rating bar
                ratingbar.setRating((float)getAvgRating(reviewList));
                //display progressbar
                progressBar5.setProgress((int)count5);
                progressBar4.setProgress((int)count4);
                progressBar3.setProgress((int)count3);
                progressBar2.setProgress((int)count2);
                progressBar1.setProgress((int)count1);
            }
        });

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
                //String msg = reviewList.get(0).getCourseKey().toString();
                //Toast.makeText(getApplicationContext(), msg,Toast.LENGTH_SHORT).show();
                Bundle args = new Bundle();
                Intent reviewlistActivity = new Intent(getApplicationContext(), CourseReviewListActivity.class);
                args.putSerializable("REVIEWLIST", reviewList);
                args.putSerializable("SELECTEDCOURSE", selectedCourse);
                reviewlistActivity.putExtra("BUNDLE", args);
                startActivity(reviewlistActivity);
            }
        });
    }

    private double getAvgRating(ArrayList<CourseReview> reviewList){
        double sum = 0;
        double avg = 0;
        int count = 0;

        for(CourseReview c : reviewList){
            sum = sum + c.getRating();
            count++;
        }
        avg = sum/count;
        return avg;
    }

    private void progressCount(ArrayList<CourseReview> reviewList){
        count5 = 0; count4 = 0; count3 = 0; count2 = 0; count1 = 0;
        int sum = 0;
        for(CourseReview c : reviewList){
            if(c.getRating() == 5){
                count5++;
            }else if(c.getRating() == 4){
                count4++;
            }else if(c.getRating() == 3){
                count3++;
            }else if(c.getRating() == 2){
                count2++;
            }else if(c.getRating() == 1){
                count1++;
            }
            sum++;
        }
        count5 = (count5/sum) *100;
        count4 = (count4/sum) *100;
        count3 = (count3/sum) *100;
        count2 = (count2/sum) *100;
        count1 = (count1/sum) *100;
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

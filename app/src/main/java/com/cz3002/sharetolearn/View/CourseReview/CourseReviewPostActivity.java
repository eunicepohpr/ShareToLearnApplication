package com.cz3002.sharetolearn.View.CourseReview;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.models.Course;
import com.cz3002.sharetolearn.models.CourseReview;
import com.cz3002.sharetolearn.viewModel.CourseReviewViewModel;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class CourseReviewPostActivity extends AppCompatActivity {

    private Course selectedCourse;
    private CourseReview courseReview;
    private RatingBar ratingBar;
    private EditText reviewDescET;
    private Button postReviewBtn;
    private CourseReviewViewModel courseReviewViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_review);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        selectedCourse = (Course) args.getSerializable("SELECTEDCOURSE");

        courseReviewViewModel = ViewModelProviders.of(this).get(CourseReviewViewModel.class);

        ratingBar = findViewById(R.id.courseRatingBar);
        reviewDescET = findViewById(R.id.postreview_desc);
        postReviewBtn = findViewById(R.id.post_button);

        postReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double rating = ratingBar.getRating();
                String description = reviewDescET.getText().toString();
                String courseKey = selectedCourse.getKey();
                String ratedByKey = "u0V6npiHU87egeDnAZzG";
                courseReview = new CourseReview(rating, ratedByKey, description, courseKey, new Date());

                courseReviewViewModel.newReview(courseReview);

                Bundle args = new Bundle();
                Intent reviewActivity = new Intent(getApplicationContext(), CourseReviewActivity.class);
                args.putSerializable("SELECTEDCOURSE", selectedCourse);
                reviewActivity.putExtra("BUNDLE", args);
                startActivity(reviewActivity);
                //Toast.makeText(getApplicationContext(), Float.toString(ratingBar.getRating()),Toast.LENGTH_SHORT).show();
            }
        });
    }
}

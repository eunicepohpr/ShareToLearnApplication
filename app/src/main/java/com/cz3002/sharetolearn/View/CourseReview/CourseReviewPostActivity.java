package com.cz3002.sharetolearn.View.CourseReview;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Date;

public class CourseReviewPostActivity extends AppCompatActivity {

    private Course selectedCourse;
    private CourseReview courseReview;
    private RatingBar ratingBar;
    private EditText reviewDescET;
    private Button postReviewBtn;
    private CourseReviewViewModel courseReviewViewModel;

    private FirebaseAuth mAuth;
    private FirebaseUser currentFbUser;
    private CourseReview userReview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_review);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        selectedCourse = (Course) args.getSerializable("SELECTEDCOURSE");
        userReview = (CourseReview) args.getSerializable("USERREVIEW");

        mAuth = FirebaseAuth.getInstance();
        currentFbUser = mAuth.getCurrentUser();

        courseReviewViewModel = ViewModelProviders.of(this).get(CourseReviewViewModel.class);

        ratingBar = findViewById(R.id.courseRatingBar);
        reviewDescET = findViewById(R.id.postreview_desc);
        postReviewBtn = findViewById(R.id.post_button);

        if(userReview != null){
            ratingBar.setRating((float)userReview.getRating());
            reviewDescET.setText(userReview.getDescription());
        }

        postReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double rating = ratingBar.getRating();
                String description = reviewDescET.getText().toString();
                String courseKey = selectedCourse.getKey();
                String ratedByKey = currentFbUser.getUid();
                courseReview = new CourseReview(rating, ratedByKey, description, courseKey, new Date());

                courseReviewViewModel.newReview(courseReview, selectedCourse, userReview);
                onBackPressed();
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}

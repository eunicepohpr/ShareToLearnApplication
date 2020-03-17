package com.cz3002.sharetolearn.View.CourseReview;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
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
import com.cz3002.sharetolearn.models.User;
import com.cz3002.sharetolearn.viewModel.CourseReviewViewModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CourseReviewActivity extends AppCompatActivity implements Button.OnClickListener {
    private Button postReview;
    private TextView avgRatingTV, coursenameTV, coursedescTV, courseAssignmentTV, noreviewsTV;
    private LinearLayout reviewToList;
    private RatingBar ratingbar;
    private ProgressBar progressBar5, progressBar4, progressBar3, progressBar2, progressBar1;
    private double count5, count4, count3, count2, count1;

    private CourseReviewViewModel courseReviewViewModel;
    private ArrayList<CourseReview> reviewList = new ArrayList<>();
    private Course selectedCourse = new Course();
    private User currentUser = new User();
    private CourseReview userReview = new CourseReview();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.course_review_fragment);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        selectedCourse = (Course) args.getSerializable("SELECTEDCOURSE");

        coursenameTV = findViewById(R.id.review_overview_coursename);
        coursenameTV.setText(selectedCourse.getCourseCode() + " " + selectedCourse.getTitle());
        coursedescTV = findViewById(R.id.review_overview_coursedesc);
        coursedescTV.setText(selectedCourse.getDescription());
        courseAssignmentTV = findViewById(R.id.overview_assignmentDesc);
        courseAssignmentTV.setText(selectedCourse.getCourseAssessment());
        noreviewsTV = findViewById(R.id.noreviews_overview);

        avgRatingTV = findViewById(R.id.review_avgRating);
        ratingbar = findViewById(R.id.ratingBar);
        progressBar5 = findViewById(R.id.progressBar5);
        progressBar4 = findViewById(R.id.progressBar4);
        progressBar3 = findViewById(R.id.progressBar3);
        progressBar2 = findViewById(R.id.progressBar2);
        progressBar1 = findViewById(R.id.progressBar1);

        postReview = findViewById(R.id.writeReview_button);
        reviewToList = findViewById(R.id.review_layout);

        courseReviewViewModel = ViewModelProviders.of(this).get(CourseReviewViewModel.class);
        courseReviewViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                currentUser = user;

                String domain = currentUser.getDomain();
                String domains = currentUser.getDomain();
                if(currentUser.getDomain().equals("Staff")){
                    postReview.setVisibility(View.INVISIBLE);
                }else{
                    postReview.setVisibility(View.VISIBLE);
                }
            }
        });
        courseReviewViewModel.getCourseReviewList(selectedCourse).observe(this, new Observer<ArrayList<CourseReview>>() {
            @Override
            public void onChanged(ArrayList<CourseReview> courseReviews) {
                reviewList = courseReviews;
                count5 = 0;
                count4 = 0;
                count3 = 0;
                count2 = 0;
                count1 = 0;
                if (!courseReviews.isEmpty()) {
                    progressCount(courseReviews);
                    //display avg
                    String avg = getAvgRating(reviewList);
                    avgRatingTV.setText(avg);
                    //display rating bar
                    ratingbar.setRating(Float.valueOf(getAvgRating(reviewList)));
                } else {
                    noreviewsTV.setVisibility(View.VISIBLE);
                }
                //display progressbar
                progressBar5.setProgress((int) count5);
                progressBar4.setProgress((int) count4);
                progressBar3.setProgress((int) count3);
                progressBar2.setProgress((int) count2);
                progressBar1.setProgress((int) count1);
            }
        });

        courseReviewViewModel.getUserReview(selectedCourse).observe(this, new Observer<CourseReview>() {
            @Override
            public void onChanged(CourseReview courseReview) {
                if (courseReview != null){
                    userReview = courseReview;
                    postReview.setText("Edit my review");
                }
            }
        });

        postReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //setContentView(R.layout.activity_course_review);
                Bundle args = new Bundle();
                Intent reviewpostActivity = new Intent(getApplicationContext(), CourseReviewPostActivity.class);
                args.putSerializable("SELECTEDCOURSE", selectedCourse);
                args.putSerializable("USERREVIEW", userReview);
                reviewpostActivity.putExtra("BUNDLE", args);
                startActivity(reviewpostActivity);
            }
        });


        reviewToList.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Bundle args = new Bundle();
                Intent reviewlistActivity = new Intent(getApplicationContext(), CourseReviewListActivity.class);
                args.putSerializable("REVIEWLIST", reviewList);
                args.putSerializable("SELECTEDCOURSE", selectedCourse);
                reviewlistActivity.putExtra("BUNDLE", args);
                startActivity(reviewlistActivity);
            }
        });
    }

    private String getAvgRating(ArrayList<CourseReview> reviewList) {
        if (reviewList.isEmpty()) {
            return "0";
        } else {
            DecimalFormat df = new DecimalFormat("0.00");
            double sum = 0;
            double avg = 0;
            int count = 0;

            for (CourseReview c : reviewList) {
                sum = sum + c.getRating();
                count++;
            }
            avg = sum / count;
            return df.format(avg);
        }
    }

    private void progressCount(ArrayList<CourseReview> reviewList) {
        count5 = 0;
        count4 = 0;
        count3 = 0;
        count2 = 0;
        count1 = 0;
        int sum = 0;
        for (CourseReview c : reviewList) {
            if (c.getRating() == 5) {
                count5++;
            } else if (c.getRating() == 4) {
                count4++;
            } else if (c.getRating() == 3) {
                count3++;
            } else if (c.getRating() == 2) {
                count2++;
            } else if (c.getRating() == 1) {
                count1++;
            }
            sum++;
        }
        count5 = (count5 / sum) * 100;
        count4 = (count4 / sum) * 100;
        count3 = (count3 / sum) * 100;
        count2 = (count2 / sum) * 100;
        count1 = (count1 / sum) * 100;
    }

    @Override
    protected void onResume() {
        super.onResume();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public void onClick(View view) {
        //Save Answer to database
    }
}

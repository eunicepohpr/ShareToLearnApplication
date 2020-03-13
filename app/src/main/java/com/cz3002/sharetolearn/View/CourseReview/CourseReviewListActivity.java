package com.cz3002.sharetolearn.View.CourseReview;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.adapter.ReviewAdapter;
import com.cz3002.sharetolearn.models.Course;
import com.cz3002.sharetolearn.models.CourseReview;
import com.cz3002.sharetolearn.viewModel.CourseReviewViewModel;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CourseReviewListActivity extends AppCompatActivity {

    private ArrayList<CourseReview> reviewList = new ArrayList<>();
    private Course selectedCourse = new Course();
    private TextView avgRatingTV;
    private TextView coursenameTV;
    private TextView noreviewsTV;
    //summary
    private RatingBar ratingbar;
    private ProgressBar progressBar5;
    private ProgressBar progressBar4;
    private ProgressBar progressBar3;
    private ProgressBar progressBar2;
    private ProgressBar progressBar1;
    private double count5, count4, count3, count2, count1;
    private ListView reviewListView;
    private CourseReviewViewModel courseReviewViewModel;
    private ReviewAdapter reviewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_reviewlist);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        reviewList = (ArrayList<CourseReview>) args.getSerializable("REVIEWLIST");
        selectedCourse = (Course) args.getSerializable("SELECTEDCOURSE");
        noreviewsTV = findViewById(R.id.noreviews_list);
        coursenameTV = findViewById(R.id.reviewlis_coursename);
        coursenameTV.setText(selectedCourse.getCourseCode() + " " + selectedCourse.getTitle());
        avgRatingTV = findViewById(R.id.reviewlist_avgRating);
        ratingbar = findViewById(R.id.list_ratingBar);
        progressBar5 = findViewById(R.id.list_progressBar5);
        progressBar4 = findViewById(R.id.list_progressBar4);
        progressBar3 = findViewById(R.id.list_progressBar3);
        progressBar2 = findViewById(R.id.list_progressBar2);
        progressBar1 = findViewById(R.id.list_progressBar1);
        reviewListView = findViewById(R.id.review_list);

        courseReviewViewModel = ViewModelProviders.of(this).get(CourseReviewViewModel.class);
        courseReviewViewModel.getCourseReviewList(selectedCourse).observe(this, new Observer<ArrayList<CourseReview>>() {
            @Override
            public void onChanged(ArrayList<CourseReview> courseReviews) {
                reviewList = courseReviews;
                count5 = 0; count4 = 0; count3 = 0; count2 = 0; count1 = 0;
                if(!courseReviews.isEmpty()){
                    progressCount(reviewList);
                    //display avg
                    String avg = getAvgRating(reviewList);
                    avgRatingTV.setText(avg);
                    //display rating bar
                    ratingbar.setRating(Float.valueOf(getAvgRating(reviewList)));
                }else{
                    noreviewsTV.setVisibility(View.VISIBLE);
                }
                //display progressbar
                progressBar5.setProgress((int) count5);
                progressBar4.setProgress((int) count4);
                progressBar3.setProgress((int) count3);
                progressBar2.setProgress((int) count2);
                progressBar1.setProgress((int) count1);

                //display list of reviews
                reviewAdapter = new ReviewAdapter(getApplicationContext(), courseReviews);
                reviewListView.setAdapter(reviewAdapter);
                reviewAdapter.notifyDataSetChanged();
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

    private String getAvgRating(ArrayList<CourseReview> reviewList) {
        if(reviewList.isEmpty()){
            return "0";
        }else{
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
}

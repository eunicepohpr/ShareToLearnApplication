package com.cz3002.sharetolearn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RatingBar;
import android.widget.TextView;
import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.models.CourseReview;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReviewAdapter extends BaseAdapter {

    private Context context;
    private List<CourseReview> courseReviews;
    private static LayoutInflater inflater = null;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    public ReviewAdapter(Context context, List<CourseReview> courseReviews) {
        this.context = context;
        this.courseReviews = courseReviews;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        return courseReviews.size();
    }

    @Override
    public Object getItem(int i) {
        return courseReviews.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        CourseReview courseReview = courseReviews.get(i);
        String username = courseReview.getRatedBy().getName();
        Date date = courseReview.getRatedDateTime();
        SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
        String formatedDate = simpleDate.format(date);
        String description = courseReview.getDescription();
        double rating = courseReview.getRating();

        if (view == null) view = inflater.inflate(R.layout.listitem_review, null);
        TextView usernameTV = view.findViewById(R.id.review_username);
        usernameTV.setText(username);
        TextView dateTV = view.findViewById(R.id.review_date);
        dateTV.setText(formatedDate);
        TextView descriptionTV = view.findViewById(R.id.review_desc);
        descriptionTV.setText(description);
        RatingBar ratingBarTV = view.findViewById(R.id.review_ratingBar);
        ratingBarTV.setRating((float) rating);

        return view;
    }
}

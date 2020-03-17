package com.cz3002.sharetolearn.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.models.CourseReview;
import com.cz3002.sharetolearn.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReviewAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<CourseReview> courseReviews;
    private static LayoutInflater inflater = null;

    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;

    public ReviewAdapter(Context context, ArrayList<CourseReview> courseReviews) {
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
        LayoutInflater inflater = LayoutInflater.from(context);
        if (view == null) view = inflater.inflate(R.layout.listitem_review, null);

        //courseReview = courseReviews.get(i);
        CourseReview courseReview = (CourseReview) getItem(i);
        User user = courseReview.getRatedBy();
        if(user != null){
            String username = user.getName();
            Date date = courseReview.getRatedDateTime();
            SimpleDateFormat simpleDate = new SimpleDateFormat("dd/MM/yyyy");
            String formatedDate = simpleDate.format(date);
            String description = courseReview.getDescription();
            double rating = courseReview.getRating();

            TextView usernameTV = view.findViewById(R.id.review_username);
            usernameTV.setText(username);
            TextView dateTV = view.findViewById(R.id.review_date);
            dateTV.setText(formatedDate);
            TextView descriptionTV = view.findViewById(R.id.review_desc);
            descriptionTV.setText(description);
            RatingBar ratingBarTV = view.findViewById(R.id.review_ratingBar);
            ratingBarTV.setRating((float) rating);
            ImageView dpIV = view.findViewById(R.id.review_userImage);
            if (user.getImageURL() != "")
                Glide.with(view.getContext()).load(user.getImageURL()).apply(RequestOptions.circleCropTransform()).into(dpIV);
            else
                Glide.with(view.getContext()).load(R.mipmap.ic_launcher_round).apply(RequestOptions.circleCropTransform()).into(dpIV);
        }

        return view;
    }
}

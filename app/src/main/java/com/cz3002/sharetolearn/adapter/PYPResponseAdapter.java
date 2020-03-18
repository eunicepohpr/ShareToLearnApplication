package com.cz3002.sharetolearn.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.models.PYPResponse;
import com.cz3002.sharetolearn.models.User;
import com.cz3002.sharetolearn.viewModel.Pyp.PYPResponseViewModel;
import com.cz3002.sharetolearn.viewModel.Pyp.VoteNumberPypLiveData;
import com.cz3002.sharetolearn.viewModel.UserViewModel;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;

public class PYPResponseAdapter extends BaseAdapter {
    private String mainUserKey;
    private Context context;
    private UserViewModel userViewModel;
    private FragmentActivity activity;
    private static LayoutInflater inflater = null;
    List<PYPResponse> pyps;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy HH:mm", Locale.US);

    public PYPResponseAdapter(Context context, FragmentActivity activity, List<PYPResponse> pyps, UserViewModel userViewModel, String mainUserKey){
        this.context = context;
        this.pyps = pyps;
        this.userViewModel = userViewModel;
        this.mainUserKey = mainUserKey;
        this.activity = activity;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return pyps.size();
    }

    @Override
    public Object getItem(int i) {
        return pyps.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        final PYPResponse response = pyps.get(i);
        if (view == null) view = inflater.inflate(R.layout.listitem_comment, null);
        if (response == null){
            return view;
        }
        TextView commentTextView = view.findViewById(R.id.comment);
        commentTextView.setText(response.getAnswer());
        final TextView postDetailsView = view.findViewById(R.id.postDetails);
        Button deleteButton = view.findViewById(R.id.delete_comment_button);
        if (mainUserKey.equals(response.getPostedByKey())) {
            postDetailsView.setText("Posted by you on "+dateFormat.format(response.getPostedDateTime()));
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                    builder.setTitle("Confirm!");
                    builder.setMessage("Do you want to delete?");
                    builder.setCancelable(false);
                    builder
                            .setPositiveButton(
                                    "Yes",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            Toast.makeText(activity, "Deleting comment", Toast.LENGTH_SHORT);
                                            PYPResponseViewModel.removePYPResponseFireStore(activity,response);
                                            FirebaseFirestore db = FirebaseFirestore.getInstance();
                                            db.collection("PYP")
                                                    .document(response.getPypKey())
                                                    .update("responses", FieldValue.arrayRemove(db.collection("PYPResponse").document(response.getKey())));
                                        }
                                    })
                            .setNegativeButton(
                                    "No",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialogInterface, int i) {
                                            dialogInterface.cancel();
                                        }
                                    }
                            );
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });
        } else {
            deleteButton.setVisibility(View.GONE);
            userViewModel.getUsers().observe(activity, new Observer<HashMap<String, User>>() {
                @Override
                public void onChanged(HashMap<String, User> userMap) {
                    String name = "...";
                    if(userMap.containsKey(response.getPostedByKey())) {
                        name = userMap.get(response.getPostedByKey()).getName();
                    }
                    postDetailsView.setText("Posted by " + name + " on " + dateFormat.format(response.getPostedDateTime()));
                }
            });
        }
        final TextView voteTextView = view.findViewById(R.id.comment_vote);
        voteTextView.setText(Integer.toString(response.getUpvoteKeys().size()));
        VoteNumberPypLiveData.getVoteNumber(response).observe(this.activity, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                voteTextView.setText(Integer.toString(integer));
            }
        });
        ImageView upVoteImageView = view.findViewById(R.id.up_vote);
        ImageView downVoteImageView = view.findViewById(R.id.down_vote);
        View.OnClickListener actionListener = new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                switch (view.getId()){
                    case R.id.up_vote:
                        if (!response.getUpvoteKeys().contains(mainUserKey)){
                            if (response.getDownvoteKeys().contains(mainUserKey)){
                                response.removeDownvoteKey(mainUserKey);
                                db.collection("PYPResponse")
                                        .document(response.getKey())
                                        .update("downvotes", FieldValue.arrayRemove(db.collection("User").document(mainUserKey)));
                            }
                            response.addUpvoteKey(mainUserKey);
                            db.collection("PYPResponse")
                                    .document(response.getKey())
                                    .update("upvotes", FieldValue.arrayUnion(db.collection("User").document(mainUserKey)));
                            Toast.makeText(context, "You have upvoted this answer", Toast.LENGTH_SHORT).show();
                        } else {
                            response.removeUpvoteKey(mainUserKey);
                            db.collection("PYPResponse")
                                    .document(response.getKey())
                                    .update("upvotes", FieldValue.arrayRemove(db.collection("User").document(mainUserKey)));
                            Toast.makeText(context, "You have undone your upvote for this answer", Toast.LENGTH_SHORT).show();
                        }
//                        PYPResponseViewModel.updatePYPResponseFireStore(context, response);
                        break;
                    case R.id.down_vote:
                        if (!response.getDownvoteKeys().contains(mainUserKey)){
                            if (response.getUpvoteKeys().contains(mainUserKey)){
                                response.removeUpvoteKey(mainUserKey);
                                db.collection("PYPResponse")
                                        .document(response.getKey())
                                        .update("upvotes", FieldValue.arrayRemove(db.collection("User").document(mainUserKey)));
                            }
                            response.addDownvoteKey(mainUserKey);
                            db.collection("PYPResponse")
                                    .document(response.getKey())
                                    .update("downvotes", FieldValue.arrayUnion(db.collection("User").document(mainUserKey)));
                            Toast.makeText(context, "You have downvoted this answer", Toast.LENGTH_SHORT).show();
                        } else {
                            response.removeDownvoteKey(mainUserKey);
                            db.collection("PYPResponse")
                                    .document(response.getKey())
                                    .update("downvotes", FieldValue.arrayRemove(db.collection("User").document(mainUserKey)));

                            Toast.makeText(context, "You have undone your downvote for this answer", Toast.LENGTH_SHORT).show();
                        }
//                        PYPResponseViewModel.updatePYPResponseFireStore(context, response);
                        break;
                }
            }
        };
        upVoteImageView.setOnClickListener(actionListener);
        downVoteImageView.setOnClickListener(actionListener);
        return view;
    }

    public void updateData(Context context, FragmentActivity activity, List<PYPResponse> pyps, UserViewModel userViewModel, String mainUserKey){
        this.context = context;
        this.pyps = pyps;
        this.userViewModel = userViewModel;
        this.mainUserKey = mainUserKey;
        this.activity = activity;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        notifyDataSetChanged();
    }
}

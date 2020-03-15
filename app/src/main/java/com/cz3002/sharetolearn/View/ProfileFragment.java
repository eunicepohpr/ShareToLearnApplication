package com.cz3002.sharetolearn.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.models.User;
import com.cz3002.sharetolearn.viewModel.ProfileViewModel;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private EditText userName, userCourse, userGradYr, userBio;
    private TextView userEmail, userCourseTitle, userGradYrTitle;
    private Button updateProfile;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.profile_fragment, container, false);

        userName = root.findViewById(R.id.profile_name);
        userEmail = root.findViewById(R.id.profile_email);
        userCourse = root.findViewById(R.id.profile_course);
        userGradYr = root.findViewById(R.id.profile_graduation);
        userBio = root.findViewById(R.id.profile_biography);
        updateProfile = root.findViewById(R.id.save_button);
        userCourseTitle = root.findViewById(R.id.profile_title_course);
        userGradYrTitle = root.findViewById(R.id.profile_title_graduation);

        profileViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                userName.setText(user.getName());
                userEmail.setText(user.getEmail());
                userCourse.setText(user.getCourseOfStudy());
                userGradYr.setText(user.getExpectedYearOfGrad());
                userBio.setText(user.getBiography());
                if (user.getDomain().equals("Staff")) {
                    userCourse.setVisibility(View.INVISIBLE);
                    userGradYr.setVisibility(View.INVISIBLE);
                    userCourseTitle.setVisibility(View.INVISIBLE);
                    userGradYrTitle.setVisibility(View.INVISIBLE);
                }
            }
        });

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileViewModel.setUser(userName.getText().toString(), userCourse.getText().toString(),
                        userGradYr.getText().toString(), userBio.getText().toString());
                Toast.makeText(getContext(), "Updated!", Toast.LENGTH_LONG).show();
            }
        });

        return root;
    }

}

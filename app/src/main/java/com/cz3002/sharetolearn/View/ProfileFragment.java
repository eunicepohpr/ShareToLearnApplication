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

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.models.User;
import com.cz3002.sharetolearn.viewModel.ProfileViewModel;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private TextView userNameTV;
    private TextView userEmailTV;
    private TextView userCourseTV;
    private TextView userGradYrTV;
    private EditText userBio;
    private Button updateProfile;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.profile_fragment, container, false);

        userNameTV = root.findViewById(R.id.profile_name);
        userEmailTV = root.findViewById(R.id.profile_email);
        userCourseTV = root.findViewById(R.id.profile_course);
        userGradYrTV = root.findViewById(R.id.profile_graduation);
        userBio = root.findViewById(R.id.profile_biography);
        updateProfile = root.findViewById(R.id.save_button);

        profileViewModel.getUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User users) {
                userNameTV.setText(users.getName());
                userEmailTV.setText(users.getEmail());
                userCourseTV.setText(users.getCourseOfStudy());
                userGradYrTV.setText(users.getExpectedYearOfGrad());
                userBio.setText(users.getBiography());
            }
        });

        updateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                profileViewModel.setUser(userBio.getText().toString());
            }
        });

        return root;
    }

}

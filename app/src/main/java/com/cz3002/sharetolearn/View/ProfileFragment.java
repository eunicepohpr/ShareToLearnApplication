package com.cz3002.sharetolearn.View;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.models.User;
import com.cz3002.sharetolearn.viewModel.ProfileViewModel;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private EditText userName, userGradYr, userBio;
    private TextView userEmail, userCourseTitle, userGradYrTitle, userBioTitle, userCourse;
    private Button updateProfile;

    private ImageView profileImage;
    StorageReference storageReference;
    private static final int IMAGE_REQUEST = 1;
    private StorageTask uploadTask;
    private Uri imageUri;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        profileViewModel = ViewModelProviders.of(this).get(ProfileViewModel.class);
        View root = inflater.inflate(R.layout.profile_fragment, container, false);

        storageReference = FirebaseStorage.getInstance().getReference();

        userName = root.findViewById(R.id.profile_name);
        userEmail = root.findViewById(R.id.profile_email);
        userCourse = root.findViewById(R.id.profile_course);
        userGradYr = root.findViewById(R.id.profile_graduation);
        userBio = root.findViewById(R.id.profile_biography);
        userBioTitle = root.findViewById(R.id.profile_title_biography);
        updateProfile = root.findViewById(R.id.save_button);
        userCourseTitle = root.findViewById(R.id.profile_title_course);
        userGradYrTitle = root.findViewById(R.id.profile_title_graduation);
        profileImage = root.findViewById(R.id.profile_photo);

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
                    userBio.setVisibility(View.INVISIBLE);
                    userBioTitle.setVisibility(View.INVISIBLE);
                }
                if (user.getImageURL() != "")
                    Glide.with(getContext()).load(user.getImageURL()).apply(RequestOptions.circleCropTransform()).into(profileImage);
                else
                    Glide.with(getContext()).load(R.mipmap.ic_launcher_round).apply(RequestOptions.circleCropTransform()).into(profileImage);
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

        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImage();
            }
        });

        return root;
    }

    private void openImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, IMAGE_REQUEST);
    }

    private String getFileExtension(Uri uri) {
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    private void uploadImage() {
        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setMessage("Uploading");
        pd.show();

        if (imageUri != null) {
            final StorageReference fileReference = storageReference.child(
                    profileViewModel.getUser().getValue().getKey() + "_" + System.currentTimeMillis() +
                            "." + getFileExtension(imageUri));
            uploadTask = fileReference.putFile(imageUri);
            uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) throw task.getException();
                    return fileReference.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        Uri downloadUri = (Uri) task.getResult();
                        String mUri = downloadUri.toString();

                        // update user db
                        profileViewModel.uploadImage(mUri);

                        Toast.makeText(getContext(), mUri, Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    } else {
                        Toast.makeText(getContext(), "Failed!", Toast.LENGTH_SHORT).show();
                        pd.dismiss();
                    }
                }
            });
        } else
            Toast.makeText(getContext(), "No image selected", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == IMAGE_REQUEST && resultCode == RESULT_OK &&
                data != null && data.getData() != null) {
            imageUri = data.getData();
            if (uploadTask != null && uploadTask != null)
                Toast.makeText(getContext(), "Upload in progress", Toast.LENGTH_SHORT).show();
            else
                uploadImage();
        }
    }
}

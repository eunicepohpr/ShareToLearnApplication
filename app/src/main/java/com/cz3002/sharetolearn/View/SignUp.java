package com.cz3002.sharetolearn.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {
    private Button signup, back;
    private String email, password, name, cos, eyog, bio;
    private EditText emailTV, pwdTV, nameTV, cosTV, eyogTV, bioTV;
    private User appUser;
    private boolean checkAuth = false;
    private ProgressDialog pd;

    public static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private FirebaseAuth mAuth;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        pd = new ProgressDialog(this);

        emailTV = findViewById(R.id.su_emailInput);
        pwdTV = findViewById(R.id.su_pwdInput);
        nameTV = findViewById(R.id.su_nameInput);
        cosTV = findViewById(R.id.su_cosInput);
        eyogTV = findViewById(R.id.su_eyogInput);
        bioTV = findViewById(R.id.su_bioInput);
        signup = (Button) findViewById(R.id.SignUpBtn);
        back = (Button) findViewById(R.id.BackBtn);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerNewUser();
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignIn.class);
                startActivity(intent);
            }
        });
    }

    private void registerNewUser() {
        pd.setMessage("Signing up...");
        pd.show();
        email = emailTV.getText().toString();
        password = pwdTV.getText().toString();
        name = nameTV.getText().toString();
        cos = cosTV.getText().toString();
        eyog = eyogTV.getText().toString();
        bio = bioTV.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }

        // check for valid email and password
        if (checkEmailForValidity(email) && password.length() >= 8 && password.length() <= 20)
            checkAuth = true;
        else {
            if (!checkEmailForValidity(email))
                Toast.makeText(getApplicationContext(), "Registration Failed. Please enter a valid email address!", Toast.LENGTH_SHORT).show();
            else if (password.length() < 8 || password.length() > 20)
                Toast.makeText(getApplicationContext(), "Registration Failed. Password should be between 8 and 20 characters!", Toast.LENGTH_SHORT).show();
        }

        // allow signup if email and password is valid
        if (checkAuth) {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getApplicationContext(), "Registration successful!", Toast.LENGTH_SHORT).show();
                                pd.dismiss();

                                FirebaseUser currentUser = mAuth.getCurrentUser();
                                if (currentUser != null) {
                                    String userId = currentUser.getUid();
                                    String email = currentUser.getEmail();
                                    appUser = new User(userId, bio, email, cos, eyog, name, "Student", "");
                                    setFireStoreData(appUser);
                                }
                                Intent intent = new Intent(SignUp.this, SignIn.class);
                                startActivity(intent);
                            } else {
                                Log.e("Signup Activity", "onComplete: Failed=" + task.getException().getMessage());
                                Toast.makeText(getApplicationContext(), "Registration failed!" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
        }

    }

    public void setFireStoreData(User user) {
        Map<String, Object> newUser = user.getFireStoreFormat();
        db.collection("User").document(user.getKey()).set(newUser);
    }

    // validate email address
    public static boolean checkEmailForValidity(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.find();
    }
}


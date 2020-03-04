package com.cz3002.sharetolearn.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.cz3002.sharetolearn.R;
import com.cz3002.sharetolearn.models.ShareToLearnApplication;

import androidx.appcompat.app.AppCompatActivity;

public class SignIn extends AppCompatActivity {
    private Button login, create;
    private EditText emailTV, pwdTV;
    private ShareToLearnApplication shareToLearnApp;
//    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        Intent intent = getIntent();
        Bundle args = intent.getBundleExtra("BUNDLE");
        shareToLearnApp = (ShareToLearnApplication) args.getSerializable("ShareToLearnApp");

//        mAuth = FirebaseAuth.getInstance();

        emailTV = findViewById(R.id.si_emailInput);
        pwdTV = findViewById(R.id.si_pwdInput);
        login = (Button) findViewById(R.id.SignInBtn);
        create = (Button) findViewById(R.id.SignUpBtn);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, MainFeed.class);
                startActivity(intent);
//                loginUserAccount();
            }
        });

        create.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SignUp.class);
                startActivity(intent);
            }
        });
    }

    /*private void loginUserAccount() {
        String email, password;
        email = emailTV.getText().toString();
        password = pwdTV.getText().toString();

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getApplicationContext(), "Please enter email!", Toast.LENGTH_LONG).show();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getApplicationContext(), "Please enter password!", Toast.LENGTH_LONG).show();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Login successful!", Toast.LENGTH_LONG).show();

                            Intent intent = new Intent(SignIn.this, NavDrawer.class);
                            startActivity(intent);
                            finish(); //to stop it from rerunning
                        } else {
                            Toast.makeText(getApplicationContext(), "Login failed! Please try again later", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }*/
}


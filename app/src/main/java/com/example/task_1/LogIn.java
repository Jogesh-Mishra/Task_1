package com.example.task_1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogIn extends AppCompatActivity {

    private View mProgressView;
    private View mLoginFormView;
    private TextView tvLoad;

    EditText etMail_Login,etPass_Login;
    Button btnLogin,btnRegister;
    TextView tvReset;


    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        FirebaseAuth fauth = FirebaseAuth.getInstance();
        FirebaseUser user = fauth.getCurrentUser();

        if(user != null){
            showProgress(true);
            tvLoad.setText("Checking User Credentials. Please Wait !");
            Intent intent = new Intent(LogIn.this,MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "User logged In Successfully !", Toast.LENGTH_SHORT).show();
            showProgress(false);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);

        etMail_Login=findViewById(R.id.etMail_Login);
        etPass_Login=findViewById(R.id.etPass_Login);
        btnRegister=findViewById(R.id.btnRegister);
        btnLogin=findViewById(R.id.btnLogin);
        tvReset=findViewById(R.id.tvReset);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etMail_Login.getText().toString().isEmpty()||etPass_Login.getText().toString().isEmpty())
                    Toast.makeText(LogIn.this, "Please Fill Required Details", Toast.LENGTH_LONG).show();
                else {
                    String email= etMail_Login.getText().toString().trim();
                    String password =etPass_Login.getText().toString().trim();

                    FirebaseAuth FAuth = FirebaseAuth.getInstance();

                    showProgress(true);
                    tvLoad.setText("Logging In. Please Wait !");

                    FAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Intent intent = new Intent(LogIn.this,MainActivity.class);
                                startActivity(intent);
                                showProgress(false);
                                Toast.makeText(LogIn.this, "User Logged In Successfully", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(LogIn.this, "Error:"+task.getException(), Toast.LENGTH_LONG).show();
                                showProgress(false);
                            }
                        }
                    });
                }

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogIn.this,Register.class);
                startActivity(intent);
            }
        });

        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etMail_Login.getText().toString().isEmpty())
                    Toast.makeText(LogIn.this, "Please Enter Your Email", Toast.LENGTH_LONG).show();
                else {
                    String resetMail = etMail_Login.getText().toString().trim();
                    FirebaseAuth Fauth= FirebaseAuth.getInstance();
                    showProgress(true);
                    tvLoad.setText("Sending Password Reset Mail.Please Wait !");
                    Fauth.sendPasswordResetEmail(resetMail).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(LogIn.this, "Password Reset Mail sent to your Email", Toast.LENGTH_LONG).show();
                                showProgress(false);
                            }
                            else{
                                Toast.makeText(LogIn.this, "Error:"+task.getException(), Toast.LENGTH_LONG).show();
                                showProgress(false);
                            }
                        }
                    });
                }

            }
        });


    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });

            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

}


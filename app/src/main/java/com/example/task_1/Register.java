package com.example.task_1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
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
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    private View mProgressView;
    private View mLoginFormView;
    private TextView tvLoad;

    EditText etName_Register,etMail_Register,etPass_Register,etConfirm_Pass;
    Button btnSubmit_Register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);
        tvLoad = findViewById(R.id.tvLoad);

        etName_Register =findViewById(R.id.etName_Register);
        etMail_Register = findViewById(R.id.etMail_Register);
        etPass_Register=findViewById(R.id.etPass_Register);
        etConfirm_Pass=findViewById(R.id.etConfirm_Pass);
        btnSubmit_Register=findViewById(R.id.btnSubmit_Register);

        btnSubmit_Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etName_Register.getText().toString().isEmpty()||etMail_Register.getText().toString().isEmpty()
                ||etPass_Register.getText().toString().isEmpty()||etConfirm_Pass.getText().toString().isEmpty())
                    Toast.makeText(Register.this, "Please Enter All Fields ", Toast.LENGTH_LONG).show();

                else{
                    if (etPass_Register.getText().toString().equals(etConfirm_Pass.getText().toString())){

                        String name =etName_Register.getText().toString().trim();
                        String mail= etMail_Register.getText().toString().trim();
                        String pass =etPass_Register.getText().toString().trim();

                        showProgress(true);
                        tvLoad.setText("Registering User. PLease Wait !");

                        FirebaseAuth fAuth = FirebaseAuth.getInstance();
                        fAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if( task.isSuccessful()){
                                    Register.this.finish();
                                    showProgress(false);
                                    Toast.makeText(Register.this, "User Reistered Successfully !", Toast.LENGTH_SHORT).show();
                                }
                                else{
                                    Toast.makeText(Register.this, "Error "+task.getException(), Toast.LENGTH_LONG).show();
                                    showProgress(false);
                                }
                            }
                        });

                    }
                    else {
                        Toast.makeText(Register.this, "Passwords Do Not Match. Please Enter Again.", Toast.LENGTH_LONG).show();
                    }
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

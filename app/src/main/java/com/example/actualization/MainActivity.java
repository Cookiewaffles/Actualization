package com.example.actualization;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //Firebase Variables
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;

    //User Interface Variables
    private Button login;
    private TextView register;
    private TextView forgotten;
    private EditText editTextEmail, editTextPassword;

    private RadioGroup radioGroup;
    private RadioButton rbClient;
    private RadioButton rbBusiness;
    private boolean isBusiness = false;

    //Shared Preferences
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //User Interface Variables Setting
        //that require On Click
        register = findViewById(R.id.txtRegister);
        register.setOnClickListener(this);

        forgotten = findViewById(R.id.txtForgotPassword);
        forgotten.setOnClickListener(this);

        login = findViewById(R.id.btnLogin);
        login.setOnClickListener(this);

        //that don't need on Click
        editTextEmail = findViewById(R.id.userName);
        editTextPassword = findViewById(R.id.Password);

        radioGroup = findViewById(R.id.radioGroup);
        rbClient = findViewById(R.id.rbClient);
        rbBusiness = findViewById(R.id.rbBusiness);

        //FireBase Setting
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        //Shared Preferences
        mPreferences = getSharedPreferences("com.example.Prefs_Guilty_Pleasures", Context.MODE_PRIVATE);
        mEditor = mPreferences.edit();
    }


    //All OnClick Events
    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.txtRegister:
                //Brings user to registration
                startActivity(new Intent(this, Register.class));
                break;
            case R.id.btnLogin:
                //Attempts to log in user
                userLogin();
                break;
            case R.id.txtForgotPassword:
                //Bring User to Reset Password Link
                startActivity(new Intent(this, ForgotenPass.class));
                break;
        }
    }


    //Verify information and Log User in
    private void userLogin() {
        //Variables set to User Input in Text Fields
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();

        //checks if Email info is entered Correctly
        if (email.isEmpty()){
            editTextEmail.setError("Email Is Empty");
            editTextEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Email Address Is Invalid");
            editTextEmail.requestFocus();
            return;
        }

        //checks if Password info is entered Correctly
        if(password.isEmpty()){
            editTextPassword.setError("Password Is Empty");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            editTextPassword.setError("Passwords are at Least 6 Characters");
            editTextPassword.requestFocus();
            return;
        }

        progressBar.setVisibility(View.VISIBLE);

        //attempts to Sign User if Previous Info Passes
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    //check if verified
                    if(user.isEmailVerified()) {
                        //redirect to correct profile
                        startActivity(new Intent(MainActivity.this, Client_ProfilePage.class));
                    }else{
                        user.sendEmailVerification();
                        Toast.makeText(MainActivity.this, "Check Email to verify account!", Toast.LENGTH_LONG).show();
                        progressBar.setVisibility(View.GONE);
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_LONG).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }
}
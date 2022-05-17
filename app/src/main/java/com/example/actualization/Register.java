package com.example.actualization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity implements View.OnClickListener  {
    private TextView RegisterUser;

    private TextView title;

    private RadioGroup radioGroup;
    private RadioButton radioButton;
    private RadioButton rbClient;
    private RadioButton rbBusiness;
    private boolean isBusiness = false;

    private EditText editTextName, editTextUsername, editTextEmail, editTextPassword;
    private ProgressBar progressBar;

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();

        RegisterUser = findViewById(R.id.registerbutton);
        RegisterUser.setOnClickListener(this);

        editTextName = findViewById(R.id.createRealName);
        editTextUsername = findViewById(R.id.createUserName);
        editTextEmail = findViewById(R.id.createEmail);
        editTextPassword = findViewById(R.id.createpassword);

        radioGroup = findViewById(R.id.radioGroup);
        rbClient = findViewById(R.id.rbClient);
        rbBusiness = findViewById(R.id.rbBusiness);


        progressBar = findViewById(R.id.progressBar2);

        title = findViewById(R.id.appTitle);
        title.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.registerbutton:
                registerUser();
                break;
            case R.id.appTitle:
                startActivity(new Intent(this, MainActivity.class));
                break;
        }
    }




    private void registerUser() {
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String name = editTextName.getText().toString().trim();
        String username = editTextUsername.getText().toString().trim();

        if(name.isEmpty()){
            editTextName.setError("Name is Missing");
            editTextName.requestFocus();
            return;
        }
        if(username.isEmpty()){
            editTextUsername.setError("UserName Is Missing");
            editTextUsername.requestFocus();
            return;
        }
        if(email.isEmpty()){
            editTextEmail.setError("Email Is Missing");
            editTextEmail.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Email Address Invalid ");
            editTextEmail.requestFocus();
            return;
        }
        if(password.isEmpty()){
            editTextPassword.setError("Password Is Missing");
            editTextPassword.requestFocus();
            return;
        }
        if(password.length() < 6){
            editTextPassword.setError("Password must be longer than 6 Characters");
            editTextPassword.requestFocus();
            return;
        }

        radioButtons();

        progressBar.setVisibility(View.VISIBLE);
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {

                    if(task.isSuccessful()) {
                        User user = new User(name, username, email, isBusiness);

                        FirebaseDatabase.getInstance().getReference("Users")
                                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user).addOnCompleteListener(task1 -> {

                            if (task1.isSuccessful()) {
                                Toast.makeText(Register.this, "Account Created!", Toast.LENGTH_SHORT).show();
                                FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("userID").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());
                                progressBar.setVisibility(View.GONE);
                                finish();
                                //back to login!
                            } else {
                                Toast.makeText(Register.this, "Register Failed. Please try again.", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility((View.GONE));
                            }
                        });
                    }else{
                        Toast.makeText(Register.this, "Registration Failed.", Toast.LENGTH_SHORT).show();
                        progressBar.setVisibility((View.GONE));
                    }
                });
    }

    public void radioButtons(){
        int radioId = radioGroup.getCheckedRadioButtonId();
        radioButton = findViewById(radioId);
        Toast.makeText(this,"" + radioButton.getId(), Toast.LENGTH_SHORT);

        if(radioId == 1){
            isBusiness = true;

        }else{
            isBusiness = false;
        }
    }
}
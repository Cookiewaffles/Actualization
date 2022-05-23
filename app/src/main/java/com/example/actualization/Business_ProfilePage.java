package com.example.actualization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Business_ProfilePage extends AppCompatActivity implements View.OnClickListener{

    private Button viewStore;
    private Button createStore;
    private Button payment;
    private Button settings;
    private Button logout;

    private FirebaseUser user;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_profile_page);


        //get user info
        user = FirebaseAuth.getInstance().getCurrentUser();
        userID = user.getUid();


        //change details on page to that of user (may need to add more info)
        final TextView UserName = findViewById(R.id.Username);


        //set on click listeners
        viewStore = findViewById(R.id.btnStoreFront);
        viewStore.setOnClickListener(this);

        createStore = findViewById(R.id.btnEditStore);
        createStore.setOnClickListener(this);

        payment = findViewById(R.id.btnPayment3);
        payment.setOnClickListener(this);

        settings = findViewById(R.id.usersettings3);
        settings.setOnClickListener(this);

        logout = findViewById(R.id.Business_Logout);
        logout.setOnClickListener(this);
    }

    //All OnClick Events
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStoreFront:
                //Brings user to View Store Front
                startActivity(new Intent(this, Business_ViewStorePage.class));
                break;
            case R.id.btnEditStore:
                //Brings user to Store Front Creation Page
                startActivity(new Intent(this, Business_CreateStoreFront.class));
                break;
            case R.id.btnPayment3:
                //Brings user to Payment Page
                startActivity(new Intent(this, Business_Payment.class));
                break;
            case R.id.usersettings3:
                //Brings user to Settings Page
                startActivity(new Intent(this, Business_Settings.class));
                break;
            case R.id.Business_Logout:
                //Log out User
                Logout();
                break;
        }
    }

    public void Logout(){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(Business_ProfilePage.this, MainActivity.class));
    }
}
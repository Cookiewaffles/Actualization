package com.example.actualization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Client_ProfilePage extends AppCompatActivity implements View.OnClickListener {
    private Button personal;
    private Button createAppt;
    private Button viewAppt;
    private Button payment;
    private Button settings;
    private Button logout;


    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile_page);

        //get user info
        user = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("Users");
        userID = user.getUid();

        //change details on page to that of user (may need to add more info)
        final TextView UserName = findViewById(R.id.Username);

        //set on click listeners
        personal = findViewById(R.id.btnFindAppt);
        personal.setOnClickListener(this);

        createAppt = findViewById(R.id.btnCreateAppointment);
        createAppt.setOnClickListener(this);

        viewAppt = findViewById(R.id.btnViewAppointments);
        viewAppt.setOnClickListener(this);

        payment = findViewById(R.id.btnPayment);
        payment.setOnClickListener(this);

        settings = findViewById(R.id.usersettings);
        settings.setOnClickListener(this);

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(this);
    }

    //All OnClick Events
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnFindAppt:
                //Brings user to View Store Front
                startActivity(new Intent(this, Client_Personal.class));
                break;
            case R.id.btnCreateAppointment:
                //Brings user to View Store Front
                startActivity(new Intent(this, Client_CreateAppointment.class));
                break;
            case R.id.btnViewAppointments:
                //Brings user to View Store Front
                startActivity(new Intent(this, Client_ViewAppointment.class));
                break;
            case R.id.btnPayment:
                //Brings user to View Store Front
                startActivity(new Intent(this, Client_Payment.class));
                break;
            case R.id.usersettings:
                //Brings user to View Store Front
                startActivity(new Intent(this, Client_Settings.class));
                break;
            case R.id.logout:
                //Logout User
                Logout();
                break;
        }
    }

    public void Logout(){
        //logout user
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(Client_ProfilePage.this, MainActivity.class));
    }
}
package com.example.actualization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Client_ProfilePage extends AppCompatActivity implements View.OnClickListener {
    private Button personal;
    private Button createAppt;
    private Button viewAppt;
    private Button payment;
    private Button settings;
    private Button logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile_page);

        personal = findViewById(R.id.btnPersonal);
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
            case R.id.btnPersonal:
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


    }
}
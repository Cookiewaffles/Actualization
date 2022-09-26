package com.example.actualization;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

public class Client_CreateAppointment extends AppCompatActivity implements View.OnClickListener {
    //Firebase Variables
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    boolean succeed = false;

    private EditText editName, editDate, editTime, editLocation, editCost;

    private Button create;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_create_appointment);


        editName = findViewById(R.id.ApptName);
        editDate = findViewById(R.id.apptDate);
        editTime = findViewById(R.id.apptTime);
        editLocation = findViewById(R.id.apptLocation);
        editCost = findViewById(R.id.apptCost);

        create = findViewById(R.id.btnCreate);
        create.setOnClickListener(this);

        cancel = findViewById(R.id.btnCancel);
        cancel.setOnClickListener(this);


        //FireBase Setting
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);
    }

    //All OnClick Events
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnCreate:
                CreateAppointment();
                break;
            case R.id.btnCancel:
                startActivity(new Intent(this, Client_ProfilePage.class));
                break;
        }
    }


    public void CreateAppointment() {
        String name = editName.getText().toString().trim();
        String date = editDate.getText().toString().trim();
        String time = editTime.getText().toString().trim();
        String location = editLocation.getText().toString().trim();
        String cost = editCost.getText().toString().trim();

        if (name.isEmpty()) {
            editName.setError("Appointment Name is Empty");
            editName.requestFocus();
            return;
        }
        if (date.isEmpty()) {
            editDate.setError("Date is Empty");
            editDate.requestFocus();
            return;
        }
        if (time.isEmpty()) {
            editTime.setError("Time is Empty");
            editTime.requestFocus();
            return;
        }
        if (location.isEmpty()) {
            editLocation.setError("Location is Empty");
            editLocation.requestFocus();
            return;
        }
        if (cost.isEmpty()) {
            editCost.setError("Cost is Empty");
            editCost.requestFocus();
            return;
        }

        Appointment appt = new Appointment(name, date, time, location, cost);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Appointments").child(name + " Appt");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(appt);
                Toast.makeText(Client_CreateAppointment.this, "data added", Toast.LENGTH_SHORT).show();
                succeed = true;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Client_CreateAppointment.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });


        if (succeed == true) {
            startActivity(new Intent(this, Client_ProfilePage.class));
        }
    }
}
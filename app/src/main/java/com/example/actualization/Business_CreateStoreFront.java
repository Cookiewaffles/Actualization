package com.example.actualization;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Business_CreateStoreFront extends AppCompatActivity implements View.OnClickListener {
    //Firebase Variables
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    private ListView lv;

    private EditText editStoreName, editDescription, editLocation, editAppt, editTime, editDate, editCost, editApptDesc;

    private Button change;
    private Button add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_create_store_front);

        editStoreName = findViewById(R.id.txtBusinessName);
        editDescription = findViewById(R.id.txtDescription);
        editLocation = findViewById(R.id.txtLocation);

        editAppt = findViewById(R.id.txtAppointment);
        editTime = findViewById(R.id.txtTimeFrame);
        editDate = findViewById(R.id.txtDate);
        editCost = findViewById(R.id.txtCost);
        editApptDesc = findViewById(R.id.txtApptDesc);

        change = findViewById(R.id.btnUpdate);
        change.setOnClickListener(this);

        add = findViewById(R.id.btnAddEvent);
        add.setOnClickListener(this);
    }

    //All OnClick Events
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUpdate:
                ChangeDescription();
                break;
            case R.id.btnAddEvent:
                AddEvent();
                break;
        }
    }



    public void ChangeDescription(){
        String storeName = editStoreName.getText().toString().trim();
        String storeDesc = editDescription.getText().toString().trim();
        String storeLocate = editLocation.getText().toString().trim();

        if (storeName.isEmpty()) {
            editStoreName.setError("Appointment Type is Empty");
            editStoreName.requestFocus();
            return;
        }
        if (storeDesc.isEmpty()) {
            editDescription.setError("Time is Empty");
            editDescription.requestFocus();
            return;
        }
        if (storeLocate.isEmpty()) {
            editLocation.setError("Time is Empty");
            editLocation.requestFocus();
            return;
        }

        StoreInfo info = new StoreInfo(storeName, storeDesc, storeLocate);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Store Info");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(info);
                Toast.makeText(Business_CreateStoreFront.this, "data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Business_CreateStoreFront.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }



    public void AddEvent(){
        String appt = editAppt.getText().toString().trim();
        String apptTime = editTime.getText().toString().trim();
        String apptDate = editDate.getText().toString().trim();
        String apptCost = editCost.getText().toString().trim();
        String apptDesc = editApptDesc.getText().toString().trim();

        if (appt.isEmpty()) {
            editAppt.setError("Appointment Type is Empty");
            editAppt.requestFocus();
            return;
        }
        if (apptTime.isEmpty()) {
            editTime.setError("Time is Empty");
            editTime.requestFocus();
            return;
        }
        if (apptDate.isEmpty()) {
            editDate.setError("Date is Empty");
            editDate.requestFocus();
            return;
        }
        if (apptCost.isEmpty()) {
            editCost.setError("Cost is Empty");
            editCost.requestFocus();
            return;
        }
        if (apptDesc.isEmpty()) {
            editApptDesc.setError("Description is Empty");
            editApptDesc.requestFocus();
            return;
        }


        StoreAppt appointment = new StoreAppt(appt, apptTime, apptDate, apptCost, apptDesc);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Store Appointments").child(appt);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                databaseReference.setValue(appointment);
                Toast.makeText(Business_CreateStoreFront.this, "data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Business_CreateStoreFront.this, "Fail to add data " + error, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
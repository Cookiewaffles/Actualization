package com.example.actualization;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Business_ViewStorePage extends AppCompatActivity {
    private TextView storeName, storeDescription;
    private Spinner spinAppt;
    List<String> list;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_view_store_page);

        storeName = findViewById(R.id.txtName);
        storeDescription = findViewById(R.id.txtDescrption);
        spinAppt = findViewById(R.id.spinAppointment);

        //Grab Store Info
        StoreInformation();

        //Grab Store Appts
        list=new ArrayList<>();




        StoreAppt();
    }


    public void StoreInformation(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Store Info");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("name").getValue().toString();
                String desc = snapshot.child("desc").getValue().toString();

                storeName.setText(name);
                storeDescription.setText(desc);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                throw error.toException();
            }
        });

    }


    public void StoreAppt(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Store Appointments");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap: snapshot.getChildren()) {
                    String spinnerApptNames = "";
                    spinnerApptNames = spinnerApptNames + snap.child("apptType").getValue().toString();
                    spinnerApptNames = spinnerApptNames + "  -  @" + snap.child("apptTime").getValue().toString();


                    list.add(spinnerApptNames);
                }

                ArrayAdapter<String> arrayAdp = new ArrayAdapter<>(Business_ViewStorePage.this, R.layout.support_simple_spinner_dropdown_item, list);
                arrayAdp.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spinAppt.setAdapter(arrayAdp);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }
}
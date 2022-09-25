package com.example.actualization;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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
    private TextView storeName, storeDescription, storeLocation, apptDescription;
    private Spinner spinAppt;
    List<String> list;
    List<String> listNames;
    ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_view_store_page);

        storeName = findViewById(R.id.txtName);
        storeLocation = findViewById(R.id.txtStoreLocation);
        storeDescription = findViewById(R.id.txtDescrption);
        spinAppt = findViewById(R.id.spinAppointment);
        apptDescription = findViewById(R.id.txtApptDescrption);

        //Grab Store Info
        StoreInformation();

        //Grab Store Appts
        list=new ArrayList<>();
        listNames=new ArrayList<>();




        StoreAppt();

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Store Appointments");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                spinAppt.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        String desc = snapshot.child(listNames.get(i)).child("apptDesc").getValue().toString();
                        desc = desc + "   ---   $" + snapshot.child(listNames.get(i)).child("apptCost").getValue().toString();

                        apptDescription.setText(desc);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void StoreInformation(){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Store Info");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String name = snapshot.child("name").getValue().toString();
                String locate = snapshot.child("location").getValue().toString();
                String desc = snapshot.child("desc").getValue().toString();

                storeName.setText(name);
                storeLocation.setText(locate);
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
        if(reference != null){
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap : snapshot.getChildren()) {
                    String spinnerApptNames = "";
                    String names = "";

                    names = names + snap.child("apptType").getValue().toString();

                    spinnerApptNames = spinnerApptNames + snap.child("apptType").getValue().toString();
                    spinnerApptNames = spinnerApptNames + "  -  @" + snap.child("apptTime").getValue().toString();
                    spinnerApptNames = spinnerApptNames + "  on  " + snap.child("apptDate").getValue().toString();

                    list.add(spinnerApptNames);
                    listNames.add(names);
                }

                ArrayAdapter<String> arrayAdp = new ArrayAdapter<>(Business_ViewStorePage.this, R.layout.support_simple_spinner_dropdown_item, list);
                arrayAdp.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
                spinAppt.setAdapter(arrayAdp);

                if (listNames.size() > 0) {

                    String desc = snapshot.child(listNames.get(0).toString()).child("apptDesc").getValue().toString();
                    desc = desc + "   ---   $" + snapshot.child(listNames.get(0).toString()).child("apptCost").getValue().toString();

                apptDescription.setText(desc);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        }
    }
}
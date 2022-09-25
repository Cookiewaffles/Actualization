package com.example.actualization;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
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

public class Client_Personal extends AppCompatActivity {
    private ListView lv;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    private Spinner spinAppt;
    List<String> list;
    List<String> apptNames;
    ArrayAdapter<String> adapter;

    TextView buisName, buisLocate, buisDesc, apptPopupDesc;
    Button buisAdd, buisCancel;
    Spinner spinner;


    String name = "";
    String locate = "";
    String desc = "";
    String apptDesc = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_personal);
        lv = (ListView) findViewById(R.id.Client_Buisness_List);;

        HashMap<String, String> nameAddresses = new HashMap<>();
        List<HashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.list_item,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.text1});


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()){
                    boolean trueOrFalse = (Boolean) snap.child("isBuisness").getValue();
                    if(trueOrFalse == true) {
                        String data = "";
                        String name = "";
                        name = snap.child("Store Info").child("name").getValue().toString();
                        data = snap.child("id").getValue().toString();


                        nameAddresses.put(name, data);
                    }
                }

                Iterator it = nameAddresses.entrySet().iterator();
                while (it.hasNext())
                {
                    HashMap<String, String> resultsMap = new HashMap<>();
                    Map.Entry pair = (Map.Entry)it.next();
                    resultsMap.put("First Line", pair.getKey().toString());
                    resultsMap.put("Second Line", pair.getValue().toString());
                    listItems.add(resultsMap);
                }

                lv.setAdapter(adapter);

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });


        //Display Popups with the correct information
       lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               dialogBuilder = new AlertDialog.Builder(Client_Personal.this);
               final View contact = getLayoutInflater().inflate(R.layout.popup, null);

               buisName = contact.findViewById(R.id.txtBuisName);
               buisLocate = contact.findViewById(R.id.txtBuisLocation);
               buisDesc = contact.findViewById(R.id.txtBuisDesc);
               spinner = contact.findViewById(R.id.spinAppointment2);
               apptPopupDesc = contact.findViewById(R.id.txtApptDescrption2);

               list = new ArrayList<>();
               apptNames = new ArrayList<>();

               buisAdd = contact.findViewById(R.id.btnAdd);
               buisCancel = contact.findViewById(R.id.btnCancel2);


               String id = listItems.get(i).get("Second Line").toString().trim();

               //retrieve data from database for all fields
               DatabaseReference mRef = FirebaseDatabase.getInstance().getReference("Users").child(id);
               mRef.addValueEventListener(new ValueEventListener() {
                   @Override
                   public void onDataChange(@NonNull DataSnapshot snapshot) {
                       //Grab Store Information ---- Name and Description
                       name = snapshot.child("Store Info").child("name").getValue().toString();
                       locate = snapshot.child("Store Info").child("location").getValue().toString();
                       desc = snapshot.child("Store Info").child("desc").getValue().toString();


                       //Grab Store Appointment Information ---- Appt Name and Times
                       for(DataSnapshot snap: snapshot.child("Store Appointments").getChildren()) {
                           String spinnerNames = "";

                           spinnerNames = spinnerNames + snap.child("apptType").getValue().toString();

                           spinnerNames = spinnerNames + "  -  @" + snap.child("apptTime").getValue().toString();

                           list.add(spinnerNames);
                       }

                       //Grab Store Appointment Information ---- Appt Name and Times
                       for(DataSnapshot snap: snapshot.child("Store Appointments").getChildren()) {
                           String listNames = "";

                           listNames = listNames + snap.child("apptType").getValue().toString();

                           apptNames.add(listNames);
                       }


                       ArrayAdapter<String> arrayAdp = new ArrayAdapter<>(Client_Personal.this, R.layout.support_simple_spinner_dropdown_item, list);
                       arrayAdp.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

                       apptDesc = snapshot.child("Store Appointments").child(apptNames.get(0)).child("apptDesc").getValue().toString();

                       dialogBuilder.setView(contact);
                       dialog = dialogBuilder.create();


                       buisName.setText(name);
                       buisLocate.setText(locate);
                       buisDesc.setText(desc);
                       spinner.setAdapter(arrayAdp);
                       apptPopupDesc.setText(apptDesc);


                       dialog.show();


                       spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                           @Override
                           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                               apptDesc = snapshot.child("Store Appointments").child(apptNames.get(i)).child("apptDesc").getValue().toString();
                               apptDesc = apptDesc + "   ---   $" + snapshot.child("Store Appointments").child(apptNames.get(i).toString()).child("apptCost").getValue().toString();

                               apptPopupDesc.setText(apptDesc);
                           }

                           @Override
                           public void onNothingSelected(AdapterView<?> adapterView){

                           }
                       });
                   }

                   @Override
                   public void onCancelled(@NonNull DatabaseError error) {
                   }
               });


               //Removes Appointment from Business store page and adds appointments to Client appointment list
               buisAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String addType = "";
                        String addLocation = "";
                        String addTime = "";
                        String addDate = "";
                        String addCost = "";
                        String addDesc = "";


                        StoreAppt addAppt = new StoreAppt(addType, addTime, addDate, addCost, addDesc);
                        dialog.dismiss();

                    }
               });

               //Dismiss The popup
               buisCancel.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       dialog.dismiss();
                   }
               });

           }
       });
    }
}
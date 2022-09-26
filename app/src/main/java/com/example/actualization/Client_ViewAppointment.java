package com.example.actualization;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

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

public class Client_ViewAppointment extends AppCompatActivity {

    private ListView lv;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    Button buisRemove, buisCancel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_view_appointment);
        lv = (ListView) findViewById(R.id.listView);;

        HashMap<String, String> nameAddresses = new HashMap<>();
        List<HashMap<String, String>> listItems = new ArrayList<>();
        SimpleAdapter adapter = new SimpleAdapter(this, listItems, R.layout.list_item,
                new String[]{"First Line", "Second Line"},
                new int[]{R.id.text1, R.id.text2});


        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("Appointments");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snap: snapshot.getChildren()){
                    String data = "";
                    String name = "";
                    name = snap.child("apptName").getValue().toString();
                    data = data + snap.child("apptDate").getValue().toString();
                    data = data + " - " + snap.child("apptTime").getValue().toString();
                    data = data + " - " + snap.child("apptLocation").getValue().toString();
                    data = data + " - $" + snap.child("apptCost").getValue().toString();


                    nameAddresses.put(name, data);
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

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                dialogBuilder = new AlertDialog.Builder(Client_ViewAppointment.this);
                final View contact = getLayoutInflater().inflate(R.layout.client_popup, null);

                buisRemove = contact.findViewById(R.id.btnRemove);
                buisCancel = contact.findViewById(R.id.btnCancel4);

                dialogBuilder.setView(contact);
                dialog = dialogBuilder.create();

                dialog.show();

                buisRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

                        reference1.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String name = listItems.get(i).get("First Line").toString();
                                snapshot.child("Appointments").child(name + " Appt").getRef().removeValue();
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                    }
                });

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
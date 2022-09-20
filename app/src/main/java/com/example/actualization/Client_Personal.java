package com.example.actualization;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
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

    Button buisAdd, buisCancel;

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


       lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               dialogBuilder = new AlertDialog.Builder(Client_Personal.this);
               final View contact = getLayoutInflater().inflate(R.layout.popup, null);

               buisAdd = contact.findViewById(R.id.btnAdd);
               buisCancel = contact.findViewById(R.id.btnCancel2);

                dialogBuilder.setView(contact);
                dialog = dialogBuilder.create();
                dialog.show();

                buisAdd.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();

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
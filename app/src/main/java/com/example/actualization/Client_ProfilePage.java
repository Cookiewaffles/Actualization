package com.example.actualization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.google.firebase.storage.FirebaseStorage;
//import com.google.firebase.storage.StorageReference;

public class Client_ProfilePage extends AppCompatActivity implements View.OnClickListener {
    private Button personal;
    private Button createAppt;
    private Button viewAppt;
    private Button payment;
    private Button settings;
    private Button logout;


   // private FirebaseUser user;
    //private FirebaseStorage storage;
   // private StorageReference storagereference;
   // private DatabaseReference reference;
  //  private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_profile_page);

        //get user info
     //   user = FirebaseAuth.getInstance().getCurrentUser();
     //   reference = FirebaseDatabase.getInstance().getReference("Users");
     //   userID = user.getUid();
    //    storage = FirebaseStorage.getInstance();
    //    storagereference = storage.getReference();

        //change details on page to that of user (may need to add more info)
        final TextView UserName = findViewById(R.id.Username);

        //set on click listeners
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
        //logout user
       // logout.setOnClickListener(v1 -> {
       //     FirebaseAuth.getInstance().signOut();
       //     startActivity(new Intent(Client_ProfilePage.this, MainActivity.class));
       //     Client_ProfilePage.this.finish();
      //  });
    }
}
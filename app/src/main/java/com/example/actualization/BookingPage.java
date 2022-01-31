package com.example.actualization;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BookingPage extends AppCompatActivity {
    //variables
    private TextView apptName;
    private TextView timeFrame;
    private TextView location;
    private TextView busiName;
    private TextView cost;
    private Button addEvent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_page);

        //setting variables
        apptName = findViewById(R.id.txtAppointment);
        timeFrame = findViewById(R.id.txtTimeFrame);
        location = findViewById(R.id.txtLocation);
        busiName = findViewById(R.id.txtBusiness);
        cost = findViewById(R.id.txtCost);
        addEvent = findViewById(R.id.btnSubmitAppt);

        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //check that all fields are filled out
                if(!apptName.getText().toString().isEmpty() && !timeFrame.getText().toString().isEmpty() && !location.getText().toString().isEmpty() &&
                        !busiName.getText().toString().isEmpty() && !cost.getText().toString().isEmpty()){
                    //create intent to put data into
                    //intent is to place is phone calendar
                    Intent intent = new Intent(Intent.ACTION_INSERT);
                    intent.setData(CalendarContract.Events.CONTENT_URI);

                    //fill intent with data
                    intent.putExtra(CalendarContract.Events.TITLE, apptName.getText().toString());
                    intent.putExtra(CalendarContract.Events.EVENT_LOCATION, location.getText().toString());
                    intent.putExtra(CalendarContract.Events.DESCRIPTION, busiName.getText().toString());
                    intent.putExtra(CalendarContract.Events.ALL_DAY, "true");
                    intent.putExtra(Intent.EXTRA_EMAIL, "test@yahoo.com");

                    //make sure their is a calendar that can handle event
                    if(intent.resolveActivity(getPackageManager()) != null){
                        startActivity(intent);
                    }else{
                        Toast.makeText(BookingPage.this, "No app available to support Action", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(BookingPage.this, "Please fill out all fields", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
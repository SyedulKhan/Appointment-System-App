package org.example.appointmentmanager_15330190;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class ViewSelectedAppointment extends Activity {
    private TextView titleOfAppointment, timeOfAppointment, detailsOfAppointment;
    private NewAppointment selectedAppointment = SearchAppointmentList.selectedAppointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_searched_appointment);
        titleOfAppointment = findViewById(R.id.txtVSearchedTitle);
        timeOfAppointment = findViewById(R.id.txtVSearchedTime);
        detailsOfAppointment = findViewById(R.id.txtVSearchedDetails);
        titleOfAppointment.setText(selectedAppointment.getTitle());
        timeOfAppointment.setText(selectedAppointment.getTime());
        detailsOfAppointment.setText(selectedAppointment.getDetails());
    }

    public void returnToMain(View v) {
        Intent intent = new Intent(ViewSelectedAppointment.this, MainActivity.class);
        startActivity(intent);
    }
}

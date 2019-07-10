package org.example.appointmentmanager_15330190;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.example.appointmentmanager_15330190.NewAppointment;

import java.util.ArrayList;
import java.util.List;

public class EditAppointment extends Activity implements View.OnClickListener {
    private EditText titleOfAppointment, timeOfAppointment, detailsOfAppointment;
    private Button updateBtn;
    private static String appointmentNumber = ViewAppointment.appointmentNumber;
    private List<NewAppointment> newAppointmentList = ViewAppointment.newAppointmentList;
    private AppointmentDatabase saveToDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_appointment);

        titleOfAppointment = findViewById(R.id.txtVEditTitleOfAppointmentInput);
        timeOfAppointment = findViewById(R.id.txtVEditTimeOfAppointmentInput);
        detailsOfAppointment = findViewById(R.id.txtVEditDetailsOfAppointmentInput);

        updateBtn = findViewById(R.id.updateBtn);
        updateBtn.setOnClickListener(this);

        saveToDatabase = new AppointmentDatabase(this, null, null, 1);
    }

    @Override
    public void onClick(View v) {
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        assert inputManager != null;
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        switch (v.getId()) {
            case R.id.updateBtn: {
                String time = timeOfAppointment.getText().toString();
                String title = titleOfAppointment.getText().toString();
                String details = detailsOfAppointment.getText().toString();

                if (TextUtils.isEmpty(time)) {
                    timeOfAppointment.setError("Please enter the time of the appointment.");
                    return;
                } else if (TextUtils.isEmpty(title)) {
                    titleOfAppointment.setError("Please enter the title of the appointment.");
                    return;
                } else if (TextUtils.isEmpty(details)) {
                    detailsOfAppointment.setError("Please enter details of the appointment.");
                    return;
                } else {
                    int i = saveToDatabase.updateAppointment(newAppointmentList.get(Integer.parseInt(appointmentNumber) - 1),
                            time, title, details);
                    if (i == 1) {
                        Toast.makeText(EditAppointment.this, "Successfully updated the appointment", Toast.LENGTH_LONG).show();
                        titleOfAppointment.setText("");
                        timeOfAppointment.setText("");
                        detailsOfAppointment.setText("");
                        finish();
                    } else if (i == -1) {
                        Toast.makeText(getBaseContext(), "Title already exists!", Toast.LENGTH_LONG).show();
                    }

                    /*saveToDatabase.updateAppointment(newAppointmentList.get(Integer.parseInt(appointmentNumber) - 1),
                            timeOfAppointment.getText().toString(), titleOfAppointment.getText().toString(), detailsOfAppointment.getText().toString());
                    Toast.makeText(getBaseContext(), "Successfully updated the appointment", Toast.LENGTH_LONG).show();
                    finish();
                    timeOfAppointment.setText("");
                    titleOfAppointment.setText("");
                    detailsOfAppointment.setText("");*/
                }
            }
            break;
        }
    }
}




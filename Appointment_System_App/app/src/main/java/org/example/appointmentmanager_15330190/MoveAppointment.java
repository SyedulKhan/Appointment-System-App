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
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;


public class MoveAppointment extends Activity implements View.OnClickListener {
    private Button updateBtn;
    private String newDate;
    private static String appointmentNumber = MoveAppointmentList.appointmentNumber;
    private CalendarView calendarView;
    private List<NewAppointment> newAppointmentList = MoveAppointmentList.newAppointmentList;
    AppointmentDatabase saveToDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_appointment);
        Intent intent = new Intent();
        newDate = intent.getStringExtra("Date");

        calendarView = findViewById(R.id.movePageCalendar);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                newDate = simpleDateFormat.format(new GregorianCalendar(year, month, dayOfMonth).getTime());
            }
        });
        updateBtn = findViewById(R.id.moveUpdateBtn);
        updateBtn.setOnClickListener(this);
        saveToDatabase = new AppointmentDatabase(this, null, null, 1);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.moveUpdateBtn: {
                saveToDatabase.moveAppointment(newAppointmentList.get(Integer.parseInt(appointmentNumber) - 1), newDate);
                Toast.makeText(this, "Appointment has moved successfully", Toast.LENGTH_SHORT).show();
                finish();
            }
            break;
        }
    }

}




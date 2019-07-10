package org.example.appointmentmanager_15330190;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.PopupWindow;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

public class MainActivity extends Activity implements View.OnClickListener {

    private Button createAppBtn,viewEditAppBtn,deleteAppBtn,moveAppBtn,searchBtn;
    private CalendarView calendarView;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        createAppBtn = findViewById(R.id.createAppointmentBtn);
        createAppBtn.setOnClickListener(this);

        viewEditAppBtn = findViewById(R.id.viewEditAppointmentBtn);
        viewEditAppBtn.setOnClickListener(this);

        deleteAppBtn = findViewById(R.id.deleteAppointmentBtn);
        deleteAppBtn.setOnClickListener(this);

        moveAppBtn = findViewById(R.id.moveAppointmentBtn);
        moveAppBtn.setOnClickListener(this);

        searchBtn = findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(this);

        calendarView = findViewById(R.id.calendar);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                date = simpleDateFormat.format(new GregorianCalendar(year, month, dayOfMonth).getTime());
            }
        });

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        date = simpleDateFormat.format(new Date(calendarView.getDate()));
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.createAppointmentBtn :{
                Intent intent = new Intent(MainActivity.this , CreateAppointment.class);
                intent.putExtra("Date" , date );
                startActivity(intent);
                break;
            }
            case R.id.deleteAppointmentBtn : {
                Intent intent = new Intent(MainActivity.this , DeleteAppointment.class);
                intent.putExtra("Date" , date );
                startActivity(intent);
                break;
            }
            case R.id.viewEditAppointmentBtn:{
                Intent intent = new Intent(MainActivity.this , ViewAppointment.class);
                intent.putExtra("Date" , date );
                startActivity(intent);
                break;
            }
            case R.id.moveAppointmentBtn :{
                Intent intent = new Intent(MainActivity.this , MoveAppointmentList.class);
                intent.putExtra("Date" , date );
                startActivity(intent);
                break;
            }
            case R.id.searchBtn :{
                Intent intent = new Intent(MainActivity.this , SearchAppointmentList.class);
                startActivity(intent);
                break;

            }
        }
    }
}

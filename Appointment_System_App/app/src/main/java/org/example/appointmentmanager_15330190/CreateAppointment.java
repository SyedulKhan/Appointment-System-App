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
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;


public class CreateAppointment extends Activity {
    private EditText titleOfAppointment, timeOfAppointment, detailsOfAppointment;
    private EditText thesaurusInput;
    private Button saveBtn, thesaurusBtn, thesaurusBtn2;
    private String date;
    public static String userWordInput;
    private AppointmentDatabase saveToDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_appointment);

        Intent intent = getIntent();
        date = intent.getStringExtra("Date");

        //initializing the edit text boxes
        titleOfAppointment = findViewById(R.id.txtVTitleOfAppointmentInput);
        timeOfAppointment = findViewById(R.id.txtVTimeOfAppointmentInput);
        detailsOfAppointment = findViewById(R.id.txtVDetailsOfAppointmentInput);
        
        saveBtn = findViewById(R.id.saveBtn);
        thesaurusInput = findViewById(R.id.thesaurusWordInput);
        thesaurusBtn = findViewById(R.id.thesaurusBtn);
        thesaurusBtn2 = findViewById(R.id.thesaurusBtn2);
        saveToDatabase = new AppointmentDatabase(this, null, null, 1);
    }

    public void saveUserInputs(View view) {
        String time = timeOfAppointment.getText().toString();
        String title = titleOfAppointment.getText().toString();
        String details = detailsOfAppointment.getText().toString();

        if (TextUtils.isEmpty(time)) {
            timeOfAppointment.setError("Please set a time for the appointment.");
        } else if (TextUtils.isEmpty(title)) {
            titleOfAppointment.setError("Please set a title for the appointment.");
        } else if (TextUtils.isEmpty(details)) {
            detailsOfAppointment.setError("Please set a details for the appointment.");
        } else {
            NewAppointment appointment = new NewAppointment(date, time, title, details);
            int i = saveToDatabase.createAppointment(appointment);
            if (i == 1) {
                errorMessage("Appointment " + title + " on " + date + " was created successfully.");
                titleOfAppointment.setText("");
                timeOfAppointment.setText("");
                detailsOfAppointment.setText("");
            } else if (i == -1) {
                errorMessage("Appointment " + title + " already exists, please choose a different event title");
            }
        }
    }

    public void thesaurusOutput(View view) {
        userWordInput = thesaurusInput.getText().toString();

        if (userWordInput.equals(null) || userWordInput.equals("")) {
            errorMessage("Please Enter a Word before pressing the thesaurus button");
        } else {
            Intent intent = new Intent(CreateAppointment.this, SynonymsList.class);
            startActivity(intent);
        }
        thesaurusInput.setText("");
    }

    public void thesaurusOutput2(View view) {
        int startSelection = detailsOfAppointment.getSelectionStart();
        int endSelection = detailsOfAppointment.getSelectionEnd();
        String selectedText = detailsOfAppointment.getText().toString().substring(startSelection, endSelection);
        userWordInput = selectedText;
        if (userWordInput.equals(null) || userWordInput.equals("")){
            errorMessage("Please Enter a Word in the text box to highlight before pressing the thesaurus button");
        } else {
            Toast.makeText(getBaseContext(), "You selected the word \"" + userWordInput + "\"", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(CreateAppointment.this, SynonymsList.class);
            startActivity(intent);
        }
    }

    public void errorMessage(String error) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(error);
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {
                dialog.dismiss();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}




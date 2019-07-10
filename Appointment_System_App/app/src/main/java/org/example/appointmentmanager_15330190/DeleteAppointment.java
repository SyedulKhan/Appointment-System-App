package org.example.appointmentmanager_15330190;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class DeleteAppointment extends Activity {
    private Button deleteBtn;
    private Button deleteAllBtn;
    private EditText appointmentNumberInput;
    private AlertDialog deleteOneAppointment;
    private AlertDialog deleteAllAppointments;

    private List<NewAppointment> newAppointmentList;
    private ArrayList<String> arrayList;
    private ArrayAdapter adapter;
    private ListView listView;
    private String selectedDate;
    AppointmentDatabase removeFromDatabase;

    private String appointmentNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_appointment);

        Intent intent = getIntent();
        selectedDate = intent.getStringExtra("Date");

        appointmentNumberInput = findViewById(R.id.delPageAppointmentNumberInput);
        deleteBtn = findViewById(R.id.deleteBtn);
        deleteAllBtn = findViewById(R.id.deleteAllBtn);

        removeFromDatabase = new AppointmentDatabase(this, null, null, 1);
        newAppointmentList = removeFromDatabase.displayAppointments(selectedDate);
        arrayList = new ArrayList<>();

        for (int j = 0; j < newAppointmentList.size(); j++) {

            arrayList.add(j + 1 + ". " + newAppointmentList.get(j).getTime() + " " + newAppointmentList.get(j).getTitle());
        }

        adapter = new ArrayAdapter<>(this, R.layout.activity_list_view, arrayList);

        listView = findViewById(R.id.listOfAppointments);
        listView.setAdapter(adapter);
    }

    public void deleteSelectedAppointment(View view) {
        appointmentNumber = appointmentNumberInput.getText().toString();
        if (appointmentNumber.equals(null) || appointmentNumber.equals("")) {
            Toast.makeText(getBaseContext(), "Please enter a valid appointment number", Toast.LENGTH_SHORT).show();
        } else {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Would you like to delete event: " + newAppointmentList.get(Integer.parseInt(appointmentNumber) - 1).getTitle() + "?");
                builder.setCancelable(false);
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int i) {
                        Toast.makeText(getBaseContext(), "Deleted the " + newAppointmentList.get(Integer.parseInt(appointmentNumber) - 1).getTitle() +
                                " appointment.", Toast.LENGTH_SHORT).show();
                        removeFromDatabase.deleteAppointment(selectedDate, newAppointmentList.get(Integer.parseInt(appointmentNumber) - 1).getTitle());
                        finish();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                deleteOneAppointment = builder.show();
                appointmentNumberInput.setText("");
            } catch (IndexOutOfBoundsException e) {
                appointmentNumberInput.setText("");
                Toast.makeText(getBaseContext(), "There's no appointment numbered " + appointmentNumber + ". Please try again with a valid number.",
                        Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                appointmentNumberInput.setText("");
                Toast.makeText(getBaseContext(), "Invalid input. Please try again with a valid number.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void deleteAllAppointments(View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Would you like to delete all appointments?");
        builder.setCancelable(false);
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int i) {
                Toast.makeText(getBaseContext(), "Deleted All Appointments", Toast.LENGTH_SHORT).show();
                removeFromDatabase.deleteAllAppointments(selectedDate);
                finish();
            }
        });
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        deleteAllAppointments = builder.show();
    }
}
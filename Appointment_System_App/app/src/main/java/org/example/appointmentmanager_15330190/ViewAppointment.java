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


public class ViewAppointment extends Activity {
    private EditText appointmentNumberInput;
    private Button editBtn;
    public static List<NewAppointment> newAppointmentList;
    public static ArrayList<String> arrayList;
    public static ListView listView;
    private AlertDialog editAppointmentMsg;
    private AppointmentDatabase viewDatabase;
    public static String appointmentNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_appointment);

        Intent intent = getIntent();
        String selectedDate = intent.getStringExtra("Date");

        appointmentNumberInput = findViewById(R.id.viewPageAppointmentNumberInput);
        editBtn = findViewById(R.id.editBtn);

        viewDatabase = new AppointmentDatabase(this, null, null, 1);
        newAppointmentList = viewDatabase.displayAppointments(selectedDate);
        arrayList = new ArrayList<>();

        for (int j = 0; j < newAppointmentList.size(); j++) {

            arrayList.add(j + 1 + ". " + newAppointmentList.get(j).getTime() + " " + newAppointmentList.get(j).getTitle());
        }

        ArrayAdapter adapter = new ArrayAdapter<>(this, R.layout.activity_list_view, arrayList);
        listView = findViewById(R.id.listOfAppointments);
        listView.setAdapter(adapter);
    }

    public void editAppointment(View view) {
        appointmentNumber = appointmentNumberInput.getText().toString();
        if (appointmentNumber.equals(null) || appointmentNumber.equals("")) {
            Toast.makeText(getBaseContext(), "Please enter a valid appointment number", Toast.LENGTH_SHORT).show();
        } else {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Would you like to edit event: " +
                        newAppointmentList.get(Integer.parseInt(appointmentNumber) - 1).getTitle() + "?");
                builder.setCancelable(false);
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int i) {
                        Intent intent = new Intent(ViewAppointment.this , EditAppointment.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                editAppointmentMsg = builder.show();
                appointmentNumberInput.setText("");
            } catch (IndexOutOfBoundsException e) {
                appointmentNumberInput.setText("");
                Toast.makeText(getBaseContext(), "There's no appointment numbered " + appointmentNumber +
                        ". Please try again with a valid number.", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                appointmentNumberInput.setText("");
                Toast.makeText(getBaseContext(), "Invalid input. Please try again with a valid number.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}
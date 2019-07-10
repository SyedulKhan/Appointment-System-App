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


public class MoveAppointmentList extends Activity implements View.OnClickListener {
    private Button moveBtn;
    private EditText appointmentNumberInput;
    private AlertDialog moveAppointmentMsg;

    public static List<NewAppointment> newAppointmentList;
    public static ArrayList<String> arrayList;
    private ArrayAdapter adapter;
    public static ListView listView;
    private String selectedDate;
    AppointmentDatabase viewDatabase;

    public static String appointmentNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_move_appointment_list);

        Intent intent = getIntent();
        selectedDate = intent.getStringExtra("Date");

        appointmentNumberInput = findViewById(R.id.movePageAppointmentNumberInput);
        moveBtn = findViewById(R.id.moveBtn);
        moveBtn.setOnClickListener(this);

        viewDatabase = new AppointmentDatabase(this, null, null, 1);
        newAppointmentList = viewDatabase.displayAppointments(selectedDate);
        arrayList = new ArrayList<>();

        for (int j = 0; j < newAppointmentList.size(); j++) {
            arrayList.add(j + 1 + ". " + newAppointmentList.get(j).getTime() + " " + newAppointmentList.get(j).getTitle());
        }

        adapter = new ArrayAdapter<>(this, R.layout.activity_list_view, arrayList);

        listView = findViewById(R.id.movePageListOfAppointments);
        listView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        appointmentNumber = appointmentNumberInput.getText().toString();
        if (appointmentNumber.equals(null) || appointmentNumber.equals("")) {
            Toast.makeText(getBaseContext(), "Please enter a valid appointment number", Toast.LENGTH_SHORT).show();
        } else {
            try {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Would you like to move event: " +
                        newAppointmentList.get(Integer.parseInt(appointmentNumber) - 1).getTitle() + "?");
                builder.setCancelable(false);
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int i) {
                        Intent intent = new Intent(getBaseContext() , MoveAppointment.class);
                        startActivity(intent);
                        finish();
                    }
                });
                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
                moveAppointmentMsg = builder.show();
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
package org.example.appointmentmanager_15330190;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class SearchAppointmentList extends Activity implements View.OnClickListener {

    private EditText searchInput;
    private AppointmentAdaptor aptAdaptor;
    private ListView listOfAppointments;
    private List<NewAppointment> resultingListOfAppointments; //list to store all resulting appointments
    public static NewAppointment selectedAppointment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_appointment_list);
        Button searchBtn = findViewById(R.id.searchBtn);
        searchBtn.setOnClickListener(this);
        searchInput = findViewById(R.id.searchPageAppointmentWordInputInput);

        AppointmentDatabase viewDatabase = new AppointmentDatabase(this, null,
                null, 1); // instance of database
        resultingListOfAppointments = viewDatabase.displayAppointments(); // store all the appointments in a list
                                                                          // from database
        listOfAppointments = findViewById(R.id.searchPageListOfAppointments);
        listOfAppointments.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedAppointment = aptAdaptor.getItem(position);
                Intent intent = new Intent(SearchAppointmentList.this, ViewSelectedAppointment.class);
                startActivity(intent);
            }
        });

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.searchBtn: {
                try {
                    if (searchInput.getText().toString().equals("") || searchInput.getText().toString().equals(null)) {
                        searchInput.setError("Please input a Keyword");
                    } else {
                        List<NewAppointment> listOfMatchingAppointments = new ArrayList<>();
                        String searchKeyword = searchInput.getText().toString();
                        for (NewAppointment appointment : resultingListOfAppointments) { //check if arrayList contains any of the keywords
                            if (appointment.getTitle().contains(searchKeyword)|| appointment.getDetails().contains(searchKeyword) ||
                            appointment.getTitle().toLowerCase().contains(searchKeyword)|| appointment.getDetails().toLowerCase().contains(searchKeyword)) {
                                listOfMatchingAppointments.add(appointment);
                            }
                        }
                        aptAdaptor = new AppointmentAdaptor(getBaseContext(), -1, listOfMatchingAppointments);
                        listOfAppointments.setAdapter(aptAdaptor);
                        if (listOfMatchingAppointments.size() == 0) {
                            Toast.makeText(getBaseContext(), "Couldn't find any matches", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), "Couldn't find any matches", Toast.LENGTH_SHORT).show();
                }
                searchInput.setText("");
                break;
            }
        }
    }
}

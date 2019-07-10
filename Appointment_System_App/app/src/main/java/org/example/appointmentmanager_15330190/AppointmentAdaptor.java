package org.example.appointmentmanager_15330190;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class AppointmentAdaptor extends ArrayAdapter<NewAppointment> {

    private TextView titleOfAppointment , detailsOfAppointment;

    public AppointmentAdaptor(Context context, int textViewResourceId, List<NewAppointment> appointments) {
        super(context, textViewResourceId, appointments);
    }

    /**
     *
     * This method will create the rows for the list view
     */
    @Override
    public View getView(int pos, View convertView, ViewGroup parent){
        RelativeLayout row = (RelativeLayout)convertView;
        if(null == row){
            //No recycled View, we have to inflate one.
            LayoutInflater inflater = (LayoutInflater)parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            assert inflater != null;
            row = (RelativeLayout)inflater.inflate(R.layout.activity_appointment_list_row, null);
        }
        titleOfAppointment = row.findViewById(R.id.txtVTitleForAdapter);
        detailsOfAppointment = row.findViewById(R.id.txtVDetailsForAdapter);
        titleOfAppointment.setText(getItem(pos).getTitle());
        detailsOfAppointment.setText(getItem(pos).getDetails());
        return row;
    }
}

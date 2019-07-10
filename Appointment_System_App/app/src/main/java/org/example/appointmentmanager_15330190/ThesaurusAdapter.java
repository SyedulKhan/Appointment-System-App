package org.example.appointmentmanager_15330190;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class ThesaurusAdapter extends ArrayAdapter<Synonym> {
    private TextView txtVCategory , txtVSynonyms;

    public ThesaurusAdapter(Context context, int textViewResourceId, List<Synonym> synonyms) {
        super(context, textViewResourceId, synonyms);
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
            row = (RelativeLayout)inflater.inflate(R.layout.activity_thesaurus_list_row, null);
        }
        txtVCategory = row.findViewById(R.id.txtVCategory);
        txtVSynonyms = row.findViewById(R.id.txtVSynonym);
        txtVCategory.setText(getItem(pos).getCategory());
        txtVSynonyms.setText(getItem(pos).getSynonyms());
        return row;
    }
}

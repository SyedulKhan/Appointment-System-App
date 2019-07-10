package org.example.appointmentmanager_15330190;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;


public class SynonymsList extends Activity {
    private String userWordInput = CreateAppointment.userWordInput;
    ThesaurusAdapter thesaurusAdapter;
    ListView synonymsList;
    public static final String THESAURUS_KEY = "4rV8gGnX9kzHcSMjLgE4"; //constant for the thesaurus service key

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_synonyms_list);

        synonymsList = findViewById(R.id.synonymsList);

        if (isNetworkAvailable()) { //check if there is internet connection
            SitesDownloadTask download = new SitesDownloadTask();
            download.execute();
        } else {
            Toast.makeText(getBaseContext(), "No internet Connection. Please connect " +
                    "your device to the internet and try again", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        assert connectivityManager != null;
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private class SitesDownloadTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                String lang = "en_US";
                DownloadFromUrl("http://thesaurus.altervista.org/thesaurus/v1?word=" + userWordInput +
                                "&language=" + lang + "&%20key=" + THESAURUS_KEY + "&output=xml",
                        openFileOutput("synonyms.xml", Context.MODE_PRIVATE)); //Download the file
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) { //set adapter to the synonymsList.
            thesaurusAdapter = new ThesaurusAdapter(SynonymsList.this, R.layout.activity_thesaurus_list_row,
                    ThesaurusXMLPullParser.getSynonymsFromFile(SynonymsList.this));
            synonymsList.setAdapter(thesaurusAdapter);
        }
    }

    /**
     * This method will try to download the xml form the internet
     *
     * @param URL URL to make the request
     * @param fos The name to store the XML file
     */
    public static void DownloadFromUrl(String URL, FileOutputStream fos) {
        try {
            java.net.URL url = new URL(URL);                 // URL of the file
            URLConnection connection = url.openConnection(); // Open a connection to URL
            InputStream is = connection.getInputStream();    // input stream that'll read from the connection
            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedOutputStream bos = new BufferedOutputStream(fos); //buffer output stream that'll write to the xml file

            byte data[] = new byte[1024];            // write to the file while reading
            int count;
            while ((count = bis.read(data)) != -1) { // loop and read the current chunk
                bos.write(data, 0, count);      // write this chunk
            }
            bos.flush();
            bos.close();
        } catch (IOException ignored) {
        }
    }

}




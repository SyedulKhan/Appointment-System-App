package org.example.appointmentmanager_15330190;

import android.content.Context;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class ThesaurusXMLPullParser {

    static final String KEY_LIST = "list"; //start tag of a synonym in the XML
    static final String KEY_CATEGORY = "category";
    static final String KEY_SYNONYMS = "synonyms";

    public static List<Synonym> getSynonymsFromFile(Context ctx) {

        List<Synonym> synonymList;     // List of synonyms that we will return
        synonymList = new ArrayList<>();

        Synonym currentSynonym = null; // temp holder for the current synonym while parsing
        String currentText = "";       // temp holder for the current text value while parsing

        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            FileInputStream fileInputStream = ctx.openFileInput("synonyms.xml");
            BufferedReader reader = new BufferedReader(new InputStreamReader(fileInputStream));
            xmlPullParser.setInput(reader);                   // point the parser to the file.

            int eventType = xmlPullParser.getEventType();     // get initial eventType

            while (eventType != XmlPullParser.END_DOCUMENT) { // Loop through pull events until we reach END_DOCUMENT
                String tagName = xmlPullParser.getName();     // Get the current tag
                switch (eventType) {
                    case XmlPullParser.START_TAG:
                        if (tagName.equalsIgnoreCase(KEY_LIST)) {
                            currentSynonym = new Synonym();   // If we are starting a new <list> block
                        }                                     // we need a new synonym object to represent it
                        break;

                    case XmlPullParser.TEXT:
                        currentText = xmlPullParser.getText(); // grab the current text so we can use it in END_TAG event
                        break;

                    case XmlPullParser.END_TAG:
                        if (tagName.equalsIgnoreCase(KEY_LIST)) {
                            synonymList.add(currentSynonym); // if </list> then we are done with current synonym
                                                             // add it to the list.
                        } else if (tagName.equalsIgnoreCase(KEY_CATEGORY)) {
                            currentSynonym.setCategory(currentText); // if </category> , set the category for the current object
                        } else if (tagName.equalsIgnoreCase(KEY_SYNONYMS)) {
                            currentSynonym.setSynonyms(currentText); // if </synonyms> , set the synonym for the current object
                        }
                        break;
                    default:
                        break;
                }
                eventType = xmlPullParser.next(); //move on to next iteration
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return synonymList; // return the list of synonyms
    }
}

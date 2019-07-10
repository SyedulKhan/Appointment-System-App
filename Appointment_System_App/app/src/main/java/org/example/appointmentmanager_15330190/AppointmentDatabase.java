package org.example.appointmentmanager_15330190;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class AppointmentDatabase extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "Appointment_Database.db";
    public static final String TABLE_APPOINTMENTS = "appointments";

    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_DATE = "date";
    public static final String COLUMN_TIME = "time";
    public static final String COLUMN_TITLE = "title";
    public static final String COLUMN_DETAILS = "details";

    public AppointmentDatabase(Context ctx, String name, SQLiteDatabase.CursorFactory cursorFactory, int version){
        super(ctx, DATABASE_NAME, cursorFactory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createAppointmentQuery = " CREATE TABLE " + TABLE_APPOINTMENTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                COLUMN_DATE + " TEXT ," +
                COLUMN_TIME + " DATETIME ," +
                COLUMN_TITLE + " TEXT ," +
                COLUMN_DETAILS + " TEXT " +
                ");";

        db.execSQL(createAppointmentQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_APPOINTMENTS);
        onCreate(db);
    }

    public int createAppointment(NewAppointment newAppointment){
        SQLiteDatabase database = getWritableDatabase();

        String sqlQuery = " SELECT * FROM " + TABLE_APPOINTMENTS + " WHERE "
                + COLUMN_DATE + "=\'" + newAppointment.getDate() + "\'" + " AND " + COLUMN_TITLE
                + "=\'" + newAppointment.getTitle() + "\';";

        Cursor cursor = database.rawQuery(sqlQuery,null);

        if (cursor == null || !cursor.moveToFirst()) {

            ContentValues contentValues = new ContentValues();

            //stores the values
            contentValues.put(COLUMN_DATE , newAppointment.getDate());
            contentValues.put(COLUMN_TIME , newAppointment.getTime());
            contentValues.put(COLUMN_TITLE , newAppointment.getTitle());
            contentValues.put(COLUMN_DETAILS , newAppointment.getDetails());


            //insert the values into the database
            database.insert(TABLE_APPOINTMENTS , null , contentValues);
            database.close(); //restores the memory
            assert cursor != null;
            cursor.close();
            return 1;
        } else {
            return -1;
        }
    }

    public void deleteAppointment(String date, String title){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_APPOINTMENTS + " WHERE " + COLUMN_DATE + "=\'" + date + "\'"
                + " AND " + COLUMN_TITLE + "=\'" + title + "\';");
        db.close();
    }

    public void deleteAllAppointments(String date){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_APPOINTMENTS + " WHERE " + COLUMN_DATE + "=\'" + date + "\';");
        db.close();
    }

    public int updateAppointment(NewAppointment appointment , String time , String title , String details){

        SQLiteDatabase db = getWritableDatabase();

        String sql = " SELECT * FROM " + TABLE_APPOINTMENTS + " WHERE "
                + COLUMN_DATE + "=\'" + appointment.getDate() + "\'" + " AND " +
                COLUMN_TITLE + "=\'" + appointment.getTitle() + "\';";

        Cursor cursor = db.rawQuery(sql,null);

        if (cursor == null || !cursor.moveToFirst()) {
            return -1;
        } else {

            ContentValues contentValues = new ContentValues();

            //stores the values to be updated
            contentValues.put(COLUMN_TIME , time);
            contentValues.put(COLUMN_TITLE , title );
            contentValues.put(COLUMN_DETAILS , details);

            //insert the values into the database
            db.update(TABLE_APPOINTMENTS, contentValues , COLUMN_DATE + "='" + appointment.getDate() + "'" + " AND " +
                    COLUMN_TITLE + "='" + appointment.getTitle() + "'" , null);
            db.close(); //restores the memory
            cursor.close();
            return 1;

        }
    }

    public int moveAppointment(NewAppointment appointment, String date){
        SQLiteDatabase db = getWritableDatabase();

        String sql = " SELECT * FROM " + TABLE_APPOINTMENTS + " WHERE "
                + COLUMN_DATE + "=\'" + appointment.getDate() + "\'" + " AND " +
                COLUMN_TITLE + "=\'" + appointment.getTitle() + "\';";

        Cursor cursor = db.rawQuery(sql,null);

        if (cursor == null || !cursor.moveToFirst()) {
            return -1;
        } else {

            ContentValues contentValues = new ContentValues();
            //stores the values to be updated
            contentValues.put(COLUMN_DATE , date);
            //insert the values into the database
            db.update(TABLE_APPOINTMENTS, contentValues , COLUMN_DATE + "='" + appointment.getDate() + "'" + " AND " +
                    COLUMN_TITLE + "='" + appointment.getTitle() + "'" , null);
            db.close(); //restores the memory
            cursor.close();
            return 1;
        }
    }


    public List<NewAppointment> displayAppointments(String date){

        List<NewAppointment> list = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_APPOINTMENTS + " WHERE " + COLUMN_DATE + "=\'" + date + "\'"
                + " ORDER BY " + COLUMN_TIME + " ASC";

        //Cursor exposes results from a query on a SQLiteDatabase
        Cursor cursor = db.rawQuery(query, null);
        //move the cursor to the first row of the results
        cursor.moveToFirst();

        //See if there are anymore results
        while (!cursor.isAfterLast()) {

            if (cursor.getString(cursor.getColumnIndex("title")) != null) {

                NewAppointment appointment = new NewAppointment(cursor.getString(cursor.getColumnIndex("date")) ,
                        cursor.getString(cursor.getColumnIndex("time")) ,
                        cursor.getString(cursor.getColumnIndex("title")) ,
                        cursor.getString(cursor.getColumnIndex("details")) );
                list.add(appointment);
            }
            cursor.moveToNext();
        }
        db.close();
        return list;
    }

    public List<NewAppointment> displayAppointments(){
        List<NewAppointment> list = new ArrayList<>();

        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_APPOINTMENTS;

        //Cursor exposes results from a query on a SQLiteDatabase
        Cursor cursor = db.rawQuery(query, null);
        //move the cursor to the first row of the results
        cursor.moveToFirst();

        //See if there are anymore results
        while (!cursor.isAfterLast()) {

            if (cursor.getString(cursor.getColumnIndex("title")) != null) {

                NewAppointment appointment = new NewAppointment(cursor.getString(cursor.getColumnIndex("date")) ,
                        cursor.getString(cursor.getColumnIndex("time")) ,
                        cursor.getString(cursor.getColumnIndex("title")) ,
                        cursor.getString(cursor.getColumnIndex("details")) );
                list.add(appointment);
            }
            cursor.moveToNext();
        }
        db.close();
        return list;
    }
}

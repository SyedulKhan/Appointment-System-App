package org.example.appointmentmanager_15330190;

public class NewAppointment {
    private String date;
    private String time;
    private String title;
    private String details;

    public NewAppointment(String date, String time, String title, String details) {
        this.date = date;
        this.time = time;
        this.title = title;
        this.details = details;
    }

    public void setDate(String date){
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public void setDetails(String details){
        this.details = details;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }

}

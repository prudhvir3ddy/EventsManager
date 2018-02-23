package com.eventsmanager.root.eventsmanager.Models;

/**
 * Created by root on 20/1/18.
 */

public class Participate_Model {
String eventname;
    String time;
    String eventdesc;

    public String getEventdesc() {
        return eventdesc;
    }

    public void setEventdesc(String eventdesc) {
        this.eventdesc = eventdesc;
    }



    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}

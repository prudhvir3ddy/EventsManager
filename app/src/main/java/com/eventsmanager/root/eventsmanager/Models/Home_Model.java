package com.eventsmanager.root.eventsmanager.Models;

/**
 * Created by root on 13/1/18.
 */

public class Home_Model {

    private String eventurl;
    private String eventname;
    private String eventdesc;
    private String timestamp;
    private String eventimg;

    public String getLikedevents() {
        return likedevents;
    }

    public void setLikedevents(String likedevents) {
        this.likedevents = likedevents;
    }

    private String likedevents;

    public String getEventurl() {
        return eventurl;
    }

    public void setEventurl(String eventurl) {
        this.eventurl = eventurl;
    }

    public String getEventname() {
        return eventname;
    }

    public void setEventname(String eventname) {
        this.eventname = eventname;
    }

    public String getEventdesc() {
        return eventdesc;
    }

    public void setEventdesc(String eventdesc) {
        this.eventdesc = eventdesc;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getEventimg() {
        return eventimg;
    }

    public void setEventimg(String eventimg) {
        this.eventimg = eventimg;
    }



}

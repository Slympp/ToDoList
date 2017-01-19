package com.todolist.slymp.todolist;

import android.graphics.Color;
import android.util.Log;

import java.util.Date;

import static android.content.ContentValues.TAG;
import static android.graphics.Color.rgb;

public class Item {

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDue_time() {
        return due_time;
    }

    public void setDue_time(String due_time) {
        this.due_time = due_time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTimestamp_dt() {
        return timestamp_dt;
    }

    public void setTimestamp_dt(String timestamp_dt) {
        this.timestamp_dt = timestamp_dt;
    }

    public int getPriorityColor() {
        return priorityColor;
    }

    public void setPriorityColor(int priorityColor) {
        this.priorityColor = priorityColor;
    }

    public static int[] getColorCode() {
        return colorCode;
    }

    public static int   getColorCodeById(int id) {
        return colorCode[id];
    }

    private String title;
    private String description;
    private String due_time;
    private String status;
    private String timestamp_dt;
    private int    id;
    private int    priorityColor;

    public static final int     PRIO_DATE = 0;
    public static final int     PRIO_HOUR = 1;
    public static final int     PRIO_TOOLATE = 2;

    public static final int[]   colorCode = { rgb(34,139,34), Color.rgb(255,140,0), Color.RED};

    public Item(String title, String description, String due_time, int id, String status) {
        super();
        this.title = title;
        this.description = description;
        this.due_time = due_time;
        this.id = id;
        this.status = status;
        this.timestamp_dt = due_time;
        this.priorityColor = 0;


        /*Log.d(TAG, "Item" + id +
                "\nTitle: " + title +
                "\nDesc: " + description +
                "\nDue_time: " + due_time +
                "\nTimestamp: " + timestamp_dt +
                "\nStatus: " + status);*/
    }
}

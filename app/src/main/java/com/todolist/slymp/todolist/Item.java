package com.todolist.slymp.todolist;

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

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }

    private String title;
    private String description;
    private String due_time;
    private String status;
    private int    id;

    public Item(String title, String description, String due_time, int id, String status) {
        super();
        this.title = title;
        this.description = description;
        this.due_time = due_time;
        this.id = id;
        this.status = status;
    }
}

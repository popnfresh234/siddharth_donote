package com.example.sthakrey.donote.data;

import java.util.Map;

/**
 * Created by sthakrey on 6/14/2017.
 */

public class Todo {

    String title, priority, label, color, description;
    Map<String, Task> task ;

    public Todo()
    {

    }

   public Todo(String title, String label, String color, Map<String, Task> taskList)
{
    this.title = title;
    this.label = label;
    this.color = color;
    this.task = taskList;

}


    public String getLabel() {
        return label;
    }

    public String getPriority() {
        return priority;
    }

    public String getTitle() {
        return title;
    }

    public String getColor() {
        return color;
    }

    public Map<String, Task> getTaskList() {
        return task;
    }

    public void setTaskList(Map<String, Task> taskList) {
        this.task = taskList;
    }
}

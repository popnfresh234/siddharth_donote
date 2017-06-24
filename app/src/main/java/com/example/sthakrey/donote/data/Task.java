package com.example.sthakrey.donote.data;

/**
 * Created by sthakrey on 6/14/2017.
 */

public class Task {
    public String task;
    public boolean isChecked ;

    public Task(String task, boolean isChecked)
    {
        this.task = task;
        this.isChecked = isChecked;
    }

    public Task()
    {

    }
    public String getTask() {
        return task;
    }

    public void setIsChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public boolean getIsChecked() {
        return isChecked;
    }

    public void setTask(String task) {
        this.task = task;
    }
}

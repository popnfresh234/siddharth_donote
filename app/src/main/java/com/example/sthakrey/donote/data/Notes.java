package com.example.sthakrey.donote.data;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by sthakrey on 6/10/2017.
 */
@IgnoreExtraProperties
public class Notes {
    private String title, description, label, color;

    public Notes() {
    }

    public Notes(String title, String description, String label, String color) {
        this.title = title;
        this.description = description;
        this.label = label;
                this.color = color;

    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String name) {
        this.title = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;

    }
    public String getLabel()
    {
        return this.label;
    }

    public void setLabel(String label)
    {
        this.label = label;
    }
    public String getColor()
    {
        return this.color;
    }

    public void setColor(String color)
    {
        this.color = color;
    }
}

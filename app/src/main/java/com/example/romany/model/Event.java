package com.example.romany.model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;

import java.util.List;

public class Event  {
    String id;
    String Name;
    String TimeOfLoc;
    String Location;
    List<Person> people;
    String date;


    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }



    public Event() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTimeOfLoc() {
        return TimeOfLoc;
    }

    public void setTimeOfLoc(String timeOfLoc) {
        TimeOfLoc = timeOfLoc;
    }

    public String getLocation() {
        return Location;
    }

    public void setLocation(String location) {
        Location = location;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }


}

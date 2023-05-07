package com.example.mylostandfoundapplication;

public class Item {

    //Instance variables
    public Integer ID;
    public String Status;
    public String Name;
    public String Phone;
    public String Description;
    public String Date;
    public String Location;

    //Constructor for initialization
    public Item(Integer ID, String Status, String Name, String Phone, String Description, String Date, String Location){
        this.ID=ID;
        this.Status=Status;
        this.Name=Name;
        this.Phone=Phone;
        this.Description=Description;
        this.Date=Date;
        this.Location=Location;
    }
}

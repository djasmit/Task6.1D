package com.example.task61d.data;

import android.util.Log;

import com.example.task61d.model.Truck;

import java.util.ArrayList;
import java.util.List;

public class TruckList {
    private static final String[] typeString = {"Truck", "Van", "Refrigerated Truck", "Mini-Truck"};
    private static final Truck truck1 = new Truck(typeString[0], "0434444444");
    private static final Truck truck2 = new Truck(typeString[0], "0443334434");
    private static final Truck truck3 = new Truck(typeString[2], "0456789123");
    private static final Truck truck4 = new Truck(typeString[0], "0400000040");

    public static List<Truck> Trucks = new ArrayList<>();

    //add all trucks to list
    public void addTrucks(){
        Trucks.add(truck1);
        Trucks.add(truck2);
        Trucks.add(truck3);
        Trucks.add(truck4);

        Log.e("Truck type", "Trucks added");
    }
}

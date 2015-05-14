/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ada.gen;

import java.util.ArrayList;

/**
 *
 * @author Marek
 */
public class Backpack {

    private double capacity;
    private ArrayList<Item> items;
    private static Backpack instance;

    private Backpack(double capacity, ArrayList<Item> items) {
        this.capacity = capacity;
        this.items = items;
    }
    
    public static Backpack getInstance(double capacity, ArrayList<Item> items) {
        if(instance==null) {
           instance = new Backpack(capacity,items); 
        }
        return instance;
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity = capacity;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public double getSize(int index) {
        return items.get(index).getSize();
    }

    public double getValue(int index) {
        return items.get(index).getValue();
    }

    @Override
    public String toString() {
        int i = 0;
        String toPrint = "\n";
        for (Item el : items) {
            toPrint += "Package " + ++i + ": " + el.toString() + "\n";
        }
        return "Backpack{" + "capacity=" + capacity + toPrint + "}\n";
    }
}

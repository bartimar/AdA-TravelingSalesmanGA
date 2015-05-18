/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ada.gen2;

import java.util.ArrayList;

/**
 *
 * @author Marek
 */
public class CityMap {

    private int total_cities;
    private ArrayList<ArrayList<Integer>> cities;
    private static CityMap instance;
    private int filled;

    private CityMap(int total_cities) {
        this.total_cities = total_cities;
        cities = new ArrayList<ArrayList<Integer>>();
        for (int i = 0; i < total_cities - 1; i++) {
            cities.add(new ArrayList<Integer>());
        }
        filled = 0;
    }

    public void add(int distance) {
        cities.get(filled).add(distance);
        if (cities.get(filled).size() + filled >= total_cities - 1) {
            filled++;
        }
    }

    public int getDist(int c1, int c2) { //E.g. 3,4 -> 2,0
        if (c1 > c2) {
            int tmp = c1;
            c1 = c2;
            c2 = tmp;
        }
        if(c1 <= 0 || c2<= 0) System.out.println("c1=" + c1 + " c2=" + c2);
        return cities.get(c1 - 1).get(c2 - 1 - c1);
    }

    public static CityMap getInstance(int total_cities) {
        if (instance == null) {
            instance = new CityMap(total_cities);
        }
        return instance;
    }

    public int getTotal_cities() {
        return total_cities;
    }

    public void setTotal_cities(int total_cities) {
        this.total_cities = total_cities;
    }

    public ArrayList<ArrayList<Integer>> getCities() {
        return cities;
    }

    public void setCities(ArrayList<ArrayList<Integer>> cities) {
        this.cities = cities;
    }

    @Override
    public String toString() {
        return "CityMap{" + "total_cities=" + total_cities + ", cities=" + cities + '}';
    }

}

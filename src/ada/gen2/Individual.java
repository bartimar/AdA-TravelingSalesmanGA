/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ada.gen2;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;

/**
 *
 * @author Marek
 */
public class Individual {

    private ArrayList<Integer> gene;
    private int fitness = 0;
    private CityMap cm;

    public Individual(ArrayList<Integer> gene, CityMap cm) {
        this.gene = gene;
        this.cm = cm;
    }

    public Individual(CityMap cm) {
        this.gene = new ArrayList();
        this.cm = cm;
    }

    public ArrayList<Integer> getGene() {
        return gene;
    }

    public void setGene(ArrayList<Integer> gene) {
        this.gene = gene;
    }

    public CityMap getCityMap() {
        return cm;
    }

    @Override
    public String toString() {
        String ret = "Individual{" + "gene=";
        for (int i = 0; i < gene.size(); i++) {
            ret += gene.get(i) + " ";
        }
        ret += "}";
        return ret;
    }

    public String print() {
        String ret = "";
        for (int i = 0; i < gene.size(); i++) {
            ret += gene.get(i);
            if (i + 1 < gene.size()) {
                ret += " ";
            }
        }
        return ret.trim();
    }

    public int getFitness() {
        if (fitness == 0) {
            fitness = Fitness.getFitness(this, cm);
        }
        return fitness;
    }

    public int getItem(int index) {
        return gene.get(index);
    }

    public void setItem(int index, int val) {
        if (index >= gene.size()) {
            gene.add(val);
        } else {
            gene.set(index, val);
        }
    }

    public void addItem(int val) {
        gene.add(val);
    }
// tohle bude spatne

    public void regenerate() {
        Random randomGenerator = new Random();
        for (;;) {
            int idx = randomGenerator.nextInt(gene.size());
            //System.out.println("idx=" + idx);
            if (gene.get(idx) == 0) {
                gene.set(idx, 1);
                break;
            }
        }

    }

    public int length() {
        return gene.size();
    }
}

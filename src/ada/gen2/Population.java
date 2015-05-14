/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ada.gen2;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.Collections;

/**
 *
 * @author Marek
 */
class Population {

    public ArrayList<Individual> individuals;
    private CityMap cm;
    private boolean sorted;

    public Population(ArrayList<Individual> individuals) {
        this.individuals = individuals;
        sorted=false;
    }

    public Population() {
        this.individuals = new ArrayList();
        sorted=false;
    }

    public Population(Population old) {
        individuals = new ArrayList();
        cm = old.getCityMap();
        sorted=false;
//        for (int i = 0; i < old.size(); i++) {
//            BitSet bs = new BitSet(cm.getItems().size());
//            Individual inew = new Individual(bs, cm.getItems().size());
//            inew.setCityMap(cm);
//            individuals.add(inew);
//        }
    }
    
    public boolean isSorted() {
        return sorted;
    }

    public void saveIndividual(int idx, Individual in) {
        individuals.set(idx, in);
    }
    public void saveIndividual(Individual in) {
        individuals.add(in);
    }

    public Individual getIndividual(int idx) {
        return individuals.get(idx);
    }

    public int size() {
        return individuals.size();
    }

    public void setCityMap(CityMap cm) {
        this.cm = cm;
    }

    public CityMap getCityMap() {
        return this.cm;
    }

    public ArrayList<Individual> getIndividuals() {
        return individuals;
    }

    public void setIndividuals(ArrayList<Individual> individuals) {
        this.individuals = individuals;
    }

    public Individual getFittest() {
        Individual ret = individuals.get(0);
        //System.out.println("indiv[0]" + ret);
        if(cm == null) {
            System.out.println("CityMap is null. Terminate");
            System.exit(2);
        }
        for (Individual in : individuals) {
            double retfit = Fitness.getFitness(ret, cm), infit = Fitness.getFitness(in, cm);
            if (retfit < infit) {
                ret = in;
            }
        }
        return ret;
    }

    public Population getElite(int count) {
        Population ret = new Population();
        //System.out.println("indiv[0]" + ret);
        sort();
        for (int i = 0; i < count ; i++) {
           ret.saveIndividual(individuals.get(i));
        }
        ret.setCityMap(cm);
        return ret;
    }
    
    public void sort() {
        Collections.sort(individuals, new IndividualComparator());
        sorted=true;
    }

    @Override
    public String toString() {
        int i = 0;
        String toPrint = "";
        for (Individual el : individuals) {
            toPrint += "Human " + ++i + ": ";
            toPrint += el.toString();
            toPrint += "\n";
        }
        return "Population{" + "size=" + size() + "\n" + toPrint + '}';
    }
    
    public String print() {
        int i = 0;
        String toPrint = "";
        for (Individual el : individuals) {
            toPrint += el.print();
            toPrint += "\n";
        }
        return toPrint;
    }

}

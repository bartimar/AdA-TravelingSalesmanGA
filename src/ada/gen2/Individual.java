/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ada.gen;

import java.util.BitSet;
import java.util.Random;

/**
 *
 * @author Marek
 */
public class Individual {

    private BitSet gene;
    private int geneLength;
    private double fitness = 0;
    private Backpack bp;

    public Individual(BitSet gene, int length, Backpack bp) {
        this.gene = gene;
        this.geneLength = length;
        this.bp = bp;
    }

    public Individual(int length, Backpack bp) {
        this.gene = new BitSet(length);
        this.geneLength = length;
        this.bp = bp;
    }

    public BitSet getGene() {
        return gene;
    }

    public void setGene(BitSet gene) {
        this.gene = gene;
    }

    public Backpack getBackpack() {
        return bp;
    }

    @Override
    public String toString() {
        String ret = "Individual{" + "gene=";
        for (int i = 0; i < geneLength; i++) {
            ret += gene.get(i) ? "1" : "0";
        }
        ret += "}";
        return ret;
    }
    
     public String print() {
        String ret = "";
        for (int i = 0; i < geneLength; i++) {
            ret += gene.get(i) ? "1" : "0";
            if(i+1<geneLength) ret+= " ";
        }
        return ret;
    }

    public double getFitness() {
        if (fitness == 0) {
            fitness = Fitness.getFitness(this, bp);
        }
        return fitness;
    }

    public boolean getItem(int index) {
        return gene.get(index);
    }

    public void setItem(int index, boolean val) {
        gene.set(index, val);
    }

    public void regenerate() {
        Random randomGenerator = new Random();
        for (;;) {
            int idx = randomGenerator.nextInt(geneLength);
            //System.out.println("idx=" + idx);
            if (gene.get(idx)) {
                gene.clear(idx);
                break;
            }
        }

    }

    public int length() {
        return geneLength;
    }
}

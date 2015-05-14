/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ada.gen;

/**
 *
 * @author Marek
 */
public class Fitness {

    private Backpack bp;

    public Fitness(Backpack bp) {
        this.bp = bp;
    }

    public static double getFitness(Individual gene, Backpack bp) {
        double fitness = 0, total_size = 0;
        if(bp == null ) {
            System.out.println("Backpack is NULL. Terminate 3");
        System.exit(3);
    }
                    
        for (int i = 0; i < gene.length(); i++) {
            if (gene.getItem(i)) {
                if (total_size + bp.getSize(i) > bp.getCapacity()) {
                    //System.out.println("Backpack is full, size=" + total_size + ", value=" + fitness);
                    gene.regenerate();
                    //System.out.println("New gen=" + gene.toString());
                    fitness = total_size = 0;
                    i=-1;
                    continue;
                }
                fitness += bp.getValue(i);
                total_size += bp.getSize(i);
            }
        }
        return (double)Math.round(fitness * 10) / 10;
    }
 
    public double CalcFitness(Population pop) {
        double fitness = 0;
        for (Individual gene : pop.getIndividuals()) {
            fitness += getFitness(gene,bp);
        }
        fitness /= pop.size();
        return fitness;
    }
}

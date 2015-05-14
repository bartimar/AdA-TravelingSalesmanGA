/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ada.gen2;

/**
 *
 * @author Marek
 */
public class Fitness {

    private CityMap cm;

    public Fitness(CityMap cm) {
        this.cm = cm;
    }

    public static double getFitness(Individual gene, CityMap cm) {
        double fitness = 0, total_size = 0;
        if(cm == null ) {
            System.out.println("CityMap is NULL. Terminate 3");
        System.exit(3);
    }
                    
        for (int i = 0; i < gene.length(); i++) {
            if (gene.getItem(i)) {
               
            }
        }
        return (double)Math.round(fitness * 10) / 10;
    }
 
    public double CalcFitness(Population pop) {
        double fitness = 0;
        for (Individual gene : pop.getIndividuals()) {
            fitness += getFitness(gene,cm);
        }
        fitness /= pop.size();
        return fitness;
    }
}

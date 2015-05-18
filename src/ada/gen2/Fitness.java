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

    public static int getFitness(Individual gene, CityMap cm) {
        int fitness = 0;
        if (cm == null) {
            System.out.println("CityMap is NULL. Terminate 3");
            System.exit(3);
        }
                    
        for (int i = 0; i < gene.length(); i++) {
            int dist, city = gene.getItem(i), nextCity;
            if(i+1>= gene.length())nextCity = gene.getItem(0);
            else nextCity = gene.getItem(i+1);
            if(city==0 || nextCity==0) System.out.println(gene);
            dist = cm.getDist(city,nextCity);
            fitness += dist;
        }
        return fitness;
    }
 
    public double CalcFitness(Population pop) {
        int fitness = 0;
        for (Individual gene : pop.getIndividuals()) {
            fitness += getFitness(gene,cm);
        }
        fitness /= pop.size();
        return fitness;
    }
}

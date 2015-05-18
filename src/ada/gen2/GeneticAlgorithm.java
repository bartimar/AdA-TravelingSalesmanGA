/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ada.gen2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

/**
 *
 * @author Marek
 */
public class GeneticAlgorithm {


    /* GA parameters */
    private static final double uniformRate = 0.1;
    private double mutationRate;
    private static final int tournamentSize = 5;
    private int elitism;
    private int selectionType;
    public static final int RANDOM = 0;
    public static final int ROULETTE_WHEEL = 1;
    public static final int TOURNAMENT = 2;
    Random rand;

    public GeneticAlgorithm(double mutationRate, int elitism, int selectionType) {
        rand = new Random();
        this.mutationRate = mutationRate;
        this.elitism = elitism;
        this.selectionType = selectionType;
    }

    /* Public methods */
    // Evolve a population
    public Population evolvePopulation(Population pop) {

        Population newPopulation = new Population(pop);

        if (elitism > 0) {
            Population elites = pop.getElite(elitism);
            // Keep our best individual
            for (Individual elite : elites.individuals) {
                newPopulation.saveIndividual(elite);
                //System.out.println("Saving elite:" + elite);

            }
            //System.out.println("Elitism done.\n" + newPopulation.toString());
        }

        // Crossover population
        // Loop over the population size and create new individuals with crossover
        for (int i = elitism; i < pop.size(); i++) {
            Individual indiv1 = null, indiv2 = null;

            switch (selectionType) {
                case RANDOM:
                    indiv1 = randomSelection(pop);
                    indiv2 = randomSelection(pop);
                    break;
                case ROULETTE_WHEEL:
                    indiv1 = rouletteSelection(pop);
                    indiv2 = rouletteSelection(pop);
                    break;
                case TOURNAMENT:
                    indiv1 = tournamentSelection(pop);
                    indiv2 = tournamentSelection(pop);
                    break;
                default:
                    System.out.println("Wrong selection type. Fatal Error!");
                    System.exit(4);
            }
            Individual newIndiv = crossover(indiv1, indiv2);
            newPopulation.saveIndividual(newIndiv);
        }

        // Mutate population
        for (int i = elitism; i < newPopulation.size(); i++) {
            mutate(newPopulation.getIndividual(i));
        }

        return newPopulation;
    }

    // Crossover individuals
    private static Individual crossover(Individual indiv1, Individual indiv2) {
        ArrayList<Integer> sol = new ArrayList();
        int length = indiv1.length();

        if (indiv1.getGene().contains(-1)) {
            System.out.println("Fath Fail gene: " + indiv1.getGene().toString());
            System.exit(1);

        }
        if (indiv2.getGene().contains(-1)) {
            System.out.println("Moth Fail gene: " + indiv2.getGene().toString());
            System.exit(1);

        }
        for (int i = 0; i < length; i++) {
            sol.add(-1);
        }
        // System.out.println("Father: " + indiv1.toString());
        // System.out.println("Mother: " + indiv2.toString());
        // Loop through genes
        for (int i = ((length / 2)); i < length - 1; i++) {
            sol.set(i, indiv2.getItem(i));
        }
        //System.out.println("After first phase gene: " + sol.toString());
        for (int i = 0, j = 0; i < indiv2.length(); i++) {
            if (sol.get(j) != -1) {
                break;
            }
            if (!sol.contains(indiv1.getItem(i))) {
                sol.set(j++, indiv1.getItem(i));
            }
        }

        for (int i = 0; i < length; i++) {
            if (!sol.contains(indiv1.getItem(i))) {
                sol.set(length - 1, indiv1.getItem(i));
            }
        }

        //System.out.println("After second phase gene: " + sol.toString());
        Individual newSol = new Individual(sol, indiv1.getCityMap());
        //System.out.println("New gene: " + newSol.toString());
        while (sol.contains(-1)) {
            for (int i = 1; i < length+1; i++) {
                if (!sol.contains(i)) {
                    sol.set(sol.lastIndexOf(-1), i);
                    i = length;
                }
            }
        }

        Set<Integer> set = new HashSet<Integer>(sol);

        if (set.size()
                < sol.size()) {
            System.out.println("Cross There are duplicates");
            System.out.println("Duplication Fail gene: " + sol.toString());
            System.exit(2);
        }
        return newSol;
    }

// Mutate an individual
    private void mutate(Individual indiv) {
        // Loop through genes
        if (indiv.getGene().contains(-1)) {
            System.out.println("Mutation Fail gene: " + indiv.getGene().toString());
            System.exit(1);

        }
        // System.out.println("Init " + indiv.getGene().toString());
        if (Math.random() <= mutationRate) {
            int i = rand.nextInt(indiv.length());
            int j = rand.nextInt(indiv.length());
            //System.out.println("Swapping " + i + "," + j);

            Collections.swap(indiv.getGene(), i, j);
        }

        Set<Integer> set = new HashSet<Integer>(indiv.getGene());

        if (set.size() < indiv.getGene().size()) {
            System.out.println("There are duplicates");
            System.out.println("Duplication Fail gene: " + indiv.getGene().toString());
            System.exit(2);
        }
    }

    // Select individuals for crossover
    private Individual tournamentSelection(Population pop) {
        // Create a tournament population
        Population tournament = new Population(pop);
        // For each place in the tournament get a random individual
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = rand.nextInt(pop.size());
            //System.out.println("Tournament("+pop.size() +"): save i, rand - "+  i + "," + randomId);
            tournament.saveIndividual(pop.getIndividual(randomId));
        }
        // Get the fittest
        Individual fittest = tournament.getFittest();
        return fittest;
    }

    // Select individuals for crossover
    private Individual rouletteSelection(Population pop) {
        // Sort the population
        if (!pop.isSorted()) {
            pop.sort();
        }
        int randomId = 0;
        double prob = rand.nextDouble();

        //System.out.println("Pop size "+ pop.size() + " prob " + prob);
        if (prob < 0.1) {
            randomId = rand.nextInt(pop.size() / 10) + pop.size() / 10;
        } else if (prob < 0.3) // 0.3 - 0.1 = 0.2 probability
        {
            randomId = rand.nextInt(pop.size() / (10 / 2)) + pop.size() / (10 / 7);
        } else if (prob < 0.6) // 0.6 - 0.3 = 0.3 probability
        {
            randomId = rand.nextInt(pop.size() / (10 / 3)) + pop.size() / (10 / 4);
        } else if (prob < 1.0) // 1.0 - 0.6 = 0.4 probability
        {
            randomId = rand.nextInt(pop.size() / (10 / 4));
        }

        randomId = randomId % pop.size(); //for sure
        return pop.getIndividual(randomId);
    }

    // Select individuals for crossover
    private Individual randomSelection(Population pop) {

        int randomId = (int) (Math.random() * pop.size());
        return pop.getIndividual(randomId);
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ada.gen2;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marek
 */
public class Algorithm {

    private String inputFile;
    private String outputFile;
    private int generation_count;
    private int individual_count;
    private double mutationRate;
    private int selectionType;
    private int elitism;
    private int total_cities;

    public Algorithm() {
    }

    private void parseArgs(String[] args) {

        if (args.length < 7) {
            System.out.println("Wrong number of arguments. Needed 7, given " + args.length);
            System.exit(1);
        }

        inputFile = args[0];
        outputFile = args[6];

        try {
            generation_count = Integer.parseInt(args[1]);
        } catch (NumberFormatException e) {
            System.err.println("Argument" + args[1] + " must be an integer.");
            System.exit(1);
        }

        try {
            individual_count = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            System.err.println("Argument" + args[2] + " must be an integer.");
            System.exit(1);
        }

        try {
            mutationRate = Double.parseDouble(args[3]);
        } catch (NumberFormatException e) {
            System.err.println("Argument" + args[3] + " must be a double.");
            System.exit(1);
        }

        try {
            selectionType = Integer.parseInt(args[4]);
            if ((selectionType < 0) || (selectionType > 2)) {
                System.out.println("Wrong selection type. Fatal Error! Only values 0,1,2 allowed. ");
                System.exit(4);
            }
        } catch (NumberFormatException e) {
            System.err.println("Argument" + args[4] + " must be an integer.");
            System.exit(1);
        }

        try {
            elitism = Integer.parseInt(args[5]);
        } catch (NumberFormatException e) {
            System.err.println("Argument" + args[5] + " must be an integer.");
            System.exit(1);
        }
    }

    private void createOutputFile(String everything, Population pop) {
        PrintWriter writer;
        try {
            writer = new PrintWriter(outputFile, "UTF-8");
            Scanner scanner = new Scanner(everything);
            for (int i = 0; i < total_cities + 2; i++) {
                writer.println(scanner.nextLine());
            }
            //writer.println(individual_count);
            writer.print(pop.print());
            writer.close();

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Algorithm.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(Algorithm.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void init(String[] args) throws IOException {

        parseArgs(args);

        try (BufferedReader br = new BufferedReader(new FileReader(inputFile))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            String everything = sb.toString();
            Scanner scanner = new Scanner(everything);
            total_cities = Integer.parseInt(scanner.nextLine());
            CityMap cm = CityMap.getInstance(total_cities);

            for (int i = 0; i < total_cities - 1; i++) {
                String[] spl = scanner.nextLine().split(" ");
                for (int j = 0; j < spl.length; j++) {
                    int distance = Integer.parseInt(spl[j]);
                    cm.add(distance);
                }
            }

            System.out.println(cm);

//            int population_size = Integer.parseInt(scanner.nextLine());
            ArrayList<Individual> population = new ArrayList();
            if (individual_count == 0) {
                // we have to use that from the input file
                //individual_count = population_size;
                for (; scanner.hasNext();) {
                    String[] s = scanner.nextLine().split(" ");
                    individual_count++;
                    //BitSet bs = new BitSet(total_cities);
                    ArrayList<Integer> gene = new ArrayList();
                    for (int j = 0; j < total_cities; j++) {
                        gene.add(Integer.parseInt(s[j]));
                    }
                    Individual inew = new Individual(gene, cm);
                    population.add(inew);
                }
            } else {
                //we have to generate random population
                Random rand = new Random();
                for (int i = 0; i < individual_count; i++) {
                    ArrayList<Integer> gene = new ArrayList();
                    for (int j = 0; j < total_cities; j++) {
                        boolean gen = true;
                        while (gen) {
                            int nxt = rand.nextInt(total_cities) + 1;
                            if (!gene.contains(nxt)) {
                                gene.add(nxt);
                                gen = false;
                            }
                        }
                    }
                    Individual inew = new Individual(gene, cm);
                    population.add(inew);
                }
            }
            if (elitism > individual_count) {
                System.err.println("Elitism cannot extend the individual count. (" + elitism + " > " + individual_count + ")");
                System.exit(1);
            }
            Population pop = new Population(population);

            //Create output file
            createOutputFile(everything, pop);

            pop.setCityMap(cm);
            String toPrint = "";
            toPrint += "total packages: " + total_cities + "\n";
            toPrint += cm.toString();
            toPrint += pop.toString();
            System.out.println(toPrint);

            Individual fittest = pop.getFittest();
            GeneticAlgorithm gen = new GeneticAlgorithm(mutationRate, elitism, selectionType);
            for (int i = 0; i < generation_count; i++) {
                Individual actFit = pop.getFittest();
                System.out.println("Generation: " + (i + 1) + " Fittest: "
                        + actFit.getFitness() + ", " + actFit + ", size: " + pop.size());
                if (pop.getFittest().getFitness() < fittest.getFitness()) {
                    fittest = pop.getFittest();
                }
                pop = gen.evolvePopulation(pop);
            }

            System.out.println("Fitness of the fittest=" + fittest.getFitness() + ", " + fittest);
        }
    }
}

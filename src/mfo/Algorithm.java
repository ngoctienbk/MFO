package mfo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class Algorithm {
	public static Random rand = new Random(100); // random seed
	private static ArrayList<Individual> childs;

	public static void main(String[] args) {
		Population pop = new Population();
		pop.initPopulation();
		for (int i = 0; i < 100; i++) {
			pop = run(pop);
		}
		pop.printPop(pop.population);
		System.out.println();
	}

	public static Population run(Population pop) {
		Population temp_pop = new Population();
		Population new_pop = new Population();
		temp_pop = pop;
		for (int i = 0; i < Population.defaultPopLength / 2; i++) {
			int parentIndex1, parentIndex2;
			parentIndex1 = rand.nextInt(Population.defaultPopLength);
			do {
				parentIndex2 = rand.nextInt(Population.defaultPopLength);
			} while (parentIndex1 == parentIndex2);

			childs = new ArrayList<Individual>();
			childs = Population.crossOver(pop.population.get(parentIndex1), pop.population.get(parentIndex2));
			temp_pop.population.add(childs.get(0));
			temp_pop.population.add(childs.get(1));
		}
		temp_pop.calculateScalarFitness(temp_pop.population, temp_pop.population.size());
		Collections.sort(temp_pop.population, Individual.compareByScalarFitness);
		int k = 0;
		for (int i = 0; i < Population.defaultPopLength * 2; i++) {
			if (temp_pop.population.get(i).getScalarFitness() == 1.0
					&& temp_pop.population.get(i).getSkillFactor() == 2) {
				new_pop.population.add(temp_pop.population.get(i));
				k++;
			}
		}
		System.out.println(k);
		for (int i = k; i < Population.defaultPopLength; i++) {
			new_pop.population.add(temp_pop.population.get(i));
		}
		return new_pop;
	}
}

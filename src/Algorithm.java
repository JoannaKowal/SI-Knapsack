import java.util.ArrayList;
import java.util.Random;

public class Algorithm {
    private int popSize;
    private double mutProb;
    private double crossProb;
    private KnapsackProblem problem;
    private Individual bestIndividual;
    private ArrayList<Individual> population;

    public Algorithm(int popSize, double crossProb, double mutProb, KnapsackProblem problem) {

        this.popSize = popSize;
        this.crossProb = crossProb;
        this.mutProb = mutProb;
        this.problem = problem;
        this.bestIndividual = null;
        initPopulation();
    }

    public void run(int iterations) {

        for (int i = 0; i < iterations; i++) {
            evaluate();
            saveBest();
            crossover();
            mutate();
        }
    }

    private void initPopulation() {

        int numberOfIndividuals = problem.getNumberOfItems();
        while (population.size() != popSize) {
            population.add(new Individual());
        }
    }

    void evaluate() {

        for (int i = 0; i < population.size(); i++) {

            population.get(i).evaluate(problem);
        }
    }

    private void saveBest() {

        Individual currentBest = population.get(0);
        for (int i = 0; i < population.size(); i++) {

            if (population.get(i).getFitness() > currentBest.getFitness()) {

                currentBest = population.get(i);
            }
        }
        if (bestIndividual == null || currentBest.getFitness() > bestIndividual.getFitness()) {

            bestIndividual = currentBest.copy();
        }

        System.out.println("Best: " + bestIndividual.getFitness());
    }

    private void crossover() {

        ArrayList<Individual> newPopulation = new ArrayList<>();
        while (newPopulation.size() != popSize) {
            Individual parent1 = selectParent();
            Individual parent2 = selectParent();
            Random doubleGenerator = new Random();
            double crossoverValue = doubleGenerator.nextDouble();
            if (crossoverValue < crossProb) {
                newPopulation.add(parent1.crossover(parent2).get(0));
                newPopulation.add(parent1.crossover(parent2).get(1));
            } else {
                newPopulation.add(parent1.copy());
                newPopulation.add(parent2.copy());
            }
        }
    }

    private void mutate(){

        for(int i = 0; i < population.size(); i++){

            population.get(i).mutate(mutProb);
        }
    }
}

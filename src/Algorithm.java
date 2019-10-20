import java.util.ArrayList;
import java.util.Random;

public class Algorithm {
    private int popSize;
    private double mutProb;
    private double crossProb;
    private KnapsackProblem problem;
    private Individual bestIndividual;
    private int tournamentSize;
    private ArrayList<Individual> population;

    public Algorithm(int popSize, double crossProb, double mutProb, int tournamentSize, KnapsackProblem problem) {

        this.popSize = popSize;
        this.crossProb = crossProb;
        this.mutProb = mutProb;
        this.tournamentSize = tournamentSize;
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

        int numberOfBits = problem.getNumberOfItems();
        population = new ArrayList<>();
        while (population.size() != popSize) {
            population.add(new Individual(numberOfBits));
        }
    }

    void evaluate() {

        for (int i = 0; i < population.size(); i++) {

            population.get(i).evaluate(problem);
        }
    }

    private void saveBest() {

        Individual currentBest = population.get(0);
        for (int i = 1; i < population.size(); i++) {

            if (population.get(i).getFitness() > currentBest.getFitness()) {

                currentBest = population.get(i);
            }
        }
        if (bestIndividual == null || currentBest.getFitness() > bestIndividual.getFitness()) {

            bestIndividual = currentBest.copy();
        }

        System.out.println("Best: " + bestIndividual.printIndividual() + " fitness: "
                + bestIndividual.getFitness());
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
        population = newPopulation;
    }

    private void mutate(){

        for(int i = 0; i < population.size(); i++){

            population.get(i).mutate(mutProb);
        }
    }

    private Individual selectParent(){

        Random generator = new Random();
        ArrayList<Individual> individuals = new ArrayList<>(tournamentSize);
        int index;
        for(int i = 0; i < tournamentSize; i++) {
            index = generator.nextInt(popSize);
            individuals.add(population.get(index));
        }
        Individual parent = individuals.get(0);
        for(int i = 1; i < individuals.size(); i++){
            if(individuals.get(i).getFitness() > parent.getFitness()){
                parent = individuals.get(i);
            }
        }
        return parent;
    }
}

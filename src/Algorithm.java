import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Algorithm {
    private int popSize;
    private double mutProb;
    private double crossProb;
    private KnapsackProblem problem;
    private MultiKnapsackProblem multiProblem;
    private Individual bestIndividual;
    private Individual worstIndividual;
    private MultiIndividual multiBestIndividual;
    private MultiIndividual multiWorstIndividual;
    private int tournamentSize;
    private ArrayList<Individual> population;
    private ArrayList<MultiIndividual> multiPopulation;
    private double avg;

    public Algorithm(int popSize, double crossProb, double mutProb, int tournamentSize,
                     KnapsackProblem problem) {
        this.popSize = popSize;
        this.crossProb = crossProb;
        this.mutProb = mutProb;
        this.tournamentSize = tournamentSize;
        this.problem = problem;
        this.bestIndividual = null;
        this.worstIndividual = null;
        initPopulation();
    }
    public Algorithm(int popSize, double crossProb, double mutProb, int tournamentSize,
                     MultiKnapsackProblem problem) {
        this.popSize = popSize;
        this.crossProb = crossProb;
        this.mutProb = mutProb;
        this.tournamentSize = tournamentSize;
        this.multiProblem = problem;
        this.multiBestIndividual = null;
        this.multiWorstIndividual = null;
        multiInitPopulation();
    }


    public void run(int iterations) {
        String data = "Nr pokolenia;Best;Avg;Worst\n";
        try {
            FileWriter writer = new FileWriter("C:\\Users\\Asiek\\Projekty\\SI-Knapsack\\single\\" +
                    "popSize1000\\popSize1000_1.csv");
            writer.write(data);

            for (int i = 0; i < iterations; i++) {
                evaluate();
                saveBest();
                crossover();
                mutate();
                writer.write(i + "; " + (int) bestIndividual.getFitness() + "; " + (int) avg + "; "
                        + (int) worstIndividual.getFitness() + "\n");
            }
            writer.close();
        } catch (IOException exception) {
        }
    }
    public void multiRun(int iterations) {
        String data = "Nr pokolenia;Best;Avg;Worst\n";
        try {
            FileWriter writer = new FileWriter("C:\\Users\\Asiek\\Projekty\\SI-Knapsack\\single\\" +
                    "popSize1000\\popSize1000_1.csv");
            writer.write(data);

            for (int i = 0; i < iterations; i++) {
                multiEvaluate();
                multiSaveBest();
                multiCrossover();
                multiMutate();
                writer.write(i + "; " + (int) multiBestIndividual.getFitness() + "; " + (int) avg + "; "
                        + (int) multiWorstIndividual.getFitness() + "\n");
            }
            writer.close();
        } catch (IOException exception) {
        }
    }

    private void initPopulation() {

        population = new ArrayList<>();
        int numberOfBits = problem.getNumberOfItems();
        while (population.size() != popSize) {
            population.add(new Individual(numberOfBits));
        }
    }
    private void multiInitPopulation(){
        multiPopulation = new ArrayList<>();
        int numberOfKnapsacks = multiProblem.getNumberOfKnapsacks();
        int numberOfBits = multiProblem.getNumberOfItems();
        while(multiPopulation.size() != popSize){
            multiPopulation.add(new MultiIndividual(numberOfKnapsacks,numberOfBits));
        }
    }


    private void evaluate() {
        for (int i = 0; i < population.size(); i++) {
            population.get(i).evaluate(problem);
        }
    }

    private void multiEvaluate() {
        for (int i = 0; i < multiPopulation.size(); i++) {
            multiPopulation.get(i).evaluate(multiProblem);
        }
    }


    private void saveBest() {
        Individual currentBest = population.get(0);
        Individual currentWorst = population.get(0);
        double sum = 0;
        double avg = 0;
        for (int i = 1; i < population.size(); i++) {
            sum += population.get(i).getFitness();
            avg = sum/(i+1);
            if (population.get(i).getFitness() > currentBest.getFitness()) {

                currentBest = population.get(i);
            }
            else if(population.get(i).getFitness() < currentWorst.getFitness())
            {
                currentWorst = population.get(i);
            }
        }
        if (bestIndividual == null || currentBest.getFitness() > bestIndividual.getFitness()) {

            bestIndividual = currentBest.copy();
        }
        if(worstIndividual == null || currentWorst.getFitness() < worstIndividual.getFitness())
        {
            worstIndividual = currentWorst.copy();
        }
        this.avg = avg;

        System.out.println("Best: " + bestIndividual.printIndividual() + " fitness: "
                + bestIndividual.getFitness());
    }
    private void multiSaveBest(){
        MultiIndividual currentBest = multiPopulation.get(0);
        MultiIndividual currentWorst = multiPopulation.get(0);
        double sum = 0;
        double avg = 0;
        for (int i = 1; i < multiPopulation.size(); i++) {
            sum += multiPopulation.get(i).getFitness();
            avg = sum/(i+1);
            if (multiPopulation.get(i).getFitness() > currentBest.getFitness()) {

                currentBest = multiPopulation.get(i);
            }
            else if(multiPopulation.get(i).getFitness() < currentWorst.getFitness())
            {
                currentWorst = multiPopulation.get(i);
            }
        }
        if (multiBestIndividual == null || currentBest.getFitness() > multiBestIndividual.getFitness()) {

            multiBestIndividual = currentBest.copy();
        }
        if(multiWorstIndividual == null || currentWorst.getFitness() < multiWorstIndividual.getFitness())
        {
            multiWorstIndividual = currentWorst.copy();
        }
        this.avg = avg;

        System.out.println("Best: " + multiBestIndividual.individualToString() + " fitness: "
                + multiBestIndividual.getFitness());

    }

    private void crossover() {

        ArrayList<Individual> newPopulation = new ArrayList<>();
        while (newPopulation.size() != popSize) {
            Individual parent1 = tournament();
            Individual parent2 = tournament();
//            Individual parent1 = roulette();
//            Individual parent2 = roulette();
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
    private void multiCrossover() {

        ArrayList<MultiIndividual> newPopulation = new ArrayList<>();
        while (newPopulation.size() != popSize) {
            MultiIndividual parent1 = multiTournament();
            MultiIndividual parent2 = multiTournament();
//            MultiIndividual parent1 = multiRoulette();
//            MultiIndividual parent2 = multiRoulette();
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
        multiPopulation = newPopulation;
    }

    private void mutate(){

        for(int i = 0; i < population.size(); i++){

            population.get(i).mutate(mutProb);
        }
    }
    private void multiMutate(){

        for(int i = 0; i < multiPopulation.size(); i++){

            multiPopulation.get(i).mutate(mutProb);
        }
    }

    private Individual tournament(){

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
    private MultiIndividual multiTournament(){

        Random generator = new Random();
        ArrayList<MultiIndividual> individuals = new ArrayList<>(tournamentSize);
        int index;
        for(int i = 0; i < tournamentSize; i++) {
            index = generator.nextInt(popSize);
            individuals.add(multiPopulation.get(index));
        }
        MultiIndividual parent = individuals.get(0);
        for(int i = 1; i < individuals.size(); i++){
            if(individuals.get(i).getFitness() > parent.getFitness()){
                parent = individuals.get(i);
            }
        }
        return parent;
    }
    private Individual roulette(){
        double sum = 0;
        for(int i = 0; i < popSize; i++){
            sum += population.get(i).getFitness();
        }
        Random generator = new Random();
        double value = generator.nextDouble() * sum;
        double partialSum = 0;
        for(int i = 0; i < popSize; i++){
            partialSum += population.get(i).getFitness();
            if(partialSum > value){
                return population.get(i);
            }
        }
        return population.get(popSize - 1);
    }
    private MultiIndividual multiRoulette(){
        double sum = 0;
        for(int i = 0; i < popSize; i++){
            sum += multiPopulation.get(i).getFitness();
        }
        Random generator = new Random();
        double value = generator.nextDouble() * sum;
        double partialSum = 0;
        for(int i = 0; i < popSize; i++){
            partialSum += multiPopulation.get(i).getFitness();
            if(partialSum > value){
                return multiPopulation.get(i);
            }
        }
        return multiPopulation.get(popSize - 1);
    }
}

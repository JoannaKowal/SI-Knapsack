import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Individual {
    private int numberOfBits;
    private double fitness;
    private ArrayList<Integer> genotype;
    private static HashMap< ArrayList<Integer>, Double> cache = new HashMap<>();

    public Individual(int numberOfBits){
        this.numberOfBits = numberOfBits;
        this.genotype = new ArrayList<>();
        randomize();
    }

    public double getFitness(){
        return fitness;
    }
    public void setFitness(double newFitness){
        this.fitness = newFitness;
    }

    public void mutate(double mutProb){

        Random generator = new Random();
        for(int i = 0; i < numberOfBits; i++){
            double mutation = generator.nextDouble();
            if(mutation < mutProb){
                bitFlip(i);
            }
        }
    }
    public ArrayList<Individual> crossover(Individual other){

        Random generator = new Random();
        Individual child1 = new Individual(numberOfBits);
        Individual child2 = new Individual(numberOfBits);
        ArrayList<Individual> children = new ArrayList<>();
        int crossingPoint =  generator.nextInt(numberOfBits) + 1;
        for(int i = 0; i < crossingPoint; i++){
            child1.genotype.set(i,genotype.get(i));
            child2.genotype.set(i,other.genotype.get(i));
        }
        for(int i = crossingPoint; i < numberOfBits; i++){
            child1.genotype.set(i, other.genotype.get(i));
            child2.genotype.set(i, genotype.get(i));
        }
        children.add(child1);
        children.add(child2);
        return children;

    }
    public void evaluate(KnapsackProblem problem){
        Double cachedValue = cache.get(genotype);
        if(cachedValue != null){
            setFitness(cachedValue);
        }
        else{
            setFitness(problem.calculateFitness(genotype));
        }

    }

    public Individual copy(){
        Individual individual = new Individual(numberOfBits);
        individual.setFitness(this.fitness);
        for(int i = 0; i < this.numberOfBits; i++){

            individual.genotype.set(i, this.genotype.get(i));
        }
        return individual;
    }
    private void bitFlip(int index){
        genotype.set(index, 1 - genotype.get(index));
    }

    private void randomize(){
        Random generator = new Random();
        for(int i = 0; i < numberOfBits; i++){
            int bit = generator.nextInt(2);
            genotype.add(bit);
        }
    }
    public String printIndividual(){
        String individual = "";
        for(int i = 0; i < numberOfBits; i++){
            individual += genotype.get(i) + ", ";
        }
        return individual;
    }




}

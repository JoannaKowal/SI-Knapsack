import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class MultiIndividual {
    private int numberOfKnapsacks;
    private int numberOfBits;
    private int[] genotype;
    private double fitness;
    private static HashMap< int[], Double> cache = new HashMap<>();

    public MultiIndividual(int numberOfKnapsacks, int numberOfBits){
        this.numberOfKnapsacks = numberOfKnapsacks;
        this.numberOfBits = numberOfBits;
        this.genotype = new int[numberOfKnapsacks * numberOfBits];
        randomize();
    }
    public double getFitness(){
        return fitness;
    }
    public void setFitness(double newFitness){
        this.fitness = newFitness;
    }
    public ArrayList<MultiIndividual> crossover(MultiIndividual other){

        Random generator = new Random();
        MultiIndividual child1 = new MultiIndividual(numberOfKnapsacks, numberOfBits);
        MultiIndividual child2 = new MultiIndividual(numberOfKnapsacks, numberOfBits);
        ArrayList<MultiIndividual> children = new ArrayList<>();
        int crossingPoint =  generator.nextInt(genotype.length) + 1;
        for(int i = 0; i < crossingPoint; i++){
            child1.genotype[i] = genotype[i];
            child2.genotype[i] = other.genotype[i];
        }
        for(int i = crossingPoint; i < genotype.length; i++){
            child1.genotype[i] = other.genotype[i];
            child2.genotype[i] = genotype[i];
        }
        children.add(child1);
        children.add(child2);
        return children;
    }
    public void mutate(double mutProb) {

        Random generator = new Random();
        for (int i = 0; i < genotype.length; i++) {
            double mutation = generator.nextDouble();
            if (mutation < mutProb) {
                bitFlip(i);
            }
        }
        fixIndividual();
    }
    public void evaluate(MultiKnapsackProblem problem){
        Double cachedValue = cache.get(genotype);
        if(cachedValue != null){
            setFitness(cachedValue);
        }
        else{
            double fitness = problem.calculateFitness(genotype);
            cache.put(genotype.clone(), fitness);
            setFitness(fitness);
        }

    }
    public MultiIndividual copy(){
        MultiIndividual individual = new MultiIndividual(numberOfKnapsacks, numberOfBits);
        individual.setFitness(fitness);
        for(int i = 0; i < genotype.length; i++){

            individual.genotype[i] = genotype[i];
        }
        return individual;
    }
    public String individualToString(){
        String individual = "";
        for(int i = 0; i < genotype.length; i++){
            individual += genotype[i] + ", ";
        }
        return individual;
    }
    private void bitFlip(int index){
        genotype[index] = 1 - genotype[index];
    }
    private void randomize(){
        Random generator = new Random();
        for(int i = 0; i < numberOfBits; i++){
            int bit = generator.nextInt(2);
            genotype[i] = bit;
        }
        fixIndividual();
    }
    private void fixIndividual(){
        for(int i = 0; i < numberOfBits; i++){
            boolean itemSelected = false;
            for(int j = 0; j < numberOfKnapsacks; j++){
                int index = j * numberOfBits + i;
                if(genotype[i] == 1){
                    itemSelected = true;
                }
                if(itemSelected){
                    genotype[index] = 0;
                }
            }
        }
    }

}

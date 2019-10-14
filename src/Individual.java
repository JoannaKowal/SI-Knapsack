import java.util.ArrayList;
import java.util.Random;

public class Individual {
    private int numberOfBits;
    private int fitness;
    private ArrayList<Integer> genotype;

    public Individual(int numberOfBits){
        this.numberOfBits = numberOfBits;
        this.genotype = new ArrayList<>();
        randomize();
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
    public int evaluate(KnapsackProblem problem){
        fitness = problem.calculateFitness(genotype);
        return fitness;
    }
    private void bitFlip(int index){

        int bit = genotype.get(index);
        bit = 1 - genotype.get(index);
    }

    private void randomize(){
        Random generator = new Random();
        int bit = generator.nextInt(2);
        genotype.add(bit);
    }



}

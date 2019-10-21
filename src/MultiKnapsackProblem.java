import java.io.*;
import java.util.ArrayList;
import java.util.Collections;

public class MultiKnapsackProblem {
    private int numberOfKnapsacks;
    private int numberOfItems;
    private ArrayList<Integer> capacities;
    private ArrayList<Integer> profits;
    private ArrayList<Integer> weights;

    public MultiKnapsackProblem(String path){
        this.capacities = new ArrayList<>();
        this.profits = new ArrayList<>();
        this.weights = new ArrayList<>();
        readFile(path);
    }

    public int getNumberOfKnapsacks() {
        return numberOfKnapsacks;
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    private void readFile(String fileName){
        String[] parts = {};
        try {
            File file = new File(fileName);
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);
            try {
                String line = br.readLine();
                parts = line.split(",");
                numberOfKnapsacks = Integer.valueOf(parts[0]);
                numberOfItems = Integer.valueOf(parts[1]);
                loadCapacities(br);
                loadItems(br);
            } catch (IOException exception){}
        }catch(FileNotFoundException exception){}
    }
    private void loadCapacities(BufferedReader br){
        try {
            String line = br.readLine();
            String[] parts = line.split(",");
            for (int i = 0; i < parts.length; i++) {
                capacities.add(Integer.valueOf(parts[i]));
            }
            Collections.sort(capacities, Collections.reverseOrder());

        } catch (IOException exception){}
    }
    private void loadItems(BufferedReader br){

        try {
            String line = br.readLine();
            String[] parts = line.split(",");
            while(parts.length == 2){
                int profit = Integer.valueOf(parts[0]);
                int weight = Integer.valueOf(parts[1]);
                profits.add(profit);
                weights.add(weight);
                line = br.readLine();
                parts = line.split(",");
            }
        } catch (IOException exception){}
    }
    public double calculateFitness(int[] genotype){
        int profit = 0;
        int weight = 0;
        int fitness = 0;
        for(int i = 0; i < numberOfKnapsacks; i++) {
            for (int j = 0; j < numberOfItems; j++) {
                int index = i * numberOfItems  + j;
                if(genotype[index] == 1){
                    profit += profits.get(j);
                    weight += weights.get(j);
                    if (weight > capacities.get(i)) {
                        return 0.1 * profit;
                    }
                }
            }
            fitness += profit;
            weight = 0;
        }
        return fitness;
    }
}

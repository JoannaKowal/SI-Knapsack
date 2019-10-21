import java.io.*;
import java.util.ArrayList;

public class KnapsackProblem {

    private int numberOfItems;
    private int capacity;
//    private int totalProfit;
  //  private int totalWeight;
    private ArrayList<Integer> profits;
    private ArrayList<Integer> weights;
    private int fitnessFuncionEvaluations = 0;

    public KnapsackProblem(String path){
//        this.numberOfItems = numberOfItems;
//        this.capacity = capacity;
//        this.totalProfit = totalProfit;
//        this.totalWeight = totalWeight;
        this.profits = new ArrayList<>();
        this.weights = new ArrayList<>();

        readFile(path);
    }

    public int getNumberOfItems() {
        return numberOfItems;
    }

    public double calculateFitness(ArrayList<Integer> genotype){
        fitnessFuncionEvaluations ++;
        int profit = 0;
        int weight = 0;
        for(int i = 0; i < genotype.size(); i++){
            if(genotype.get(i) == 1){
                profit += profits.get(i);
                weight += weights.get(i);
            }
        }
        if(weight > capacity){
            return 0.1 * profit;
        }
        return profit;

    }
    public void readFile(String fileName){

        String[] parts = {};
            try {
                File file = new File(fileName);
                FileReader fr = new FileReader(file);
                BufferedReader br = new BufferedReader(fr);
                    try {
                        String line = br.readLine();
                        parts = line.split(",");
                        numberOfItems = Integer.valueOf(parts[0]);
                        capacity = Integer.valueOf(parts[1]);
                        loadItems(br);
                    } catch (IOException exception){}
            }catch(FileNotFoundException exception){}

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

    public int getFitnessFuncionEvaluations() {
        return fitnessFuncionEvaluations;
    }
}

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class KnapsackProblem {

    private int numberOfItems;
    private int capacity;
    private int totalProfit;
    private int totalWeight;
    private ArrayList<Integer> profits;
    private ArrayList<Integer> weights;

    public KnapsackProblem(String path){
        this.numberOfItems = numberOfItems;
        this.capacity = capacity;
        this.totalProfit = totalProfit;
        this.totalWeight = totalWeight;
        this.profits = new ArrayList<>();
        this.weights = new ArrayList<>();

        readFile(path);
    }

    public int calculateFitness(ArrayList<Integer> genotype){

        int profit = 0;
        int weight = 0;
        for(int i = 0; i < genotype.size(); i++){
            if(genotype.get(i) == 1){
                profit += profits.get(i);
                weight += weights.get(i);
            }
        }
        if(weight > capacity){
            return 0;
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
                        while(parts.length <= 2){

                            String line = br.readLine();
                            parts = line.split(",");
                            loadItem(br);
                        }

                    } catch (IOException exception){}
            }catch(FileNotFoundException exception){}
            numberOfItems = Integer.valueOf(parts[0]);
            capacity = Integer.valueOf(parts[1]);

    }
    private void loadItem(BufferedReader br){

        try {
            String line = br.readLine();
            String[] parts = line.split(",");
            int profit = Integer.valueOf(parts[0]);
            int weight = Integer.valueOf(parts[1]);
            profits.add(profit);
            weights.add(weight);

        } catch (IOException exception){}
    }
}

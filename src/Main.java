public class Main {
    public static void main(String[] args) {
        KnapsackProblem problem = new KnapsackProblem("C:\\Users\\Asiek\\Projekty\\SI-Knapsack\\p06.csv");
        Algorithm GA = new Algorithm(100,0.7, 0.01, 2, problem);
        GA.run(100);
    }


}

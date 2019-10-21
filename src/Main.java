public class Main {
    public static void main(String[] args) {
//        KnapsackProblem problem = new KnapsackProblem("C:\\Users\\Asiek\\Projekty\\SI-Knapsack\\single" +
//                "\\p01.csv");
        MultiKnapsackProblem multi = new MultiKnapsackProblem("C:\\Users\\Asiek\\Projekty\\SI-Knapsack\\multi" +
                "\\p05.csv");
//        Algorithm GA = new Algorithm(1000,0.7, 0.01, 2, problem);
//        GA.run(100);

        Algorithm GA = new Algorithm(100,0.7,0.01,2, multi);
        GA.multiRun(1000);
     //   System.out.println(problem.getFitnessFuncionEvaluations());
    }


}

package org.example;

import static org.example.Main.DIMENSIONS;
import static org.example.Main.MIN_COORDINATE;
import static org.example.Main.MAX_COORDINATE;

public class SimulatedAnnealing {
    public static final double MIN_TEMPERATURE = 1;
    public static final double MAX_TEMPERATURE = 10000000;
    public static final double COOLING_RATE = 0.98;

    private final double[] currentCoordinates;
    private final double[] bestCoordinates;
    private final Function function;

    public SimulatedAnnealing(Function function){
        this.function = function;
        this.currentCoordinates = getRandomCoords();
        this.bestCoordinates = new double[DIMENSIONS];
        System.arraycopy(currentCoordinates, 0, bestCoordinates, 0, currentCoordinates.length);
    }

    public void solve(){

        double temperature = MAX_TEMPERATURE;

        while(temperature > MIN_TEMPERATURE){

            double[] nextCoordinates = getRandomCoords();

            double currentSolution = getSolution(currentCoordinates);
            double newSolution = getSolution(nextCoordinates);

            if( acceptanceProbability(currentSolution, newSolution, temperature) > Math.random() ){
                System.arraycopy(nextCoordinates, 0, currentCoordinates, 0, currentCoordinates.length);
            }

            if( getSolution(currentCoordinates) < getSolution(bestCoordinates)){
                System.arraycopy(currentCoordinates, 0, bestCoordinates, 0, bestCoordinates.length);
            }

            temperature *= COOLING_RATE;
        }
    }

    public void showSolution() {
        System.out.println("\nRozwiązanie metodą symulowanego wyżarzania");
        System.out.print("Punkty ->");
        for (int i = 0; i < DIMENSIONS; i++){
            System.out.print(" P"+(i+1)+": " + this.bestCoordinates[i]);
        }
        System.out.println("\nWartość f()=" + function.solveFunction(bestCoordinates));
    }

    private double getSolution(double[] x) {
        return function.solveFunction(x);
    }

    private double[] getRandomCoords() {
        double[] x = new double[DIMENSIONS];
        for (int i = 0; i < DIMENSIONS; ++i) {
            x[i] = random(MIN_COORDINATE , MAX_COORDINATE);
        }
        return x;
    }

    private double random(double min, double max) {
        return min + (max - min) * Math.random();
    }

    public double acceptanceProbability(double solution, double newSolution, double temperature) {
        if (newSolution < solution) {
            return 1.0;
        }
        return Math.exp((solution - newSolution) / temperature);
    }

    public double[] getBestCoordinates(){
        return bestCoordinates;
    }
}

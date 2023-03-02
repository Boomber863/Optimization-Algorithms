package org.example;

import static org.example.Main.DIMENSIONS;
import static org.example.Main.MIN_COORDINATE;
import static org.example.Main.MAX_COORDINATE;

public class ParticleSwarm {

    public static final int PARTICLES = 5;
    public static final int ITERATIONS = 1000;
    public static final double W = 0.729; // współczynnik bezwładności
    public static final double C1 = 1.49445; // współczynnik dążenia do najlepszego lokalnego rozwiązania
    public static final double C2 = 1.49445; // współczynnik dążenia do najlepszego globalnego rozwiązania


    private final double[] globalBestPosition = new double[DIMENSIONS];
    private final Particle[] particleSwarm;
    private int epochs;
    private final Function function;

    public ParticleSwarm(Function function) {
        this.function = function;
        this.particleSwarm = new Particle[PARTICLES];
        this.epochs = 0;
        generateRandomCoordinates();
    }

    private void generateRandomCoordinates() {
        for (int i = 0; i < DIMENSIONS; ++i) {
            double randCoordinate = random(MIN_COORDINATE, MAX_COORDINATE);
            this.globalBestPosition[i] = randCoordinate;
        }
    }

    public void solve() {

        for (int i = 0; i < PARTICLES; ++i) {

            double[] x = initializePosition();
            double[] v = initializeVelocity();

            this.particleSwarm[i] = new Particle(x, v, function);
            if(this.particleSwarm[i].checkBestPosition(this.globalBestPosition)){
                System.arraycopy(particleSwarm[i].getBestPosition(), 0, this.globalBestPosition, 0, this.globalBestPosition.length);
            }
        }

        while (this.epochs < ITERATIONS) {

            for (Particle part : this.particleSwarm) {

                for (int i = 0; i < part.getVelocity().length; ++i) {

                    double rl = Math.random();
                    double rg = Math.random();

                    part.getVelocity()[i] = W * part.getVelocity()[i]
                            + C1 * rl * (part.getBestPosition()[i] - part.getPosition()[i])
                            + C2 * rg * (this.globalBestPosition[i] - part.getPosition()[i]);
                }

                for (int i = 0; i < part.getPosition().length; ++i) {

                    part.getPosition()[i] += part.getVelocity()[i];

                    if (part.getPosition()[i] < MIN_COORDINATE) {
                        part.getPosition()[i] = MIN_COORDINATE;
                    } else if (part.getPosition()[i] > MAX_COORDINATE) {
                        part.getPosition()[i] = MAX_COORDINATE;
                    }
                }

                if (function.solveFunction(part.getPosition()) < function.solveFunction(part.getBestPosition())) {
                    part.setBestPosition(part.getPosition());
                }

                if (function.solveFunction(part.getBestPosition()) < function.solveFunction(this.globalBestPosition)) {
                    System.arraycopy(part.getBestPosition(), 0, this.globalBestPosition, 0, part.getBestPosition().length);
                }
            }

            ++this.epochs;
        }
    }

    private double[] initializeVelocity() {

        double[] v = new double[DIMENSIONS];
        for(int i = 0; i < DIMENSIONS; ++i){
            v[i] = random(-(MAX_COORDINATE - MIN_COORDINATE), MAX_COORDINATE - MIN_COORDINATE);
        }

        return v;
    }

    private double[] initializePosition() {

        double[] x = new double[DIMENSIONS];
        for(int i = 0; i < DIMENSIONS; ++i){
            x[i] = random(MIN_COORDINATE, MAX_COORDINATE);
        }

        return x;
    }

    private double random(double min, double max) {
        return min + (max - min) * Math.random();
    }

    public void showSolution() {
        System.out.println("Rozwiązanie metodą roju cząstek");
        System.out.print("Punkty ->");
        for (int i = 0; i < DIMENSIONS; i++){
            System.out.print(" P"+(i+1)+": " + this.globalBestPosition[i]);
        }
        System.out.println("\nWartość f()=" + function.solveFunction(globalBestPosition));
    }

    public double[] getGlobalBestPosition(){
        return globalBestPosition;
    }
}

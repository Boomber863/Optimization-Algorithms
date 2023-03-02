package org.example;

import static org.example.Main.DIMENSIONS;

public class Particle {

    private final double[] position; // pozycja
    private final double[] velocity; // prędkość
    private final double[] bestPosition; // najlepsze rozwiązanie lokalne
    private final Function function;


    public Particle(double[] position, double[] velocity, Function function) {

        this.position = new double[DIMENSIONS];
        this.velocity = new double[DIMENSIONS];
        this.bestPosition = new double[DIMENSIONS];
        this.function = function;

        System.arraycopy(position, 0, this.position, 0, position.length);
        System.arraycopy(velocity, 0, this.velocity, 0, velocity.length);
        System.arraycopy(this.position, 0, this.bestPosition, 0, this.position.length);
    }

    public double[] getPosition() {
        return position;
    }

    public double[] getVelocity() {
        return velocity;
    }

    public double[] getBestPosition() {
        return bestPosition;
    }

    public void setBestPosition(double[] bestPosition) {
        System.arraycopy(bestPosition, 0, this.bestPosition, 0, bestPosition.length);
    }

    public boolean checkBestPosition(double[] globalBestPosition) {

        return function.solveFunction(this.bestPosition) < function.solveFunction(globalBestPosition);
    }

    public String toString() {
        return "Best position so far: " + this.bestPosition[0] + ","
                + this.bestPosition[1] + "," + this.bestPosition[2];
    }
}

package org.example;

import org.knowm.xchart.QuickChart;
import org.knowm.xchart.SwingWrapper;
import org.knowm.xchart.XYChart;

import java.util.Scanner;

public class Main {
    public static final int DIMENSIONS = 2;
    public static final double MIN_COORDINATE = -10;
    public static final double MAX_COORDINATE = 10;

    public static void main(String[] args) {
        System.out.println("Podaj równanie");
        Scanner sc = new Scanner(System.in);
        String func = sc.nextLine();
        Function function = new Function(func);

        ParticleSwarm particleSwarm = new ParticleSwarm(function);
        particleSwarm.solve();
        particleSwarm.showSolution();

        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(function);
        simulatedAnnealing.solve();
        simulatedAnnealing.showSolution();

        if(DIMENSIONS == 1){
            double[] fx = new double[(int)Math.ceil(MAX_COORDINATE) - (int)Math.floor(MIN_COORDINATE) + 1];
            double[] fy = new double[(int)Math.ceil(MAX_COORDINATE) - (int)Math.floor(MIN_COORDINATE) + 1];
            double[] tmp = new double[1];
            for(int i = (int)Math.floor(MIN_COORDINATE), j = 0; i<=(int)Math.ceil(MAX_COORDINATE); i++, j++){
                fx[j] = i;
                tmp[0] = i;
                fy[j] = function.solveFunction(tmp);
            }


            tmp[0] = function.solveFunction(particleSwarm.getGlobalBestPosition());
            XYChart chartParticle = QuickChart.getChart("Rój cząstek", "X", "Y", func, fx, fy);
            chartParticle.addSeries("punkt",particleSwarm.getGlobalBestPosition(), tmp);
            new SwingWrapper(chartParticle).displayChart();

            tmp[0] = function.solveFunction(simulatedAnnealing.getBestCoordinates());
            XYChart chartSimulated = QuickChart.getChart("Symulowane wyżarzanie", "X", "Y", func, fx, fy);
            chartSimulated.addSeries("punkt", simulatedAnnealing.getBestCoordinates(), tmp);
            new SwingWrapper(chartSimulated).displayChart();
        }
    }
}

// (1.5-x+x*y)^2+(2.25-x+x*y^2)^2+(2.625-x+x*y^3)^2 - (3,0.5) = 0 - Beale function (-4.5,4.5)
// (x+2*y-7)^2+(2*x+y-5)^2 - (1,3) = 0 - Booth function (-10,10)
// 0.26*(x^2+y^2)-0.48*x*y - (0,0) = 0 - Matyas function (-10,10)
// (1-x)^2+100*(y-x^2)^2 - (1,1) = 0 - Rosenbrock function
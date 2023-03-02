package org.example;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import static org.example.Main.DIMENSIONS;

public class Function {
    private final Expression expression;
    public Function(String string) {
        if(DIMENSIONS == 1) {
            expression = new ExpressionBuilder(string)
                    .variables("x")
                    .build();
        }
        else if(DIMENSIONS == 2) {
            expression = new ExpressionBuilder(string)
                    .variables("x", "y")
                    .build();
        }
        else {
            expression = new ExpressionBuilder(string)
                    .variables("x", "y", "z")
                    .build();
        }
    }

    public double solveFunction(double[] variables){
        expression.setVariable("x", variables[0]);
        if(DIMENSIONS >= 2) expression.setVariable("y", variables[1]);
        if(DIMENSIONS >= 3) expression.setVariable("z", variables[2]);
        return expression.evaluate();
    }
}

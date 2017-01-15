package hr.fer.zemris.apr.lab1;

import hr.fer.zemris.apr.lab1.matrix.Matrix;
import hr.fer.zemris.apr.lab1.solver.SystemSolver;

import java.io.IOException;
import java.util.stream.IntStream;

/**
 * Created by generalic on 22/10/16.
 */
public class Test {

    public static void main(String[] args) throws IOException {
        Matrix A = Matrix.loadFromFile("A_test1");

        Matrix b = Matrix.loadFromFile("b_test");


        SystemSolver.solveLUP(A, b);
    }
}

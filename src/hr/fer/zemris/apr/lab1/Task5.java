package hr.fer.zemris.apr.lab1;

import hr.fer.zemris.apr.lab1.matrix.Matrix;
import hr.fer.zemris.apr.lab1.solver.SystemSolver;

import java.io.IOException;

/**
 * Zadatak 2.
 * <p>
 * Created by generalic on 21/10/16.
 */
public class Task5 {

    /**
     *
     * Main metoda programa
     * @param args argumenti komandne linije, nisu potrebni
     */
    public static void main(String[] args) throws IOException {
        Matrix A = Matrix.loadFromFile("A_task5");
        Matrix A2 = A.copy();

        Matrix b = Matrix.loadFromFile("b_task5");

        System.out.println("##########");
        SystemSolver.solveLU(A, b);
        System.out.println("##########");
        SystemSolver.solveLUP(A2, b);
        System.out.println("##########");
    }

    /**
     * Ispisi zadanu poruku na standardni izlaz i izadji iz programa
     * @param message poruka greske
     */
    private static void exitWithMsg(String message) {
        System.out.println(message);
        System.exit(-1);
    }

}

package hr.fer.zemris.apr.lab1;

import hr.fer.zemris.apr.lab1.matrix.Matrix;
import hr.fer.zemris.apr.lab1.solver.SystemSolver;

import java.io.IOException;

/**
 * Zadatak 2.
 * <p>
 * Created by generalic on 21/10/16.
 */
public class Task3 {

    /**
     * Main metoda programa
     * @param args argumenti komandne linije, nisu potrebni
     */
    public static void main(String[] args) throws IOException {
        Matrix A1 = Matrix.loadFromFile("A_task3");
        Matrix A2 = A1.copy();


        System.out.println(A1.decomposeLU());
        System.out.println(A2.decomposeLUP());

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

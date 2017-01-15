package hr.fer.zemris.apr.lab1.solver;

import hr.fer.zemris.apr.lab1.matrix.Matrix;

import java.util.function.Function;

/**
 * Razred koji sluzi za rjesavanje linearnih sustava oblika: A * x = b.
 *
 * Created by generalic on 21/10/16.
 */
public class SystemSolver {

    private static final boolean VERBOSE = true;

    /**
     * Metoda koja rjesava sustav jednadzbi oblika A * x = b
     * @param A matrica A
     * @param b vektor b
     * @return vektor rjesenja
     */
    public static Matrix solveLU(Matrix A, Matrix b) {
        return solve(A, b, Matrix::decomposeLU);
    }


    /**
     * Metoda koja rjesava sustav jednadzbi oblika A * x = b
     * @param A matrica A
     * @param b vektor b
     * @return vektor rjesenja
     */
    public static Matrix solveLUP(Matrix A, Matrix b) {
        return solve(A, b, Matrix::decomposeLUP);
    }

    private static Matrix solve(Matrix A, Matrix b, Function<Matrix, Matrix> function) {
        try {
            Matrix copyOfA = A.copy();

            if (VERBOSE) {
                System.out.println("A:\n" + copyOfA.toString() + "\n");
                System.out.println("b:\n" + b.toString() + "\n");
            }

            Matrix P = function.apply(copyOfA);

            if (P == null) {
                System.out.println("Dekompozicija neuspjela.");
                return null;
            }

            System.out.println("ffffffff");
            System.out.println(copyOfA);
            System.out.println("ffffffff");

            if (VERBOSE) {
                System.out.println("L:\n" + copyOfA.getL().toString() + "\n");
                System.out.println("U:\n" + copyOfA.getU().toString() + "\n");
                System.out.println("P:\n" + P.toString() + "\n");
            }

            b = P.multiply(b);

            if (VERBOSE) {
                System.out.println("b:\n" + b.toString() + "\n");
            }

            Matrix y = copyOfA.forwardSubstitution(b);

            if (VERBOSE) {
                System.out.println("y:\n" + y.toString() + "\n");
            }

            Matrix x = copyOfA.backwardSubstitution(y);

            if (VERBOSE) {
                System.out.println("x:\n" + x.toString() + "\n");
            }

            return x;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

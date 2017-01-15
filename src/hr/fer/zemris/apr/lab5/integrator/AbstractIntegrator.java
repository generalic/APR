package hr.fer.zemris.apr.lab5.integrator;

import hr.fer.zemris.apr.lab1.matrix.Matrix;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by generalic on 15/01/17.
 */
public abstract class AbstractIntegrator implements IIntegrator {

    private static final String DATA_PATH = "output.txt";
    protected Matrix A;
    protected Matrix x;
    protected double T;
    protected double tMax;

    private List<Double> ts = new ArrayList<>();
    private List<Double> x1s = new ArrayList<>();
    private List<Double> x2s = new ArrayList<>();

    public AbstractIntegrator(Matrix A, Matrix x, double T, double tMax) {
        this.A = A;
        this.x = x;
        this.T = T;
        this.tMax = tMax;
    }

    @Override
    public Matrix integrate() {
        Matrix xK = x.copy();

        double t = 0.0;
        while (t <= tMax) {
            xK = calculate(xK);
            t += T;

            System.out.println(t);
            System.out.println(xK);
            System.out.println();

            ts.add(t);
            x1s.add(xK.get(0, 0));
            x2s.add(xK.get(1, 0));
        }

        writeData();
        return xK;
    }

    private void writeData() {
        StringBuilder sb = new StringBuilder();

        sb.append(onlyNumbers(Arrays.toString(ts.toArray())));
        sb.append("\n");
        sb.append(onlyNumbers(Arrays.toString(x1s.toArray())));
        sb.append("\n");
        sb.append(onlyNumbers(Arrays.toString(x2s.toArray())));
        sb.append("\n");

        try {
            Files.write(Paths.get(DATA_PATH), sb.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
            // // TODO: 15/01/17 write some message about error
        }
    }

    private String onlyNumbers(String line) {
        return line.substring(1, line.length() - 1);
    }

    protected abstract Matrix calculate(Matrix xK);
}

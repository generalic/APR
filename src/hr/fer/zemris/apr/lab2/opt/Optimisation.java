package hr.fer.zemris.apr.lab2.opt;

import hr.fer.zemris.apr.lab1.matrix.Matrix;
import hr.fer.zemris.apr.lab2.functions.AbstractFunction;
import hr.fer.zemris.apr.lab2.functions.IFunction;
import hr.fer.zemris.apr.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by generalic on 06/11/16.
 */
public class Optimisation {

    private static final double EPSILON = 1E-5;

    private static final double K = 0.5 * (Math.sqrt(5) - 1);

    private static final double ALPHA = 1;
    private static final double BETA = 0.5;
    private static final double GAMMA = 2;
    private static final double SIGMA = 0.5;

    private static final double MOVE_SIMPLEX = 1;
    private static final double DX = 0.5;

    public static Pair<Double, Double> unimodalInterval(IFunction function, Matrix xs, double h) {
        double point = xs.getVector()[0];
        double l = point - h;
        double r = point + h;
        double m = point;
        double step = 1;

        double fm = function.evaluate(new Matrix(new double[]{point}));
        double fl = function.evaluate(new Matrix(new double[]{l}));
        double fr = function.evaluate(new Matrix(new double[]{r}));

        if (fm < fr && fm < fl) {
            return new Pair<>(l, r);
        } else if (fm > fr) {
            while (true) {
                l = m;
                m = r;
                fm = fr;
                step *= 2;
                r = point + h * step;
                fr = function.evaluate(new Matrix(new double[]{r}));
                if (!(fm > fr)) {
                    break;
                }
            }
        } else {
            while (true) {
                r = m;
                m = l;
                fm = fl;
                step *= 2;
                l = point - h * step;
                fl = function.evaluate(new Matrix(new double[]{l}));
                if (!(fm > fl)) {
                    break;
                }
            }

        }
        return new Pair<>(l, r);
    }

    public static Matrix coordinateDescent(IFunction f, Matrix x0) {
        int n = x0.getRows();
        Matrix x = x0;

        List<Matrix> vectors = new ArrayList<>();
        IntStream.range(0, n).forEach(i -> {
            double[] array = new double[n];
            array[i] = 1;
            vectors.add(new Matrix(array));
        });
        while (true) {
            Matrix xs = x.copy();

            for (int i = 0; i < n; i++) {

                Matrix v = vectors.get(i).copy();
                final Matrix finalLastX = x.copy();

                IFunction function = new AbstractFunction(x0.getVector()) {
                    @Override
                    protected double evaluateFunction(Matrix point) {
                        double[] transformed = new double[v.getRows()];

                        for (int j = 0; j < transformed.length; j++) {
                            double k = v.get(j, 0);
                            double a = finalLastX.get(j, 0);

                            transformed[j] = k * point.get(0, 0) + a;
                        }

                        return f.evaluate(new Matrix(transformed));
                    }
                };

                Pair<Double, Double> interval = unimodalInterval(
                        function,
                        new Matrix(new double[]{x0.getVector()[i]}),
                        1
                );

                double lambda = goldenSectionSearch(
                        function,
                        interval.getLeft(),
                        interval.getRight()
                );

                x.set(i, 0, x.get(i, 0) + lambda);
            }


            if (isSimilar(x, xs)) {
                break;
            }
        }

        return x;
    }

    private static boolean isSimilar(Matrix a, Matrix b) {
        double[] delta = a.subtract(b).getVector();

//        for (double x : delta) {
//            if (x > EPSILON) {
//                return false;
//            }
//        }

        double sum = Arrays.stream(delta)
                           .map(x -> x * x)
                           .sum();

        if (Math.sqrt(sum) >= EPSILON) {
            return false;
        }

        return true;
    }

    public static Matrix simplex(IFunction function, Matrix x0, double moveSimplex) {
        List<Matrix> simplex = new ArrayList<>();
        simplex.add(x0);

        for (int i = 0, n = x0.getRows(); i < n; i++) {
            Matrix xCopy = x0.copy();
            xCopy.set(i, 0, x0.get(i, 0) + moveSimplex);
            simplex.add(xCopy);
        }

        int counter = 0;
        while (true) {
            Pair<Integer, Integer> pair = getMinMaxIndices(function, simplex);
            int l = pair.getLeft();
            int h = pair.getRight();
            Matrix xc = getCentroid(simplex, h);
            Matrix xr = reflection(simplex.get(h), xc);

            if (counter > 10000) {
                return xc;
            } else {
                counter += 1;
            }
            if (function.evaluate(xr) < function.evaluate(simplex.get(l))) {
                Matrix xe = expansion(xr, xc);

                if (function.evaluate(xe) < function.evaluate(simplex.get(l))) {
                    simplex.set(h, xe);
                } else {
                    simplex.set(h, xr);
                }
            } else {
                if (checkCondition(function, simplex, xr, h)) {
                    if (function.evaluate(xr) < function.evaluate(simplex.get(h))) {
                        simplex.set(h, xr);
                    }
                    Matrix xk = contraction(simplex.get(h), xc);

                    if (function.evaluate(xk) < function.evaluate(simplex.get(h))) {
                        simplex.set(h, xk);
                    } else {
                        simplex = movePoints(simplex, simplex.get(l));
                    }
                } else {
                    simplex.set(h, xr);
                }
            }

            //            System.out.println(xc);
            //            System.out.println(function.evaluate(xc));

            if (stopValue(function, simplex, xc) <= EPSILON) {
                return xc;
            }
        }

    }

    public static Matrix simplex(IFunction function, Matrix x0) {
        return simplex(function, x0, MOVE_SIMPLEX);
    }

    private static Matrix reflection(Matrix xh, Matrix xc) {
        Matrix a = xc.multiply(1 + ALPHA);
        Matrix b = xh.multiply(ALPHA);

        return a.subtract(b);
    }

    private static Matrix expansion(Matrix xr, Matrix xc) {
        Matrix a = xc.multiply(1 - GAMMA);
        Matrix b = xr.multiply(GAMMA);

        return a.add(b);
    }

    private static Matrix contraction(Matrix xh, Matrix xc) {
        Matrix a = xc.multiply(1 - BETA);
        Matrix b = xh.multiply(BETA);

        return a.add(b);
    }

    private static Matrix getCentroid(List<Matrix> simplex, int h) {
        double[] xc = new double[simplex.get(0).getRows()];

        for (int i = 0, n = simplex.size(); i < n; i++) {
            if (i == h) {
                continue;
            }
            Matrix xs = simplex.get(i);
            for (int j = 0, m = xs.getRows(); j < m; j++) {
                xc[j] += xs.get(j, 0);
            }
        }

        return new Matrix(xc).multiply(1 / (double) xc.length);
    }

    private static double stopValue(IFunction f, List<Matrix> simplex, Matrix xc) {
        double sum = simplex.stream()
                            .mapToDouble(s -> Math.pow(f.evaluate(s) - f.evaluate(xc), 2))
                            .sum();

        return Math.sqrt(sum / (double) simplex.size());
    }

    private static List<Matrix> movePoints(List<Matrix> simplex, Matrix move) {
        return simplex.stream()
                      .map(x -> x.add(move))
                      .map(x -> x.multiply(SIGMA))
                      .collect(Collectors.toList());
    }

    private static boolean checkCondition(IFunction f, List<Matrix> simplex, Matrix xr, int h) {
        for (int i = 0, n = simplex.size(); i < n; i++) {
            if (i == h) {
                continue;
            }
            if (f.evaluate(xr) <= f.evaluate(simplex.get(i))) {
                return false;
            }
        }
        return true;
    }

    private static Pair<Integer, Integer> getMinMaxIndices(IFunction f, List<Matrix> simplex) {
        double tmpMin = f.evaluate(simplex.get(0));
        double tmpMax = f.evaluate(simplex.get(0));

        int minIndex = 0;
        int maxIndex = 0;

        for (int i = 0, n = simplex.size(); i < n; i++) {
            Matrix x = simplex.get(i);

            if (f.evaluate(x) - tmpMax > EPSILON) {
                tmpMax = f.evaluate(x);
                maxIndex = i;
            }
            if (tmpMin - f.evaluate(x) > EPSILON) {
                tmpMin = f.evaluate(x);
                minIndex = i;
            }
        }

        return new Pair<>(minIndex, maxIndex);
    }

    /**
     * x0 - pocetna tocka
     * xb - bazna tocka
     * xp - pocetna tocka pretrazivanja
     * xn - tocka dobivena pretrazivanjem
     * @param f
     * @param x0
     * @return
     */
    public static Matrix hookeJeeves(IFunction f, Matrix x0) {
        double dx = DX;

        Matrix xp = x0;
        Matrix xb = x0;

        while (true) {
            Matrix xn = explore(f, xp, dx);
            if (f.evaluate(xn) < f.evaluate(xb)) {
                xp = xn.multiply(2).subtract(xb);
                xb = xn;
            } else {
                dx *= 0.5;
                xp = xb;
            }

            if (dx < EPSILON) {
                return xb;
            }
        }
    }

    private static Matrix explore(IFunction f, Matrix xp, double dx) {
        Matrix xn = xp.copy();

        for (int i = 0, n = xp.getRows(); i < n; i++) {
            double fp = f.evaluate(xn);

            xn.set(i, 0, xn.get(i, 0) + dx);
            double fn = f.evaluate(xn);

            if (fn > fp) {
                xn.set(i, 0, xn.get(i, 0) - 2 * dx);
                fn = f.evaluate(xn);

                if (fn > fp) {
                    xn.set(i, 0, xn.get(i, 0) + dx);
                }
            }
        }

        return xn;
    }

    public static double goldenSectionSearch(IFunction function, double a, double b) {
        double c = b - K * (b - a);
        double d = a + K * (b - a);
        double fc = function.evaluate(new Matrix(new double[]{c}));
        double fd = function.evaluate(new Matrix(new double[]{d}));

        while ((b - a) > EPSILON) {
            if (fc < fd) {
                b = d;
                d = c;
                c = b - K * (b - a);
                fd = fc;
                fc = function.evaluate(new Matrix(new double[]{c}));
            } else {
                a = c;
                c = d;
                d = a + K * (b - a);
                fc = fd;
                fd = function.evaluate(new Matrix(new double[]{d}));
            }

            //            System.out.println(Arrays.toString(new double[]{a, b, c, d}));
            //            System.out.println(Arrays.toString(new double[]{
            //                    function.evaluate(new Matrix(new double[]{a})),
            //                    function.evaluate(new Matrix(new double[]{b})),
            //                    function.evaluate(new Matrix(new double[]{c})),
            //                    function.evaluate(new Matrix(new double[]{d}))
            //            }));

        }
        return (b + a) / 2;
    }

}

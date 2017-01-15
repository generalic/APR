package hr.fer.zemris.apr.lab3.opt;

import hr.fer.zemris.apr.lab1.matrix.Matrix;
import hr.fer.zemris.apr.lab3.constraints.IConstraint;
import hr.fer.zemris.apr.lab3.functions.AbstractFunction;
import hr.fer.zemris.apr.lab3.functions.IFunction;
import hr.fer.zemris.apr.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by generalic on 06/11/16.
 */
@SuppressWarnings("Duplicates")
public class Optimisation {

    private static final double EPSILON = 1E-5;

    private static final double K = 0.5 * (Math.sqrt(5) - 1);

    private static final double ALPHA = 1;
    private static final double BETA = 0.5;
    private static final double GAMMA = 2;
    private static final double SIGMA = 0.5;

    private static final double MOVE_SIMPLEX = 1;
    private static final double DX = 0.5;

    private static final double ALPHA_BOX = 1.3;
    private static final Pair<Double, Double> EXPLICIT = new Pair<>(-100.0, 100.0);


    public static Pair<Double, Double> unimodalInterval(IFunction function, Matrix xs, double h) {
        double point = xs.getVector()[0];
        double l = point - h;
        double r = point + h;
        double m = point;
        double step = 1;

        double fm = function.evaluate(new Matrix(new double[]{point})).get(0, 0);
        double fl = function.evaluate(new Matrix(new double[]{l})).get(0, 0);
        double fr = function.evaluate(new Matrix(new double[]{r})).get(0, 0);

        if (fm < fr && fm < fl) {
            return new Pair<>(l, r);
        } else if (fm > fr) {
            while (true) {
                l = m;
                m = r;
                fm = fr;
                step *= 2;
                r = point + h * step;
                fr = function.evaluate(new Matrix(new double[]{r})).get(0, 0);
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
                fl = function.evaluate(new Matrix(new double[]{l})).get(0, 0);
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
                    protected Matrix evaluateFunction(Matrix point) {
                        double[] transformed = new double[v.getRows()];

                        for (int j = 0; j < transformed.length; j++) {
                            double k = v.get(j, 0);
                            double a = finalLastX.get(j, 0);

                            transformed[j] = k * point.get(0, 0) + a;
                        }

                        return f.evaluate(new Matrix(transformed));
                    }

                    @Override
                    protected Matrix gradientFunction(Matrix point) {
                        return null;
                    }

                    @Override
                    protected Matrix hessianFunction(Matrix point) {
                        return null;
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

        double sum = Arrays.stream(delta)
                           .map(x -> x * x)
                           .sum();

        if (Math.sqrt(sum) >= EPSILON) {
            return false;
        }

        return true;
    }

    private static Pair<Integer, Integer> getMinMaxIndices(IFunction f, List<Matrix> simplex) {
        double tmpMin = f.evaluate(simplex.get(0)).get(0, 0);
        double tmpMax = f.evaluate(simplex.get(0)).get(0, 0);

        int minIndex = 0;
        int maxIndex = 0;

        for (int i = 0, n = simplex.size(); i < n; i++) {
            Matrix x = simplex.get(i);

            if (f.evaluate(x).get(0, 0) - tmpMax > EPSILON) {
                tmpMax = f.evaluate(x).get(0, 0);
                maxIndex = i;
            }
            if (tmpMin - f.evaluate(x).get(0, 0) > EPSILON) {
                tmpMin = f.evaluate(x).get(0, 0);
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
            if (f.evaluate(xn).get(0, 0) < f.evaluate(xb).get(0, 0)) {
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
            double fp = f.evaluate(xn).get(0, 0);

            xn.set(i, 0, xn.get(i, 0) + dx);
            double fn = f.evaluate(xn).get(0, 0);

            if (fn > fp) {
                xn.set(i, 0, xn.get(i, 0) - 2 * dx);
                fn = f.evaluate(xn).get(0, 0);

                if (fn > fp) {
                    xn.set(i, 0, xn.get(i, 0) + dx);
                }
            }
        }

        return xn;
    }

    public static double goldenSectionSearch(IFunction function, Matrix xs) {
        Pair<Double, Double> pair = unimodalInterval(function, xs, 1);
        return goldenSectionSearch(function, pair.getLeft(), pair.getRight());
    }

    public static double goldenSectionSearch(IFunction function, double a, double b) {
        double c = b - K * (b - a);
        double d = a + K * (b - a);
        double fc = function.evaluate(new Matrix(new double[]{c})).get(0, 0);
        double fd = function.evaluate(new Matrix(new double[]{d})).get(0, 0);

        while ((b - a) > EPSILON) {
            if (fc < fd) {
                b = d;
                d = c;
                c = b - K * (b - a);
                fd = fc;
                fc = function.evaluate(new Matrix(new double[]{c})).get(0, 0);
            } else {
                a = c;
                c = d;
                d = a + K * (b - a);
                fc = fd;
                fd = function.evaluate(new Matrix(new double[]{d})).get(0, 0);
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

    public static Matrix gradientDescent(IFunction f, boolean gss) {
        Matrix x = f.getInitPoint().copy();
        double old = f.evaluate(x).get(0, 0);
        double current = old;
        int counter = 0;

        while (f.gradient(x).norm() > EPSILON) {
            if (current != old) { // maybe try less than epsilon
                counter = 0;
            } else {
                counter++;
            }

            if (counter > 100) {
                System.out.println("Does not converge!");
                return x;
            }

            Matrix gradient = f.gradient(x);

            double ratio = -1;
            if (gss) {
                double[] x0 = {0};
                Matrix finalGradient = gradient;
                IFunction function = new AbstractFunction(x0) {
                    @Override
                    protected Matrix evaluateFunction(Matrix point) {
                        return f.evaluate(x.add(finalGradient.multiply(point)));
                    }

                    @Override
                    protected Matrix gradientFunction(Matrix point) {
                        return null;
                    }

                    @Override
                    protected Matrix hessianFunction(Matrix point) {
                        return null;
                    }
                };
                ratio = goldenSectionSearch(function, new Matrix(x0));
            }

            x.addToThis(gradient.multiply(ratio));

            old = current;
            current = f.evaluate(x).get(0, 0);
        }

        return x;
    }

    public static Matrix newtonRaphson(IFunction f, boolean gss) {
        Matrix x = f.getInitPoint().copy();
        double old = f.evaluate(x).get(0, 0);
        double current = old;
        int counter = 0;

        Matrix gradient = f.gradient(x);
        Matrix hessian = f.hessian(x);
        Matrix shift = hessian.multiply(gradient);

        while (shift.norm() > EPSILON) {
            if (current != old) { // maybe try less than epsilon
                counter = 0;
            } else {
                counter++;
            }

            if (counter > 100) {
                System.out.println("Does not converge!");
                return x;
            }

            gradient = f.gradient(x);
            hessian = f.hessian(x);
            shift = hessian.multiply(gradient);

            double ratio = -1;
            if (gss) {
                double[] x0 = {0};
                Matrix finalShift = shift;
                IFunction function = new AbstractFunction(new double[]{0}) {
                    @Override
                    protected Matrix evaluateFunction(Matrix point) {
                        return f.evaluate(x.add(finalShift.multiply(point)));
                    }

                    @Override
                    protected Matrix gradientFunction(Matrix point) {
                        return null;
                    }

                    @Override
                    protected Matrix hessianFunction(Matrix point) {
                        return null;
                    }
                };
                ratio = goldenSectionSearch(function, new Matrix(x0));
            }

            x.addToThis(shift.multiply(ratio));

            old = current;
            current = f.evaluate(x).get(0, 0);
        }

        return x;
    }

    private static boolean validate(Matrix x0, List<IConstraint> ls) {
        for (IConstraint l : ls) {
            if (l.evaluate(x0) < 0) {
                return false;
            }
        }

        return true;
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

        int n = simplex.size() - (h != -1 ? 1 : 0);
        return new Matrix(xc).multiply(1 / (double) n);
    }

    private static int getMaxIndex(IFunction f, List<Matrix> simplex, int h) {
        double tmpMax = Double.MIN_VALUE;
        int maxIndex = 0;

        for (int i = 0, n = simplex.size(); i < n; i++) {
            Matrix x = simplex.get(i);

            double result = f.evaluate(x).get(0, 0);
            if (result - tmpMax > EPSILON) {
                if (i != h) {
                    tmpMax = result;
                    maxIndex = i;
                }
            }
        }

        return maxIndex;
    }

    private static Matrix reflection(Matrix xh, Matrix xc) {
        return xc.multiply((1 + ALPHA_BOX)).subtract(xh.multiply(ALPHA_BOX));
    }

    private static double getStopValue(IFunction f, List<Matrix> simplex, Matrix xc) {
        double n = simplex.size();

        double sum = simplex.stream()
                            .map(x -> f.evaluate(x).subtract(f.evaluate(xc)))
                            .mapToDouble(x -> x.get(0, 0))
                            .map(x -> x * x)
                            .sum();

        return Math.sqrt(sum / n);
    }

    public static Matrix box(IFunction f, List<IConstraint> constraints) {
        Matrix x0 = f.getInitPoint();

        if (constraintsNotSatisfied(x0, constraints)) {
            throw new IllegalArgumentException("Initial point is not valid.");
        }

        Matrix xc = x0.copy();
        int n = x0.getRows();

        List<Matrix> simplex = IntStream.range(0, 2 * n)
                                        .boxed()
                                        .map(i -> x0.getVector())
                                        .map(Matrix::new)
                                        .collect(Collectors.toList());

        Random random = new Random();

        double ex = EXPLICIT.getLeft();
        double ey = EXPLICIT.getRight();

        for (int t = 0; t < 2 * n; t++) {
            for (int i = 0; i < n; i++) {
                simplex.get(t).set(i, 0, ex + random.nextDouble() * (ey - ex));
            }

            while (!validate(simplex.get(t), constraints)) {
                simplex.set(t, simplex.get(t).add(xc).multiply(0.5));
            }

            xc = getCentroid(simplex, -1);
        }

        double old = f.evaluate(xc).get(0, 0);
        double current = old;
        int counter = 0;

        while (true) {
            if (current != old) { // maybe try less than epsilon
                counter = 0;
            } else {
                counter++;
            }

            if (counter > 100) {
                System.out.println("Does not converge!");
                return xc;
            }

            int h = getMaxIndex(f, simplex, -1);
            int h2 = getMaxIndex(f, simplex, h);

            xc = getCentroid(simplex, h);
            Matrix xr = reflection(simplex.get(h), xc);

            for (int i = 0; i < n; i++) {
                if (xr.get(i, 0) < ex) {
                    xr.set(i, 0, ex);
                } else if (xr.get(i, 0) > ey) {
                    xr.set(i, 0, ey);
                }
            }

            while (!validate(xr, constraints)) {
                xr = xr.add(xc).multiply(0.5);
            }

            if (f.evaluate(xr).get(0, 0) > f.evaluate(simplex.get(h2)).get(0, 0)) {
                xr = xr.add(xc).multiply(0.5);
            }

            simplex.set(h, xr);

            old = current;
            current = f.evaluate(xc).get(0, 0);

            if (getStopValue(f, simplex, xc) <= EPSILON) {
                return xc;
            }
        }
    }

    private static boolean constraintsNotSatisfied(Matrix x, List<IConstraint> constraints) {
        return !validate(x, constraints) || checkExplicitConstraints(x);
    }

    private static boolean checkExplicitConstraints(Matrix point) {
        double[] vector = point.getVector();

        double x = vector[0];
        double y = vector[1];

        double ex = EXPLICIT.getLeft();
        double ey = EXPLICIT.getRight();

        return x < ex || x > ey || y < ex || y > ey;
    }

    private static boolean shouldStop(Matrix x1, Matrix x2) {
        for (int i = 0, n = x1.getRows(); i < n; i++) {
            if (Math.abs(x1.get(i, 0) - x2.get(i, 0)) > EPSILON) {
                return false;
            }
        }

        return true;
    }

    public static Pair<Matrix, Integer> transform(IFunction f, List<IConstraint> constraints,
            double t, BiFunction<Double, Matrix, Double> hs) {

        TransformedFunction fTransformed = new TransformedFunction(
                f,
                constraints,
                t,
                f.getInitPoint(),
                hs
        );

        while (true) {
            Matrix old = fTransformed.getInitPoint();
            Matrix current = hookeJeeves(fTransformed, fTransformed.getInitPoint());

            t *= 10;

            fTransformed.setT(t);
            fTransformed.setInitPoint(current.copy());

            if (shouldStop(old, current)) {
                int iterations = (int) (Math.log10(t) + 1);
                return new Pair<>(current, iterations);
            }
        }
    }

    public static Matrix innerPointSearch(Matrix x0, double t, List<IConstraint> constraints) {
        boolean valid = constraints.stream()
                                   .map(c -> c.evaluate(x0))
                                   .filter(v -> v < 0)
                                   .count() == 0;

        if (valid) {
            return x0;
        }

        IFunction f = new AbstractFunction(x0.getVector()) {
            @Override
            protected Matrix evaluateFunction(Matrix point) {
                double result = -constraints.stream()
                                            .mapToDouble(c -> c.evaluate(point))
                                            .filter(v -> v < 0)
                                            .sum();

                return new Matrix(new double[]{result});
            }

            @Override
            protected Matrix gradientFunction(Matrix point) {
                return null;
            }

            @Override
            protected Matrix hessianFunction(Matrix point) {
                return null;
            }
        };

        return hookeJeeves(f, x0);
    }

    public static class TransformedFunction implements IFunction {

        private IFunction function;
        private List<IConstraint> constraints;
        private double t;
        private Matrix initPoint;
        private BiFunction<Double, Matrix, Double> hs;

        public TransformedFunction(IFunction function,
                List<IConstraint> constraints, double t, Matrix initPoint,
                BiFunction<Double, Matrix, Double> hs) {
            this.function = function;
            this.constraints = constraints;
            this.t = t;
            this.initPoint = initPoint;
            this.hs = hs;
        }

        public Matrix evaluate(Matrix x) {
            double solution = 0;

            if (Objects.nonNull(hs)) {
                solution += hs.apply(t, x);
            }

            double tmp = 0;
            for (IConstraint c : constraints) {
                double result = c.evaluate(x);
                if (result <= 0) {
                    return new Matrix(new double[]{Double.MAX_VALUE});
                } else {
                    tmp += Math.log(result);
                }
            }

            solution -= 1 / t * tmp;
            solution += function.evaluate(x).get(0, 0);

            return new Matrix(new double[]{solution});
        }

        @Override
        public Matrix gradient(Matrix point) {
            return null;
        }

        @Override
        public Matrix hessian(Matrix point) {
            return null;
        }

        public Matrix getInitPoint() {
            return initPoint;
        }

        @Override
        public int getEvaluateCounter() {
            return 0;
        }

        @Override
        public int getGradientCounter() {
            return 0;
        }

        @Override
        public int getHessianCounter() {
            return 0;
        }

        @Override
        public void resetCounters() {

        }

        public void setInitPoint(Matrix initPoint) {
            this.initPoint = initPoint;
        }

        public void setT(double t) {
            this.t = t;
        }
    }

}

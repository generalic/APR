package hr.fer.zemris.apr.lab1.matrix;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Razred koji je reprezentacija matrice realnih brojeva.
 * <p>
 * Created by generalic on 21/10/16.
 */
public class Matrix {

    private static final double EPSILON = 1E-9;

    private int rows;
    private int cols;
    private double[][] data;
    private boolean isDecomposed;

    public Matrix(int n) {
        this(n, n);
    }

    public Matrix(int rows, int cols) {
        initRowsAndCols(rows, cols);
        this.data = new double[rows][cols];
    }

    /**
     * Stvora matricu iz 2D polja realnih brojeva.
     * @param data 2D polje doubleova
     */
    public Matrix(double[][] data) {
        if (data == null) {
            throw new IllegalArgumentException("Polje podataka ne smije biti null!");
        }

        if (data.length < 1 || data[0].length < 1) {
            throw new IllegalArgumentException("Polje podataka je lose formatirano!");
        }

        this.isDecomposed = false;
        this.rows = data.length;
        this.cols = data[0].length;
        this.data = data;
    }

    /**
     * Stvora matricu iz zadane matrice, dobiva se prava kopija matrice.
     * @param copyFrom zadana matrica
     */
    public Matrix(Matrix copyFrom) {
        this(copyFrom.rows, copyFrom.cols);
        this.isDecomposed = copyFrom.isDecomposed;

        for (int i = 0; i < this.rows; i++) {
            for (int j = 0; j < this.cols; j++) {
                this.data[i][j] = copyFrom.data[i][j];
            }
        }
    }

    /**
     * Stvora stupcastu matricu iz vektora realnih brojeva.
     * @param vector vektor realnih brojeva
     */
    public Matrix(double[] vector) {
        if (vector == null) {
            throw new IllegalArgumentException("Vektor ne smije biti null!");
        }

        initRowsAndCols(vector.length, 1);

        this.data = new double[vector.length][1];
        for (int i = 0; i < vector.length; i++) {
            this.data[i][0] = vector[i];
        }
    }

    /**
     * Vraca jedinicnu kvadratnu matricu reda {@code n}.
     * @param n
     * @return
     */
    public static Matrix eye(int n) {
        final Matrix matrix = new Matrix(n);
        IntStream.range(0, n).forEach(i -> matrix.data[i][i] = 1);
        return matrix;
    }

    /**
     * Vraca matricu procitanu iz datoteke.
     * @param filePath staza do datoteke
     * @return procitana matrica
     * @throws IOException
     */
    public static Matrix loadFromFile(String filePath) throws IOException {
        Path path = Paths.get(filePath);
        List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8)
                                  .stream()
                                  .map(String::trim)
                                  .filter(l -> !l.isEmpty())
                                  .filter(l -> !l.startsWith("#"))
                                  .collect(Collectors.toList());

        int rows = lines.size();
        int cols = lines.get(0).split("\\s+").length;

        final Matrix m = new Matrix(rows, cols);

        List<double[]> rowsList = lines.stream()
                                       .map(Matrix::toDoubleArray)
                                       .collect(Collectors.toList());
        IntStream.range(0, rows).forEach(i -> m.data[i] = rowsList.get(i));

        return m;
    }

    /**
     * Vraca redak matrice iz tekstualne reprezentacije.
     * @param line tekstualna reprezentacija redtka matrice
     * @return
     */
    private static double[] toDoubleArray(String line) {
        return Arrays.stream(line.split("\\s+"))
                     .mapToDouble(Double::parseDouble)
                     .toArray();
    }

    /**
     * Provjerva da li je realni broj priblizno jednak nuli.
     * @param number realni broj
     * @return true ako je priblizno jednak nuli, false inace
     */
    private static boolean isZero(double number) {
        return Math.abs(number) < EPSILON;
    }

    public double[] getVector() {
        if (!isVector(rows)) {
            throw new IllegalStateException("Matrica nije vektor.");
        }

        double[] vector = new double[rows];

        for (int i = 0; i < vector.length; i++) {
            vector[i] = this.data[i][0];
        }

        return vector;
    }

    /**
     * Provjerava da li je matrica vektor zadane velicine
     * @param size velicina vektora
     * @return trua ako je matrica vektor, false inace
     */
    public boolean isVector(int size) {
        if (rows == size && cols == 1) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Mijenja velicinu matrice, ako je nova matrica veca od
     * originalne ne definirani elementi ce biti postavljeni na 0.
     * @param rows novi broj redaka
     * @param cols novi broj stupaca
     */
    public void resize(int rows, int cols) {
        if (rows < 1 || cols < 1) {
            throw new IllegalArgumentException("Broj redaka i stupaca mora biti veci od 1!");
        }

        double[][] newData = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                newData[i][j] = data[i][j];
            }
        }

        this.data = newData;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    /**
     * Vratca element na zadanom indeksu.
     * @param row indeks retka
     * @param col indeks stupca
     * @return element na [row, col]
     */
    public double get(int row, int col) {
        checkRowIndex(row);
        checkColumnIndex(col);

        return data[row][col];
    }

    /**
     * Postavlja vrijednost elementa na zadanom indeksu.
     * @param row indeks retka
     * @param col indeks stupca
     * @param value nova vrijednost elementa
     */
    public void set(int row, int col, double value) {
        checkRowIndex(row);
        checkColumnIndex(col);

        data[row][col] = value;
    }

    /**
     * Vraca stupac matrice kao zasebnu matricu.
     * @param col indeks stupca
     * @return stupcasta matrica
     */
    public Matrix columnAsMatrix(int col) {
        checkColumnIndex(col);

        Matrix m = new Matrix(rows, 1);

        for (int i = 0; i < rows; i++) {
            m.data[i][0] = data[i][col];
        }

        return m;
    }

    /**
     * Kopira elemente iz stupcaste matrice u zadani stupac.
     * @param columnMatrix stupcasta matrica
     * @param col indeks stupca
     */
    public void setColumn(Matrix columnMatrix, int col) {
        checkColumnIndex(col);

        if (columnMatrix == null) {
            throw new IllegalArgumentException("Matrica ne smije biti null!");
        }

        if (columnMatrix.cols != 1 || columnMatrix.rows != rows) {
            throw new IllegalArgumentException("Matirce nemaju kompatibilne dimenzije!");
        }

        for (int i = 0; i < rows; i++) {
            data[i][col] = columnMatrix.data[i][0];
        }
    }

    /**
     * Zbroji ovu matricu s drugom matricom, mijenja stanje ove matice.
     * @param other druga matrica
     * @return this
     */
    public Matrix addToThis(Matrix other) {
        return binaryOperation(other, (i, j) -> data[i][j] += other.data[i][j]);
    }

    /**
     * Zbroji ovu matricu s drugom matricom, ne mijenja stanje ove matice.
     * @param other druga matrica
     * @return nova matrica
     */
    public Matrix add(Matrix other) {
        return this.copy().addToThis(other);
    }

    /**
     * Oduzmi zadanu matricu od ove matrice, mijenja stanje ove matice.
     * @param other druga matrica
     * @return this
     */
    public Matrix subtractToThis(Matrix other) {
        return binaryOperation(other, (i, j) -> data[i][j] -= other.data[i][j]);
    }

    /**
     * Oduzmi zadanu matricu od ove matrice, ne mijenja stanje ove matice.
     * @param other druga matrica
     * @return nova matrica
     */
    public Matrix subtract(Matrix other) {
        return this.copy().subtractToThis(other);
    }

    /**
     * Mnozi matricu sa zadanom matricom.
     * @param other druga matrica
     * @return umnozak matrica
     */
    public Matrix multiply(Matrix other) {
        if (cols != other.rows) {
            throw new IllegalArgumentException("Matrice nisu odgovarajuce dimenzije.");
        }

        Matrix m = new Matrix(rows, other.cols);

        IntStream.range(0, rows).forEach(i -> {
            IntStream.range(0, other.cols).forEach(j -> {
                m.data[i][j] = 0.0;
                IntStream.range(0, cols).forEach(k -> {
                    m.data[i][j] += data[i][k] * other.data[k][j];
                });
            });
        });

        return m;
    }

    /**
     * Mnozi matricu sa skalarom,
     * mijenja unutarnje stanje matrice
     * @param scalar skalarna vrijednost
     */
    public Matrix multiply(double scalar) {
        Matrix m = this.copy();
        IntStream.range(0, m.rows).forEach(i -> {
            IntStream.range(0, m.cols).forEach(j -> {
                m.data[i][j] *= scalar;
            });
        });
        return m;
    }

    /**
     * Transponira matricu.
     * @return transponirana matrica
     */
    public Matrix transponse() {
        Matrix m = new Matrix(cols, rows);

        IntStream.range(0, rows).forEach(i -> {
            IntStream.range(0, cols).forEach(j -> {
                m.data[j][i] = data[i][j];
            });
        });

        return m;
    }

    /**
     * Invertira matricu, ne mijenja unutarnje stanje.
     * @return invertirana matrica, null u slucaju neuspjeha
     */
    public Matrix inverse() {
        Matrix LU = this.copy();
        Matrix P = LU.decomposeLUP();

        if (P == null) {
            System.err.println("Inverz matrice se ne moze izracunati!");
            return null;
        }

        Matrix inverse = new Matrix(rows, cols);

        IntStream.range(0, cols).forEach(j -> {
            Matrix y = LU.forwardSubstitution(P.columnAsMatrix(j));
            Matrix x = LU.backwardSubstitution(y);

            inverse.setColumn(x, j);
        });

        return inverse;
    }

    /**
     * Supstitucija unaprijed.
     * @param b vektor b (permutirani ako se koristila LUP dekompozicija)
     * @return vektor y
     */
    public Matrix forwardSubstitution(Matrix b) {
        checkSquare();
        checkVector(b);

        double[] y = new double[rows];

        for (int i = 0; i < rows; i++) {
            y[i] = b.get(i, 0);

            for (int j = 0; j <= i - 1; j++) {
                y[i] -= y[j] * data[i][j];
            }
        }
        return new Matrix(y);
    }

    public double norm() {
        checkVector(this);
        return Math.sqrt(Arrays.stream(getVector()).map(x -> x * x).sum());
    }

    private void checkVector(Matrix b) {
        if (b.rows != rows || b.cols != 1) {
            throw new IllegalArgumentException("Krivi format vektora!");
        }
    }

    /**
     * Supstitucija unatrag.
     * @param y vektor y
     * @return vektor x
     */
    public Matrix backwardSubstitution(Matrix y) {
        checkSquare();
        checkVector(y);

        double[] x = new double[rows];

        for (int i = rows - 1; i >= 0; i--) {
            x[i] = y.get(i, 0);

            for (int j = i + 1; j < rows; j++) {
                x[i] -= x[j] * data[i][j];
            }

            x[i] /= data[i][i];
        }

        return new Matrix(x);
    }

    /**
     * Dekomponira trenutnu matricu na L i U.
     * @return vektor permutacija P, null u slucaju neuspjeha
     */
    public Matrix decomposeLU() {
        return decompose((i, P) -> {
        });
    }

    /**
     * Dekomponira trenutnu matricu na L i U.
     * @return vektor permutacija P, null u slucaju neuspjeha
     */
    public Matrix decomposeLUP() {
        return decompose(this::doLUP);
    }

    private void doLUP(int i, Matrix P) {
        // ako je lup postupak odabran provodi se djelomicno pivotiranje po stupcima
        int pivotIndex = i;
        for (int k = pivotIndex + 1; k < rows; k++) {
            if (Math.abs(data[k][i]) > Math.abs(data[pivotIndex][i])) {
                pivotIndex = k;
            }
        }
        this.swapRows(i, pivotIndex);
        P.swapRows(i, pivotIndex);
    }

    private Matrix decompose(BiConsumer<Integer, Matrix> lu) {
        checkSquare();

        final Matrix P = eye(rows);

        for (int i = 0; i < rows - 1; i++) {
            lu.accept(i, P);

            double pivotValue = data[i][i];
            if (isZero(pivotValue)) {
                System.err.println("Pivot je priblizno nula, pivot: " + pivotValue);
                return null;
            }

            for (int j = i + 1; j < rows; j++) {
                data[j][i] /= pivotValue;

                for (int k = i + 1; k < cols; k++) {
                    data[j][k] -= data[j][i] * data[i][k];
                }
            }
        }

        double lastPivotValue = data[rows - 1][cols - 1];
        if (isZero(lastPivotValue)) {
            System.err.println("LU[N,N] je priblizno nula, pivot: " + lastPivotValue);
            return null;
        }

        isDecomposed = true;
        return P;
    }

    /**
     * Vrata kopiju matrice L.
     * @return matrica L
     */
    public Matrix getL() {
        if (!isDecomposed) {
            throw new IllegalStateException("Matrica nije dekomponirana!");
        }

        Matrix L = eye(rows);

        for (int i = 1; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i > j) {
                    L.data[i][j] = data[i][j];
                }
            }
        }

        return L;
    }

    /**
     * Vrata kopiju matrice U.
     * @return matrica U
     */
    public Matrix getU() {
        if (!isDecomposed) {
            throw new IllegalStateException("Matrica nije dekomponirana!");
        }

        Matrix U = eye(rows);

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i <= j) {
                    U.data[i][j] = data[i][j];
                }
            }
        }

        return U;
    }

    /**
     * Vratca kopiju trenutne matrice.
     * @return kopija
     */
    public Matrix copy() {
        return new Matrix(this);
    }

    /**
     * Sprema matricu u obliku datoteke te vraca {@code true} ako je spremanje uspjelo,
     * inace {@code false}.
     * @param path
     * @return
     */
    public boolean saveToFile(String path) {
        if (Objects.isNull(path)) {
            throw new IllegalArgumentException("Staza datoteke ne moze biti null.");
        }

        try {
            Files.write(Paths.get(path), toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = rows;
        result = 31 * result + cols;
        result = 31 * result + Arrays.deepHashCode(data);
        result = 31 * result + (isDecomposed ? 1 : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        Matrix matrix = (Matrix) o;

        if (rows != matrix.rows) { return false; }
        if (cols != matrix.cols) { return false; }
        if (isDecomposed != matrix.isDecomposed) { return false; }

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!isZero(data[i][j] - matrix.data[i][j])) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public String toString() {
        if (isVector(rows)) {
            return Arrays.toString(getVector());
        }
        StringBuilder sb = new StringBuilder();

        IntStream.range(0, rows).forEach(i -> {
            IntStream.range(0, cols).forEach(j -> {
                sb.append(data[i][j]);

                if (j != cols - 1) {
                    sb.append("\t");
                }
            });

            if (i != rows - 1) {
                sb.append("\n");
            }
        });

        return sb.toString();
    }

    /**
     * Izvodi odabranu binarnu operaciju nad matricama i vraca novu matricu.
     * @param other druga matrica
     * @param operation binarna operacija zbrajanja ili oduzimanja
     * @return nova matrica koja je rezultat binarne operacije
     */
    private Matrix binaryOperation(final Matrix other,
            final BiConsumer<Integer, Integer> operation) {
        if (rows != other.rows || cols != other.cols) {
            throw new IllegalArgumentException("Matrice nemaju kompatibilne dimenzije!");
        }

        IntStream.range(0, rows).forEach(i -> {
            IntStream.range(0, cols).forEach(j -> {
                operation.accept(i, j);
            });
        });

        return this;
    }

    /**
     * Zamijenjuje dva retka u matrici.
     * @param first indeks prvog retka
     * @param second indeks drugog retka
     */
    private void swapRows(int first, int second) {
        checkRowIndex(first);
        checkRowIndex(second);

        double[] tmp = data[first];
        data[first] = data[second];
        data[second] = tmp;
    }

    /**
     * Provjerava da li je zadani indeks retka valjan.
     * @param row indeks retka
     */
    private void checkRowIndex(int row) {
        if (row < 0 || row >= rows) {
            throw new IllegalArgumentException("Ilegalni indeks retka!");
        }
    }

    /**
     * Provjerava da li je zadani indeks stupca valjan.
     * @param col indeks stupca
     */
    private void checkColumnIndex(int col) {
        if (col < 0 || col >= cols) {
            throw new IllegalArgumentException("Ilegalni indeks retka!");
        }
    }

    private void initRowsAndCols(int rows, int cols) {
        if (rows < 1 || cols < 1) {
            throw new IllegalArgumentException("Invalid data dimension: " + rows + "x" + cols);
        }
        this.rows = rows;
        this.cols = cols;
    }

    /**
     * Vraca <code>true</code> ako je matrica kvadratna, <code>false</code> inace.
     */
    private void checkSquare() {
        if (rows != cols) {
            throw new IllegalArgumentException("Matrica nije kvadratna!");
        }
    }

}

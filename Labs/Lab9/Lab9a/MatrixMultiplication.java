import java.util.concurrent.RecursiveTask;

class MatrixMultiplication extends RecursiveTask<Matrix> {
  
  /** The fork threshold. */
  private static final int FORK_THRESHOLD = 256; // Find a good threshold

  /** The first matrix to multiply with. */
  private final Matrix m1;

  /** The second matrix to multiply with. */
  private final Matrix m2;

  /** The starting row of m1. */
  private final int m1Row;

  /** The starting col of m1. */
  private final int m1Col;

  /** The starting row of m2. */
  private final int m2Row;

  /** The starting col of m2. */
  private final int m2Col;

  /**
   * The dimension of the input (sub)-matrices and the size of the output
   * matrix.
   */
  private int dimension;

  /**
   * A constructor for the Matrix Multiplication class.
   * @param  m1 The matrix to multiply with.
   * @param  m2 The matrix to multiply with.
   * @param  m1Row The starting row of m1.
   * @param  m1Col The starting col of m1.
   * @param  m2Row The starting row of m2.
   * @param  m2Col The starting col of m2.
   * @param  dimension The dimension of the input (sub)-matrices and the size
   *     of the output matrix.
   */
  MatrixMultiplication(Matrix m1, Matrix m2, int m1Row, int m1Col, int m2Row,
                       int m2Col, int dimension) {
    this.m1 = m1;
    this.m2 = m2;
    this.m1Row = m1Row;
    this.m1Col = m1Col;
    this.m2Row = m2Row;
    this.m2Col = m2Col;
    this.dimension = dimension;
  }

  private void add(Matrix m1, Matrix m2, Matrix res, int size, int q) {
    for (int i = 0; i < size; i++) {
      double[] m1m = m1.m[i];
      double[] m2m = m2.m[i];
      double[] rlm;
      if (q == 3 || q == 4) {
        rlm = res.m[i + size];
      } else {
        rlm = res.m[i];
      }
      
      for (int j = 0; j < size; j++) {
        if (q == 2 || q == 4) {
          rlm[j + size] = m1m[j] + m2m[j];
        } else {
          rlm[j] = m1m[j] + m2m[j];
        }
      }
    }

  }


  @Override
  public Matrix compute() {
  //  System.out.println("No. of threads: " + Thread.activeCount());
    if (dimension <= FORK_THRESHOLD) {
      return Matrix.nonRecursiveMultiply(this.m1, this.m2, this.m1Row, this.m1Col,
                                         this.m2Row, this.m2Col, dimension);
    }

    int size = this.dimension / 2;

    MatrixMultiplication c11 = new MatrixMultiplication(this.m1, this.m2,
                                             this.m1Row, this.m1Col,
                                             this.m2Row, this.m2Col,
                                             size);
    MatrixMultiplication t11 = new MatrixMultiplication(this.m1, this.m2,
                                             this.m1Row, this.m1Col + size,
                                             this.m2Row + size, this.m2Col,
                                             size);


    MatrixMultiplication c12 = new MatrixMultiplication(this.m1, this.m2,
                                             this.m1Row, this.m1Col,
                                             this.m2Row, this.m2Col + size,
                                             size);
    MatrixMultiplication t12 = new MatrixMultiplication(this.m1, this.m2,
                                             this.m1Row, this.m1Col + size,
                                             this.m2Row + size, this.m2Col + size,
                                             size);


    MatrixMultiplication c21 = new MatrixMultiplication(this.m1, this.m2,
                                             this.m1Row + size, this.m1Col,
                                             this.m2Row, this.m2Col,
                                             size);
    MatrixMultiplication t21 = new MatrixMultiplication(this.m1, this.m2,
                                             this.m1Row + size, this.m1Col + size,
                                             this.m2Row + size, this.m2Col,
                                             size);


    MatrixMultiplication c22 = new MatrixMultiplication(this.m1, this.m2,
                                             this.m1Row + size, this.m1Col,
                                             this.m2Row, this.m2Col + size,
                                             size);
    MatrixMultiplication t22 = new MatrixMultiplication(this.m1, this.m2,
                                             this.m1Row + size, this.m1Col + size,
                                             this.m2Row + size, this.m2Col + size,
                                             size);
    Matrix result = new Matrix(dimension);

    c11.fork();
    t11.fork();

    c12.fork();
    t12.fork();

    c21.fork();
    t21.fork();

    c22.fork();
    t22.fork();
    
    add(t22.join(), c22.join(), result, size, 4);
    add(t21.join(), c21.join(), result, size, 3);
    add(t12.join(), c12.join(), result, size, 2);
    add(t11.join(), c11.join(), result, size, 1);
    
    return result;
  }
}

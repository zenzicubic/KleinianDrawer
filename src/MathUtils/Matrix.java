package MathUtils;

/**
 * A 2x2 complex square matrix class.
 */
public class Matrix {
    public Complex a, b, c, d;

    /**
     * Creates a 2x2 matrix (in row-major format) with given
     * entries.
     * @param a First entry.
     * @param b Second entry.
     * @param c Third entry.
     * @param d Fourth entry.
     */
    public Matrix(Complex a, Complex b, Complex c, Complex d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
    }

    /**
     * Adds two matrices.
     * @param m Operand.
     * @return Sum of two matrices.
     */
    public Matrix add(Matrix m) {
        return new Matrix(this.a.add(m.a), this.b.add(m.b),
                          this.c.add(m.c), this.d.add(m.d));
    }

    /**
     * Subtracts two matrices.
     * @param m Operand.
     * @return Difference of two matrices.
     */
    public Matrix sub(Matrix m) {
        return new Matrix(this.a.sub(m.a), this.b.sub(m.b),
                          this.c.sub(m.c), this.d.sub(m.d));
    }

    /**
     * Multiplies two matrices.
     * @param m Operand.
     * @return Product of the two matrices.
     */
    public Matrix mul(Matrix m) {
        Complex A = this.a.mul(m.a).add(this.b.mul(m.c));
        Complex B = this.a.mul(m.b).add(this.b.mul(m.d));
        Complex C = this.c.mul(m.a).add(this.d.mul(m.c));
        Complex D = this.c.mul(m.b).add(this.d.mul(m.d));
        return new Matrix(A, B, C, D);
    }

    /**
     * Scales entries of matrix by given factor.
     * @param z Scaling factor (a complex number).
     * @return Scaled matrix.
     */
    public Matrix mul(Complex z) {
        return new Matrix(this.a.mul(z), this.b.mul(z),
                          this.c.mul(z), this.d.mul(z));
    }

    /**
     * Scales entries of matrix by given factor.
     * @param f Scaling factor (a float).
     * @return Scaled matrix.
     */
    public Matrix mul(float f) {
        return new Matrix(this.a.mul(f), this.b.mul(f),
                          this.c.mul(f), this.d.mul(f));
    }

    /**
     * Finds trace of current matrix.
     * @return Trace of given matrix.
     */
    public Complex trace() {
        return this.a.add(this.d);
    }

    /**
     * Finds determinant of current matrix.
     * @return Determinant of given matrix.
     */
    public Complex det() {
        return this.a.add(this.d).sub(this.b.mul(this.c));
    }

    /**
     * Inverts the current matrix.
     * @return Inverse of given matrix.
     */
    public Matrix inverse() {
        Complex c = this.det().inv();
        return new Matrix(this.d, this.b.neg(),
                          this.c.neg(), this.a).mul(c);
    }

    /**
     * Finds the positive fixed point of the Moebius transform
     * the current matrix describes.
     * @return Positive fixed point.
     */
    public Complex fix() {
        Complex D = this.d.sub(this.a);
        Complex discriminant = (D.sqr().sub(this.b.mul(this.c).mul(4))).sqrt();

        return D.neg().add(discriminant)
                .div(this.c.mul(2));
    }
}

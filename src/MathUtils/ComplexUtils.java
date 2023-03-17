package MathUtils;

/**
 * A complex number utility class.
 */
public final class ComplexUtils {
    public static Complex ONE = new Complex(1, 0);
    public static Complex I = new Complex(0, 1);
    public static Complex ZERO = new Complex(0, 0);
    public static Complex TWO_I = Im(2);
    public static Complex TWO = Re(2);

    /**
     * Creates a complex number with a given real part.
     * @param x Real part.
     * @return A complex number with given real part.
     */
    public static Complex Re(float x) {
        return new Complex(x, 0);
    }

    /**
     * Creates a complex number with a given imaginary part.
     * @param x Imaginary part.
     * @return A complex number with given imaginary part.
     */
    public static Complex Im(float x) {
        return new Complex(0, x);
    }

    /**
     * Finds the roots of the current quadratic.
     * @param a A-coefficient.
     * @param b B-coefficient.
     * @param c C-coefficient.
     * @return Roots (both of them) of the given quadratic.
     */
    public static Complex[] solveQuadratic(Complex a, Complex b, Complex c) {
        Complex D = b.sqr().sub(a.mul(c).mul(4)).sqrt();

        Complex f = a.mul(2);
        return new Complex[] {
                (b.neg().add(D)).div(f),
                (b.neg().sub(D)).div(f)
        };
    }
}

package MathUtils;

/**
 * A generic class for complex numbers.
 */
public class Complex {
    public float x, y;

    /**
     * Creates a complex number with given real and imaginary parts.
     * @param x Real part.
     * @param y Imaginary part.
     */
    public Complex(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Adds two complex numbers.
     * @param z Operand.
     * @return Sum of two complex numbers.
     */
    public Complex add(Complex z) {
        return new Complex(this.x + z.x, this.y + z.y);
    }

    /**
     * Subtracts two complex numbers.
     * @param z Operand.
     * @return Difference of two complex numbers.
     */
    public Complex sub(Complex z) {
        return new Complex(this.x - z.x, this.y - z.y);
    }

    /**
     * Scales current complex number by given factor.
     * @param s Scaling factor (operand).
     * @return Scaled result.
     */
    public Complex mul(float s) {
        return new  Complex(this.x * s, this.y * s);
    }

    /**
     * Multiplies two complex numbers.
     * @param z Operand.
     * @return Product of two complex numbers.
     */
    public Complex mul(Complex z) {
        return new Complex(this.x * z.x - this.y * z.y, this.x * z.y + this.y * z.x);
    }

    /**
     * Divides two complex numbers.
     * @param z Operand.
     * @return Quotient of two complex numbers.
     */
    public Complex div(Complex z) {
        return this.mul(z.inv());
    }

    /**
     * Contracts given complex number by a factor.
     * @param s Shrink factor.
     * @return Result of scaling.
     */
    public Complex div(float s) {
        return new  Complex(this.x / s, this.y / s);
    }

    /**
     * Calculates the product of the complex number by <i>i</i>.
     * @return Operation result.
     */
    public Complex itimes() {
        return new Complex(-this.y, this.x);
    }

    /**
     * Calculates the negated version of the current complex number.
     * @return Negated version of current complex number.
     */
    public Complex neg() {
        return new Complex(-this.x, -this.y);
    }

    /**
     * Calculates the complex conjugate of the current complex number.
     * @return Conjugate of current complex number.
     */
    public Complex conj() {
        return new Complex(this.x, -this.y);
    }

    /**
     * Calculates the multiplicative inverse of the current complex number.
     * @return Multiplicative inverse of current complex number.
     */
    public Complex inv() {
        float f = this.x * this.x + this.y * this.y;
        return this.conj().div(f);
    }

    /**
     * Squares the current complex number and returns it.
     * @return Square of current complex number.
     */
    public Complex sqr() {
        return this.mul(this);
    }

    /**
     * Calculates the magnitude of the current complex number.
     * @return Magnitude of current complex number.
     */
    public float norm() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y);
    }

    /**
     * Returns the sign of the input.
     * Zero is positive, which Math.signum does not consider,
     * so I had to redo it.
     *
     * @param t Number to find sign of.
     * @return Sign of number.
     */
    private float sign(float t) { return (t >= 0 ? 1 : -1); }

    /**
     * Finds the square root of the current complex number and returns it.
     *
     * Note that this only returns the principal (positive) square root.
     * @return Square root of current complex number.
     */
    public Complex sqrt() {
        // Compute the square root of the current num
        float n = this.norm();
        float nX = (float) Math.sqrt((n + this.x) * 0.5f);
        float nY = (float) Math.sqrt((n - this.x) * 0.5f) * sign(this.y);

        return new Complex(nX, nY);
    }

    /**
     * Stringifies current complex number.
     * @return Stringified version of current complex number.
     */
    @Override
    public String toString() {
        // Why are there so many edge cases?
        if (this.y == 0) {
            return String.valueOf(this.x);
        } else if (this.x == 0) {
            return this.y + "i";
        } else if (this.y > 0) {
            return this.x + " - " + Math.abs(this.y) + "i";
        }
        return this.x + " - " + this.y + "i";
    }
}

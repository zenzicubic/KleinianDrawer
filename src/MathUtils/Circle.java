package MathUtils;

/**
 * Circle class defined by float and
 * complex numbers.
 */
public class Circle {
    public Complex z;
    public float r;

    /**
     * Creates a circle with given parameters.
     * @param z Center as a complex number.
     * @param r Radius.
     */
    public Circle(Complex z, float r) {
        this.z = z;
        this.r = r;
    }
}

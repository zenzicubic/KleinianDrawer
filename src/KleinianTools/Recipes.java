package KleinianTools;

import MathUtils.*;

import static MathUtils.ComplexUtils.*;

/**
 * This is a class full of various "recipes"
 * for Kleinian and Schottky groups as given in
 * Indra's Pearls.
 */
public final class Recipes {
    /**
     * Calculates the &theta;-Schottky group generators and circles as
     * detailed on p. 118 of Indra's Pearls.
     * @param t Angle.
     * @return Schottky group with given parameters.
     */
    public static SchottkyGenerator thetaSchottky(float t) {
        float R = (float) Math.abs(Math.tan(t));
        float r = (float) (1 / Math.cos(t));

        float cos = (float) Math.cos(t);
        float sin = (float) (1 / Math.sin(t));

        Matrix a = new Matrix(ONE, Re(cos), Re(cos), ONE).mul(sin);
        Matrix b = new Matrix(ONE, Im(cos), Im(-cos), ONE).mul(sin);

        Circle[] circles = new Circle[] {new Circle(Re(r), R), new Circle(Re(-r), R),
                new Circle(Im(r), R), new Circle(Im(-r), R)};
        return new SchottkyGenerator(a, b, circles);
    }

    /**
     * Calculates the kissing-Schottky group generators and circles as detailed on p. 170.
     * @param y Trace of the first matrix.
     * @param u Trace of the second matrix.
     * @param k Relative size.
     * @return Generator object describing the Schottky group.
     */
    public static SchottkyGenerator kissingSchottky(float y, float u, float k) {
        float x = (float) Math.sqrt(y * y + 1);
        float v = (float) Math.sqrt(u * u - 1);

        Matrix a = new Matrix(Re(u), Im(k * v), Im(-v / k), Re(u));
        Matrix b = new Matrix(Re(x), Re(y), Re(y), Re(x));

        float r = k / v;
        float R = 1 / y;
        Circle[] circles = new Circle[]{
                new Circle(Re(x / y), R),
                new Circle(Re(-x / y), R),
                new Circle(Im((k * u) / v), r),
                new Circle(Im(-(k * u) / v), r)};
        return new SchottkyGenerator(a, b, circles);
    }

    // TODO: find and add a generator that produces Schottky groups for given circles.

    /**
     * These are the Apollonian gasket matrices from p. 201 of
     * Indra's Pearls.
     */
    public static Generator apollonian = new Generator(
            new Matrix(ONE, ZERO, Im(-2), ONE),
            new Matrix(new Complex(1, -1), ONE, ONE, new Complex(1, 1))
    );

    /**
     * Calculates the matrices for a Maskit-type quasifuchsian group
     * as described on p. 259 of Indra's Pearls.
     *
     * @param mu The parameter for the Maskit recipe.
     * @return A Generator object representing the desired group.
     */
    public static Generator maskitKleinian(Complex mu) {
        Matrix a = new Matrix(mu, ONE, ONE, ZERO);
        Matrix b = new Matrix(ONE, Re(2), ZERO, ONE);

        return new Generator(a, b);
    }

    /**
     * Calculates the matrices for a Riley-type quasifuchsian group
     * as described on p. 259 of Indra's Pearls.
     *
     * @param c The parameter for the recipe.
     * @return A Generator object representing the desired group.
     */
    public static Generator rileyKleinian(Complex c) {
        Matrix a = new Matrix(ONE, ZERO, c, ONE);
        Matrix b = new Matrix(ONE, Re(2), ZERO, ONE);

        return new Generator(a, b);
    }

    /**
     * Troels Jorgensen's "recipe" for parabolic commutator groups,
     * from pg. 256 of Indra's Pearls.
     *
     * @param tA Trace of first generator matrix.
     * @param tB Trace of second generator matrix.
     * @param solNum Which solution to use (0 or 1).
     * @return A Generator object representing the desired group.
     */
    public static Generator jorgensenRecipe(Complex tA, Complex tB, int solNum) {
        Complex tAB = solveQuadratic(ONE, tA.mul(tB).neg(), tA.sqr().add(tB.sqr()))[solNum];

        Complex i = tA.sub(tB.div(tAB));
        Complex j = tA.div(tAB.sqr());
        Complex k = tB.div(tAB);
        Matrix a = new Matrix(i, j, tA, k);

        i = tB.sub(tA.div(tAB));
        j = tB.div(tAB.sqr()).neg();
        k = tA.div(tAB);
        Matrix b = new Matrix(i, j, tB.neg(), k);

        return new Generator(a, b);
    }

    /**
     * "Grandma's recipe" for parabolic commutator groups, from pg. 229 of Indra's Pearls.
     *
     * @param tA Trace of first generator matrix.
     * @param tB Trace of second generator matrix.
     * @param solNum Which solution to use (0 or 1).
     * @return A Generator object representing the desired group.
     */
    public static Generator grandmaRecipe(Complex tA, Complex tB, int solNum) {
        // Precompute necessary coeffs
        Complex tAB = solveQuadratic(ONE, tA.mul(tB).neg(), tA.sqr().add(tB.sqr()))[solNum];

        Complex num = (tAB.sub(TWO)).mul(tB);
        Complex den = tB.mul(tAB).sub(tA.mul(2)).add(tAB.mul(TWO_I));
        Complex z0 = num.div(den);

        // Now, compute the b and ab matrices
        Complex d = tB.mul(0.5f);
        Complex r1 = (tB.sub(TWO_I)).mul(0.5f);
        Complex r2 = (tB.add(TWO_I)).mul(0.5f);
        Matrix b = new Matrix(r1, d, d, r2);

        d = tAB.mul(0.5f);
        r1 = ((tAB.sub(TWO))).div(z0.mul(2));
        r2 = ((tAB.add(TWO)).mul(z0)).mul(0.5f);
        Matrix ab = new Matrix(d, r1, r2, d);

        // Find one matrix using the other
        Matrix a = ab.mul(b.inverse());
        return new Generator(a, b);
    }
}

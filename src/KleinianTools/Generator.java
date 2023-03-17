package KleinianTools;

import MathUtils.Matrix;

/**
 * A class describing a two-generator Kleinian group.
 */
public class Generator {
    Matrix[] gens;

    /**
     * Creates a new two-generator Kleinian group with given generators.
     * @param a First generator.
     * @param b Second generator.
     */
    public Generator(Matrix a, Matrix b) {
        this.gens = new Matrix[] {a, b, a.inverse(), b.inverse()};
    }
}

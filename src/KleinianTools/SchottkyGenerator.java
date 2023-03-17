package KleinianTools;

import MathUtils.*;

import java.security.GeneralSecurityException;

/**
 * This class defines a two-generator Schottky group with its paired circles.
 */
public class SchottkyGenerator extends Generator {
    Circle[] circ;
    /**
     * Creates a new two-generator Schottky group with given generators.
     *
     * @param a First generator.
     * @param b Second generator.
     * @param circles Circles defining the Schottky group.
     */
    public SchottkyGenerator(Matrix a, Matrix b, Circle[] circles) {
        super(a, b);
        circ = circles;
    }

    /**
     * Returns this Schottky group's generator matrices.
     * @return A Generator object with the same set of generators.
     */
    public Generator generator() {
        return new Generator(this.gens[0], this.gens[1]);
    }
}

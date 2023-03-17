package MathUtils;

import static MathUtils.ComplexUtils.*;

/**
 * Set of Moebius-transform related utils.
 */
public final class MoebiusUtils {

    /**
     * Applies Moebius transformation to a given point.
     * @param M Matrix describing the transform.
     * @param z Point to transform.
     * @return Transformed point.
     */
    public static Complex moebiusPt(Matrix M, Complex z) {
        Complex num = M.a.mul(z).add(M.b);
        Complex den = M.c.mul(z).add(M.d);
        return num.div(den);
    }

    /**
     * Applies Moebius transformation to a given circle.
     * Method from pg. 91 of Indra's Pearls.
     *
     * @param M Matrix describing the transform.
     * @param c Circle to transform.
     * @return Transformed circle.
     */
    public static Circle moebiusCirc(Matrix M, Circle c) {
        Complex den = M.d.div(M.c).add(c.z);
        Complex z = c.z.sub(Re(c.r * c.r).div(den.conj()));

        Complex cen = moebiusPt(M, z);
        float r = cen.sub(moebiusPt(M, c.z.add(Re(c.r)))).norm();
        return new Circle(cen, r);
    }
}

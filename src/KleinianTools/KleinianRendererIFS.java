package KleinianTools;

import MathUtils.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static MathUtils.ComplexUtils.*;
import static MathUtils.MoebiusUtils.*;

/**
 * This class describes a renderer for a general Kleinian limit set
 * using Michael Barnsley's IFS technique.
 */
public class KleinianRendererIFS {
    int width, height, hW, hH;
    float size, scale;
    Complex cen = ZERO;

    int pointSz;
    Generator gen;

    int w = 1;
    BufferedImage im;
    Graphics2D gr;

    /**
     * Creates a new Kleinian group renderer with given parameters.
     * @param width Width of output image.
     * @param height Height of output image.
     * @param size Display size about minor axis.
     * @param gen Generators for the group.
     */
    public KleinianRendererIFS(int width, int height, float size,  Generator gen) {
        this.width = width;
        this.height = height;
        this.hW = width / 2;
        this.hH = height / 2;

        this.gen = gen;
        this.size = size;
        this.scale = Math.min(this.hW, this.hH) / size;

        im = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        gr = im.createGraphics();
    }

    /**
     * Sets the current paint color to a given color.
     * @param r Red value of color.
     * @param g Green value of color.
     * @param b Blue value of color.
     */
    public void setColor(int r, int g, int b) {
        this.gr.setColor(new Color(r, g, b));
    }

    /**
     * Sets the current stroke size.
     * @param i Size in px.
     */
    public void setWeight(int i) {
        this.w = i;
    }

    /**
     * Clears the image and sets its background.
     * @param r Red value of color.
     * @param g Green value of color.
     * @param b Blue value of color.
     */
    public void setBG(int r, int g, int b) {
        this.gr.setBackground(new Color(r, g, b));
        this.gr.clearRect(0, 0, this.width, this.height);
    }

    /**
     * This private method plots a point onto the image.
     * @param z Point as a complex number.
     */
    private void markPt(Complex z) {
        z = z.sub(this.cen);
        int nX = (int) (z.x * this.scale + this.hW);
        int nY = (int) (z.y * this.scale + this.hH);

        if (nX < width && nX > 0 && nY < height && nY > 0) {
            gr.fillRect(nX, nY, this.w, this.w);
        }
    }

    /**
     * Applies the IFS method to draw the limit set of this Kleinian group.
     * The initial condition is the positive fixed point of one of the generators (a here).
     * @param numPts Number of points to plot.
     */
    public void calculate(long numPts) {
        Complex z = gen.gens[0].fix();
        int j;
        for (long i = 0; i < numPts; i ++) {
            j = (int)Math.floor(4 * Math.random());
            z = moebiusPt(gen.gens[j], z);
            this.markPt(z);
        }
    }

    /**
     * Sets center of the view coordinate system.
     * @param cen Center point as a complex number.
     */
    public void setCenter(Complex cen) {
        this.cen = cen;
    }

    /**
     * Exports limit set as a .png file with given name.
     * @param name Name of file.
     */
    public void export(String name) {
        File file = new File(name + ".png");
        try {
            ImageIO.write(this.im, "png", file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

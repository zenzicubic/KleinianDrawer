package KleinianTools;

import MathUtils.*;

import static MathUtils.MoebiusUtils.*;

import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * A class that describes a Schottky circle drawer
 * using BFS.
 */
public class SchottkyOrbitRenderer {
    int width, height, hW, hH;
    float size, scale;

    int maxIt;
    SchottkyGenerator gen;

    BufferedImage im;
    Graphics2D gr;

    /**
     * Creates a new renderer object with given params.
     * @param width Image width.
     * @param height Image height.
     * @param size Display size about minor axis.
     * @param maxIter Maximum number of iterations.
     * @param gen Generator matrices and circles.
     */
    public SchottkyOrbitRenderer(int width, int height, float size, int maxIter, SchottkyGenerator gen) {
        this.width = width;
        this.height = height;
        this.hW = width / 2;
        this.hH = height / 2;

        this.maxIt = maxIter;
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
        this.gr.setPaint(new Color(r, g, b));
    }

    /**
     * Sets the current stroke size.
     * @param i Size in px.
     */
    public void setWeight(int i) {
        this.gr.setStroke(new BasicStroke(i));
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
     * Draws a circle with given coords.
     * @param c Circle object.
     */
    private void drawCircle(Circle c) {
        // Draw a circle
        int s = (int) (c.r * this.scale);
        int nX = (int) (c.z.x * this.scale + this.hW);
        int nY = (int) (c.z.y * this.scale + this.hH);
        gr.drawOval(nX - s, nY - s, 2 * s, 2 *  s);
    }

    /**
     * Starts the calculation process, and initializes the recursive drawing process.
     */
    public void calc() {
        for (Circle c : gen.circ) {
            drawCircle(c);
        }

        for (int j = 0; j < 4; j ++) {
            bfs(gen.gens[j], j, maxIt);
        }
    }

    /**
     * This (private) method defines the recursive process
     * that traverses the Schottky tree.
     * @param M Current matrix.
     * @param j Index of the last matrix.
     * @param i Number of iterations remaining until the process terminates.
     */
    private void bfs(Matrix M, int j, int i) {
        Circle nCirc;
        for (Circle c : gen.circ) {
            nCirc = moebiusCirc(M, c);
            drawCircle(nCirc);
        }

        if (i == 0) return;
        for (int k = 0; k < 4; k ++) {
            if (k != (j + 2) % 4) {
                bfs(M.mul(gen.gens[k]), k, i - 1);
            }
        }
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

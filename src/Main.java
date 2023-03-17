import KleinianTools.Recipes;
import KleinianTools.*;
import MathUtils.Complex;

public class Main {
    public static void main(String[] args) {
        KleinianRendererIFS ren = new KleinianRendererIFS(5000, 3000, 1.2f, Recipes.jorgensenRecipe(new Complex(1.87f, 0.1f), new Complex(1.87f, -0.1f), 1));
        ren.setBG(7,7,7);
        ren.setColor(255,255,255);
        ren.setWeight(1);

        ren.calculate(150000000L);
        ren.export("jorgenson");
    }
}
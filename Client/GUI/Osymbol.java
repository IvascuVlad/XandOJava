package GUI;

import java.awt.geom.Ellipse2D;

public class Osymbol extends Ellipse2D.Double {
    public Osymbol(double x0, double y0, double radius) {
        super(x0 - radius / 2, y0 - radius / 2, radius, radius);
    }
}

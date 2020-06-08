package GUI;

import java.awt.*;

public class Xsymbol extends Polygon {
    public Xsymbol(int x, int y, Graphics2D graphics){
        graphics.setStroke(new BasicStroke(7));
        graphics.drawLine(x-50,y-50,x+50,y+50);
        graphics.drawLine(x+50,y-50,x-50,y+50);
    }
}

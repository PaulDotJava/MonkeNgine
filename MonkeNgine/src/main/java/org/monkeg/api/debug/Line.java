package org.monkeg.api.debug;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.monkeg.MonkeNgine;
import org.monkeg.api.util.color.Color;

public class Line {
    public  float x1, y1;
    public float x2, y2;

    public Color color;

    public Line(Vector2f p1, Vector2f p2, Color color) {
        this(p1.x, p1.y, p2.x, p2.y, color);
    }

    public Line(float x1, float y1, float x2, float y2, Color color) {
        this.x1 = x1;
        this.y1 = y1;

        this.x2 = x2;
        this.y2 = y2;

        this.color = color;
    }

    public void add() {
        MonkeNgine.getInstance().getRenderer().getDebugLayer().addLine(this);
    }
}

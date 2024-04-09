package org.monkeg.api.util.color;

public class Color {
    public static Color RED = new Color(1, 0, 0);
    public static Color GREEN = new Color(0, 1, 0);
    public static Color BLUE = new Color(0, 0, 1);
    public static Color YELLOW = new Color(1, 1, 0);
    public static Color CYAN = new Color(0, 1, 1);
    public static Color MAGENTA = new Color(1, 0, 1);

    private float[] rgba;

    public Color(float r, float g, float b) {
        rgba = new float[]{r, g, b, 1};
    }

    public Color(float r, float g, float b, float a) {
        this(r, g, b);
        rgba[3] = a;
    }

    public float[] getRGBA() {
        return rgba;
    }
}

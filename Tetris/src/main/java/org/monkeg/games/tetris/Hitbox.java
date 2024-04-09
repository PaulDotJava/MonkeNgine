package org.monkeg.games.tetris;

import org.joml.Vector2f;

public class Hitbox {
    int x, y;
    int w, h;

    public Hitbox(int x, int y, int w, int h) {
        this.x = x;
        this.y = y;
        this.w = w;
        this.h = h;
    }

    public boolean isInBox(Vector2f coords) {
        return coords.x > x && coords.x < x + w && coords.y > y && coords.y < y + h;
    }
}

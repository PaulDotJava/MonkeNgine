package org.monkeg.api.entity;

import org.joml.Vector2f;
import org.monkeg.api.entity.elements.Transform;
import org.monkeg.rendering.data.Quad;
import org.monkeg.rendering.data.textures.Texture;

public class Sprite extends Entity implements Drawable{
    private final Texture texture;
    private final Transform transform;
    private final Quad quad;

    public Sprite(String file) {
        quad = new Quad();
        texture = new Texture(file);
        transform = new Transform();
        transform.scale(texture.getWidth(), texture.getHeight());
    }

    @Override
    public void draw() {
        quad.bind();
        texture.bind(0);
        transform.bind();
    }

    @Override
    public void delete() {
        super.delete();
        freeResources();
    }

    @Override
    public void freeResources() {
        super.freeResources();
        texture.delete();
        quad.delete();
    }

    public void move(float dx, float dy) {
        transform.translate(dx, dy);
    }

    public void move(Vector2f d) {
        move(d.x, d.y);
    }

    public void setPosition(Vector2f pos) {
        transform.setPosition(pos);
    }

    public void scale(float factor) {
        transform.scale(factor, factor);
    }

    public Vector2f getPosition() {
        return new Vector2f(transform.getPosition().x, transform.getPosition().y);
    }

    public int getWidth() {
        return texture.getWidth();
    }

    public int getHeight() {
        return texture.getHeight();
    }
}

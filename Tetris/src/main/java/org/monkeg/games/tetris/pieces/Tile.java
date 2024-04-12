package org.monkeg.games.tetris.pieces;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.monkeg.api.entity.Sprite;
import org.monkeg.api.util.color.Color;
import org.monkeg.games.tetris.GameScreen;

public class Tile {
    private Sprite sprite;

    private Vector2i position;  // Grid coords

    private Piece parentPiece;
    private PieceColor color;

    public Tile(Vector2i position, PieceColor color) {
        this.position = new Vector2i(position);

        this.color = color;

        String fileName = switch (color) {
            case RED -> "red_tile.png";
            case GREEN -> "green_tile.png";
            case YELLOW -> "yellow_tile.png";
            case PURPLE -> "purple_tile.png";
            case BLUE -> "blue_tile.png";
        };

        sprite = new Sprite("src/main/resources/textures/" + fileName);
        setPosition(position);

        float factor = (float) GameScreen.tileSize / (float) sprite.getWidth();
        float newSize = sprite.getHeight() * factor;

        sprite.scale(newSize);
    }

    public void setParentPiece(Piece parentPiece) {
        this.parentPiece = parentPiece;
    }

    public Piece getParentPiece() {
        return parentPiece;
    }

    public void setPosition(Vector2i position) {
        sprite.setPosition(gridToScreen(position));
        this.position = position;
    }

    public Vector2i getPosition() {
        return position;
    }
    public Vector2f getSpritePosition() {
        return sprite.getPosition();
    }

    public PieceColor getColor() {
        return color;
    }

    private Vector2f gridToScreen(Vector2i vec) {
        return new Vector2f(vec.x * GameScreen.tileSize, vec.y * GameScreen.tileSize);
    }

    public void delete() {
        sprite.delete();
    }
}

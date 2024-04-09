package org.monkeg.games.tetris.pieces;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.monkeg.api.entity.Sprite;
import org.monkeg.games.tetris.GameScreen;

public class Tile {
    private Sprite sprite;

    private Vector2i position;  // Grid coords

    private Piece parentPiece;

    public Tile(Vector2i position, PieceColor color) {
        this.position = new Vector2i(position);

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

    public void setPosition(Vector2i position) {
        sprite.setPosition(gridToScreen(position));
        this.position = position;
    }

    public void moveTo(Vector2i newPos, char[][] map) {
        map[position.x][position.y] = '\0';
        setPosition(newPos);
        map[position.x][position.y] = parentPiece.sign;
    }

    public Vector2i getPosition() {
        return position;
    }

    private Vector2f gridToScreen(Vector2i vec) {
        return new Vector2f(vec.x * GameScreen.tileSize, vec.y * GameScreen.tileSize);
    }

    public void delete() {
        sprite.delete();
    }
}

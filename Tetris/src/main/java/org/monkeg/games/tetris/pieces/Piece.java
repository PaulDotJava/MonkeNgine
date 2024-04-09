package org.monkeg.games.tetris.pieces;

import org.joml.*;
import org.monkeg.api.util.logging.Log;
import org.monkeg.games.tetris.GameScreen;

import java.lang.Math;
import java.util.ArrayList;

public abstract class Piece {
    protected ArrayList<Tile> tiles;
    protected static double fallingDelay;
    protected static double normalFallingDelay;
    protected static double fastFallingDelay;

    protected Vector2i position;

    protected char sign;
    protected int rotation = 0;

    protected boolean falling = true;

    protected double fallingTimer;

    public Piece() {
        normalFallingDelay = 1000;
        fastFallingDelay = 200;

        fallingDelay = normalFallingDelay;
        fallingTimer = 0;
        tiles = new ArrayList<>();
    }

    protected void registerTiles() {
        GameScreen.tiles.addAll(tiles);
        tiles.forEach((Tile tile) -> tile.setParentPiece(this));
    }

    public void rotate(int drotation, char[][] map) {
        this.rotation += drotation;

        if(this.rotation == 4) {
            this.rotation = 0;
        } else if (this.rotation == -1) {
            this.rotation = 3;
        }

        ArrayList<Vector2f> fVecs = new ArrayList<>();

        tiles.forEach(tile -> fVecs.add(worldToModelPos(tile.getPosition())));

        Matrix2f rotation = new Matrix2f();
        rotation.rotate((float) Math.toRadians(90));

        fVecs.forEach((vec -> vec.mul(rotation)));

        ArrayList<Vector2i> newPositions = new ArrayList<>();
        fVecs.forEach(vec -> newPositions.add(modelToWorldPos(vec)));

        newPositions.forEach(vec -> Log.debug(vec.toString()));

        for(Vector2i pos : newPositions) {
            if(!isPosValid(pos, map)) {
                return;
            }
        }

        tiles.forEach(tile -> map[tile.getPosition().x][tile.getPosition().y] = '\0');

        for (int i = 0; i < newPositions.size(); i++) {
            tiles.get(i).setPosition(newPositions.get(i));
        }

        tiles.forEach(tile -> map[tile.getPosition().x][tile.getPosition().y] = sign);
    }

    protected abstract Vector2f worldToModelPos(Vector2i worldPos);
    protected abstract Vector2i modelToWorldPos(Vector2f modelPos);

    private boolean isPosValid(Vector2i pos, char[][] map) {
        if(GameScreen.isOutOfBounds(pos)) {
            return false;
        }

        return map[pos.x][pos.y] == '\0' || map[pos.x][pos.y] == sign;
    }

    public void fall(char[][] map, double dt) {
        if(!canFall(map) && falling) {
            touchdown(map);
        }

        if (falling && canFall(map)) {
            fallingTimer += dt;

            if(fallingTimer > fallingDelay) {
                fallingTimer -= fallingDelay;
                position.y -= 1;

                dropTiles(map);
            }
        }
    }

    private boolean canFall(char[][] map) {
        Vector2i pos;

        for(Tile tile : tiles) {
            pos = tile.getPosition();

            if(GameScreen.isOutOfBounds(new Vector2i(pos.x, pos.y - 1))) {
                return false;
            }

            // Tile below is neither empty nor part of this piece
            if(!(map[pos.x][pos.y-1] == sign || map[pos.x][pos.y-1] == '\0')) {
                return false;
            }
        }

        return true;
    }

    private void dropTiles(char[][] map) {
        tiles.forEach(tile -> map[tile.getPosition().x][tile.getPosition().y] = '\0');
        tiles.forEach(tile -> tile.setPosition(new Vector2i(tile.getPosition().x, tile.getPosition().y - 1)));
        tiles.forEach(tile -> map[tile.getPosition().x][tile.getPosition().y] = sign);
    }

    private void touchdown(char[][] map) {
        //Log.debug("Touchdown");
        sign = 'g';

        // update to final sign on map
        tiles.forEach((tile) -> map[tile.getPosition().x][tile.getPosition().y] = sign);
        falling = false;
    }

    public void delete() {
        tiles.forEach(Tile::delete);
    }

    public static void setFastFall() {
        fallingDelay = fastFallingDelay;
    }

    public static void setNormalFall() {
        fallingDelay = normalFallingDelay;
    }

    public boolean hasLanded() {
        return !falling;
    }

    private boolean tryMoveSide(int side, char[][] map) {
        if(side < 0) side = -1;     // left
        else side = 1;              // right

        ArrayList<Vector2i> newTiles = new ArrayList<>();
        ArrayList<Tile> oldTiles = new ArrayList<>();

        // Find the left most tiles and save the left pos of them
        for (Tile tile : tiles) {
            Vector2i pos = tile.getPosition();

            if(GameScreen.isOutOfBounds(new Vector2i(pos.x + side, pos.y))) {
                return false;
            }

            if(map[pos.x + side][pos.y] == '\0') {
                newTiles.add(new Vector2i(pos.x + side, pos.y));
            }
        }

        // Find the right most tiles to replace
        for (Tile tile : tiles) {
            Vector2i pos = tile.getPosition();

            if(GameScreen.isOutOfBounds(new Vector2i(pos.x - side, pos.y))) {
                oldTiles.add(tile);
                continue;
            }

            if(map[pos.x - side][pos.y] == '\0') {
                oldTiles.add(tile);
            }
        }

        if(oldTiles.size() != newTiles.size()) {
            return false;
        }

        // Move the right most tiles to the saved positions on the left
        for(Tile tile : oldTiles) {
            Vector2i currentPos = tile.getPosition();
            Vector2i newPos = newTiles.remove(0);

            map[currentPos.x][currentPos.y] = '\0';
            map[newPos.x][newPos.y] = sign;

            tile.setPosition(newPos);
        }

        position.x += side;

        return true;
    }

    public boolean tryGoLeft(char[][] map) {
        return tryMoveSide(-1, map);
    }

    public boolean tryGoRight(char[][] map) {

        return tryMoveSide(1, map);
    }


}

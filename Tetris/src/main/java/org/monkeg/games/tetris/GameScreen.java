package org.monkeg.games.tetris;

import org.joml.Vector2i;
import org.monkeg.MonkeNgine;
import org.monkeg.api.input.InputManager;
import org.monkeg.api.input.Key;
import org.monkeg.api.util.color.Color;
import org.monkeg.api.util.logging.Log;
import org.monkeg.games.tetris.pieces.*;
import org.monkeg.rendering.debug.circle.Circle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public class GameScreen {
    private Piece fallingPiece;
    private final char[][] map;
    public static ArrayList<Tile> tiles = new ArrayList<>();
    public static Vector2i mapSize;
    public static int tileSize = 32;

    public GameScreen() {
        mapSize = new Vector2i(15, 30);
        map = new char[mapSize.x][mapSize.y];

        for(int x = 0; x < mapSize.x; x++) {
            for(int y = 0; y < mapSize.y; y++) {
                map[x][y] = '\0';
            }
        }

        //spawnNewPiece();
        fallingPiece = new RedPiece(map);
    }

    public static boolean isOutOfBounds(Vector2i point) {
        return point.x < 0 || point.x >= mapSize.x ||
                point.y < 0 || point.y >= mapSize.y;
    }

    public int update(double dt) {

        InputManager input = MonkeNgine.getInstance().getInputManager();

        if(input.isKeyPressed(Key.MONKE_KEY_DOWN)) {
            Piece.setFastFall();
        } else {
            Piece.setNormalFall();
        }

        if(input.isKeyTapped(Key.MONKE_KEY_LEFT)) {
            //Log.debug("Trying to go left!");
            if(fallingPiece.tryGoLeft(map)) {
               printMap(map);
            }
        }

        if(input.isKeyTapped(Key.MONKE_KEY_RIGHT)) {
            //Log.debug("Trying to go right!");
            if(fallingPiece.tryGoRight(map)) {
                printMap(map);
            }
        }

        if(input.isKeyTapped(Key.MONKE_KEY_A)) {
            fallingPiece.rotate(1, map);
        }

        fallingPiece.fall(map, dt);

        removeFullRows();

        if (fallingPiece.hasLanded()) {
           spawnNewPiece();
        }

        for(int y = 0; y < mapSize.y; y++) {
            for(int x = 0; x < mapSize.x; x++) {
                if (map[x][y] != '\0')  {
                    new Circle(x * tileSize + tileSize / 2, y * tileSize + tileSize / 2, 7, Color.CYAN);
                }
            }
        }

        return 0;
    }

    private void removeFullRows() {
        ArrayList<Integer> ret = new ArrayList<>();

        // Removing full lines
        for(int y = 0; y < mapSize.y; y++) {
            // Detect if line is full
            boolean isLineFull = true;
            for(int x = 0; x < mapSize.x; x++) {
                if(map[x][y] == '\0' || map[x][y] == 'c') {
                    isLineFull = false;
                    break;
                }
            }

            // Remove line
            if (isLineFull) {
                for(int x = 0; x < mapSize.x; x++) {
                    map[x][y] = '\0';
                }

                ArrayList<Integer> lineIndices = getTileIndicesOfLine(y);
                for (int i = 0; i < lineIndices.size(); i++) {
                    //Log.debug("{}", tiles.get(i).getColor().toString());
                    tiles.remove(lineIndices.get(i) - i).delete();
                }

                makeLinesAboveFall(y);
                // Have to check this line again
                y--;
            }


        }
    }

    private void makeLinesAboveFall(int y) {
        ArrayList<Tile> affectedTiles = new ArrayList<>();
        tiles.forEach(tile -> {
            if (tile.getPosition().y > y) {
                map[tile.getPosition().x][tile.getPosition().y] = '\0';
                affectedTiles.add(tile);
            }
        });

        Log.info("Affected tiles: {}", affectedTiles.size());
        affectedTiles.forEach(tile -> tile.setPosition(new Vector2i(tile.getPosition().x, tile.getPosition().y - 1)));
        //affectedTiles.forEach(tile -> Log.debug("{}, {}", tile.getPosition().x, tile.getPosition().y));
        affectedTiles.forEach(tile -> map[tile.getPosition().x][tile.getPosition().y] = tile.getParentPiece().getSign());
    }

    private ArrayList<Integer> getTileIndicesOfLine(int y) {
        ArrayList<Integer> ret = new ArrayList<>();

        for(int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).getPosition().y == y) {
                ret.add(i);
            }
        }

        return ret;
    }

    private void spawnNewPiece() {
        Random random = new Random();
        int selection = random.nextInt(5);

        fallingPiece = switch (selection) {
            case 0 -> new RedPiece(map);
            case 1 -> new GreenPiece(map);
            case 2 -> new YellowPiece(map);
            case 3 -> new PurplePiece(map);
            case 4 -> new BluePiece(map);

            default -> new GreenPiece(map);
        };

        //fallingPiece = new BluePiece(map);
    }

    public static void printMap(char[][] map) {

        /*
        Log.debug("");
        for(int y = 0; y < mapSize.y; y++) {
            String line = "";
            for(int x = 0; x < mapSize.x; x++) {
                line += map[x][y];
            }
            Log.debug("{}", line);
        }
        Log.debug("");
        */
    }
}

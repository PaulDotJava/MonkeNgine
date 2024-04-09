package org.monkeg.games.tetris.pieces;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.monkeg.games.tetris.GameScreen;

public class RedPiece extends Piece{
    public RedPiece(char[][] map) {
        falling = true;

        sign = 'c';

        position = new Vector2i(GameScreen.mapSize.x / 2, GameScreen.mapSize.y - 6);

        tiles.add(new Tile(new Vector2i(position.x, position.y), PieceColor.RED));
        tiles.add(new Tile(new Vector2i(position.x, position.y + 1), PieceColor.RED));
        tiles.add(new Tile(new Vector2i(position.x + 1, position.y), PieceColor.RED));
        tiles.add(new Tile(new Vector2i(position.x + 1, position.y + 1), PieceColor.RED));

        map[position.x][position.y] = sign;
        map[position.x][position.y + 1] = sign;
        map[position.x + 1][position.y] = sign;
        map[position.x + 1][position.y + 1] = sign;

        registerTiles();
    }

    @Override
    protected Vector2f worldToModelPos(Vector2i worldPos) {
        return new Vector2f(worldPos).sub(new Vector2f(position)).sub(new Vector2f(0.5f, 0.5f));
    }

    @Override
    protected Vector2i modelToWorldPos(Vector2f modelPos) {
        return new Vector2i((int) (modelPos.x + position.x + 0.5f), (int) (modelPos.y + position.y + 0.5f));
    }
}

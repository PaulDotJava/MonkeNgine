package org.monkeg.games.tetris.pieces;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.monkeg.games.tetris.GameScreen;

public class BluePiece extends Piece{
    public BluePiece(char[][] map) {
        falling = true;

        sign = 'c';

        position = new Vector2i(GameScreen.mapSize.x / 2, GameScreen.mapSize.y - 6);

        tiles.add(new Tile(new Vector2i(position), PieceColor.BLUE));
        tiles.add(new Tile(new Vector2i(position.x + 1, position.y), PieceColor.BLUE));
        tiles.add(new Tile(new Vector2i(position.x + 2, position.y), PieceColor.BLUE));
        tiles.add(new Tile(new Vector2i(position.x + 3, position.y), PieceColor.BLUE));

        map[position.x][position.y] = sign;
        map[position.x + 1][position.y] = sign;
        map[position.x + 2][position.y] = sign;
        map[position.x + 3][position.y] = sign;

        registerTiles();
    }

    @Override
    protected Vector2f worldToModelPos(Vector2i worldPos) {
        return new Vector2f(worldPos).sub(new Vector2f(position)).sub(1.5f, 0);
    }

    @Override
    protected Vector2i modelToWorldPos(Vector2f modelPos) {
        return new Vector2i((int) (modelPos.x + position.x + 1.5f), (int) (modelPos.y + position.y + 0));
    }
}

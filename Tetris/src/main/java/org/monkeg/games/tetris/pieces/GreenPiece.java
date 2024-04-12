package org.monkeg.games.tetris.pieces;

import org.joml.Vector2f;
import org.joml.Vector2i;
import org.monkeg.games.tetris.GameScreen;

public class GreenPiece extends Piece {
    public GreenPiece(char[][] map) {
        falling = true;

        //int tileSize = GameScreen.tileSize;

        sign = 'c';

        position = new Vector2i(GameScreen.mapSize.x / 2, GameScreen.mapSize.y - 6);

        tiles.add(new Tile(new Vector2i(position), PieceColor.GREEN));
        tiles.add(new Tile(new Vector2i(position.x + 1, position.y), PieceColor.GREEN));
        tiles.add(new Tile(new Vector2i(position.x + 1, position.y + 1), PieceColor.GREEN));
        tiles.add(new Tile(new Vector2i(position.x + 2, position.y + 1), PieceColor.GREEN));
    }

    @Override
    protected Vector2f worldToModelPos(Vector2i worldPos) {
        return new Vector2f(worldPos).sub(new Vector2f(position)).sub(new Vector2f(1, 1));
    }

    @Override
    protected Vector2i modelToWorldPos(Vector2f modelPos) {
        return new Vector2i((int) (modelPos.x + position.x + 1), (int) (modelPos.y + position.y + 1));
    }


}

package pieces;

import gameboard.GameBoard;


import java.util.ArrayList;
import java.util.List;

import static pieces.Piece.PieceColor.WHITE;
import static pieces.Piece.PieceType.KNIGHT;

/**
 * Represents a Knight piece of the chess game
 * Created by SiyangLiu on 2018/2/2.
 */
public class Knight extends Piece {
    // the coordinate offset of all possible moves of a knight
    private final int[][] moveCandidates = new int[][]{{-2,-1}, {-2,1}, {-1,-2}, {-1,2}, {1,-2}, {1,2}, {2, -1}, {2,1}};

    public Knight(PieceColor pieceColor, int coordinateRow, int coordinateCol) {
        super(pieceColor, coordinateRow, coordinateCol);
        this.pieceType = KNIGHT;
    }

    @Override
    public List<Move> computeValidMoves(GameBoard board) {
        this.validMoves = new ArrayList<>();
        computeValidMovesHelper(board, this.moveCandidates);
        return this.validMoves;
    }

    @Override
    public String getString() {
        return getPieceColor() == WHITE ? "white_knight" : "black_knight";
    }

}

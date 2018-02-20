package pieces;

import gameboard.GameBoard;

import java.util.ArrayList;
import java.util.List;

import static pieces.Piece.PieceColor.WHITE;
import static pieces.Piece.PieceType.KING;

/**
 * Represents a King piece of the chess game
 * Created by SiyangLiu on 2018/2/2.
 */
public class King extends Piece {
    // the coordinate offset of all possible moves of a king
    private final int[][] moveCandidates = new int[][]{{-1, -1}, {-1,0}, {-1, 1}, {0,-1}, {0,1}, {1,-1}, {1,0}, {1, 1}};

    public King(PieceColor pieceColor, int coordinateRow, int coordinateCol) {
        super(pieceColor, coordinateRow, coordinateCol);
        this.pieceType = KING;
    }


    @Override
    public List<Move> computeValidMoves(GameBoard board) {
        this.validMoves = new ArrayList<>();
        computeValidMovesHelper(board, this.moveCandidates);
        return this.validMoves;
    }

    @Override
    public String getString() {
        return getPieceColor() == WHITE ? "white_king" : "black_king";
    }

}

package pieces;

import gameboard.GameBoard;

import java.util.ArrayList;
import java.util.List;

import static pieces.Piece.PieceColor.WHITE;
import static pieces.Piece.PieceType.QUEEN;

/**
 * Represents a Queen piece of the chess game
 * Created by SiyangLiu on 2018/2/2.
 */
public class Queen extends Piece {
    // the coordinate offset of all possible moves of a queen
    private final int[][] moveCandidates = new int[][]{{0,-1}, {0,1}, {-1,0}, {1,0}, {-1,-1}, {1,1}, {-1,1}, {1,-1}};

    public Queen(PieceColor pieceColor, int coordinateRow, int coordinateCol) {
        super(pieceColor, coordinateRow, coordinateCol);
        this.pieceType = QUEEN;
    }

    @Override
    public List<Move> computeValidMoves(GameBoard board) {
        this.validMoves = new ArrayList<>();
        computeValidMovesHelperWhile(board, this.moveCandidates);
        return this.validMoves;
    }

    @Override
    public String getString() {
        return getPieceColor() == WHITE ? "white_queen" : "black_queen";
    }

}

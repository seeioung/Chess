package pieces;

import gameboard.GameBoard;

import java.util.ArrayList;
import java.util.List;

import static pieces.Piece.PieceColor.WHITE;
import static pieces.Piece.PieceType.BISHOP;

/**
 * Represents a Bishop piece of the chess game
 * Created by SiyangLiu on 2018/2/2.
 */
public class Bishop extends Piece {
    // the coordinate offset of all possible moves of a bishop
    private final int[][] moveCandidates = new int[][]{{-1,-1}, {-1,1}, {1,-1}, {1,1}};

    public Bishop(PieceColor pieceColor, int coordinateRow, int coordinateCol) {
        super(pieceColor, coordinateRow, coordinateCol);
        this.pieceType = BISHOP;
    }

    @Override
    public List<Move> computeValidMoves(GameBoard board) {
        this.validMoves = new ArrayList<>();
        computeValidMovesHelperWhile(board, this.moveCandidates);
        return this.validMoves;
    }

    @Override
    public String getString() {
        return getPieceColor() == WHITE ? "white_bishop" : "black_bishop";
    }

}


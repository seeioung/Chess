package pieces;

import gameboard.GameBoard;

import java.util.ArrayList;
import java.util.List;

import static pieces.Piece.PieceColor.WHITE;
import static pieces.Piece.PieceType.EMPRESS;

/**
 * An empress is a fairy chess piece that can move like a rook or a knight.
 */
public class Empress extends Piece{
    // the coordinate offset of all possible moves of a knight
    private final int[][] moveCandidatesKnight = new int[][]{{-2,-1}, {-2,1}, {-1,-2}, {-1,2}, {1,-2}, {1,2}, {2, -1}, {2,1}};

    // the coordinate offset of all possible moves of a rook
    private final int[][] moveCandidatesRook = new int[][]{{0,-1}, {0,1}, {-1,0}, {1,0}};



    public Empress(PieceColor pieceColor, int rowIndex, int colIndex) {
        super(pieceColor, rowIndex, colIndex);
        this.pieceType = EMPRESS;
    }

    @Override
    public List<Move> computeValidMoves(GameBoard board) {
        this.validMoves = new ArrayList<>();
        computeValidMovesHelper(board, this.moveCandidatesKnight);
        computeValidMovesHelperWhile(board, this.moveCandidatesRook);

        return this.validMoves;
    }

    @Override
    public String getString() {
        return getPieceColor() == WHITE ? "white_empress" : "black_empress";
    }
}

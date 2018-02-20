package pieces;

import gameboard.GameBoard;
import gameboard.Square;

import java.util.List;


/**
 * Represents a piece in the chess game
 * Created by SiyangLiu on 2018/2/2.
 */
public abstract class Piece {
    private PieceColor pieceColor;
    PieceType pieceType;
    private int rowIndex;
    private int colIndex;
    List<Move> validMoves = null;

    protected Piece(PieceColor pieceColor, int rowIndex, int colIndex) {
        this.pieceColor = pieceColor;
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
    }

    /**
     * Checks if the move of the piece is valid
     */
    public boolean isValidMove(Move move) {
        return this.validMoves.contains(move);
    }

    /**
     * Computes all valid moves of the piece given the current board
     */
    public abstract List<Move> computeValidMoves(final GameBoard board);

    /**
     * Helper function for Computing all valid moves of the piece given the current board
     * Works for piece types: king, knight
     */
    void computeValidMovesHelper(GameBoard board, int[][] moveCandidates) {
        for (int[] moveCandidate : moveCandidates) {                /* try all possible moves */
            int destRow = this.rowIndex + moveCandidate[0];
            int destCol = this.colIndex + moveCandidate[1];

            if (!board.isValidCoordinates(destRow, destCol)) {      /* out of bound */
                continue;
            }

            Square destSquare = board.getSquare(destRow, destCol);
            if (!destSquare.hasPiece()) {                           /* valid to move the piece */
                Move move = new Move(this, destRow, destCol, null);
                this.validMoves.add(move);
            } else if (destSquare.getPiece().getPieceColor() != this.pieceColor) {  /* valid to attack the piece in the destination */
                Move move = new Move(this, destRow, destCol, destSquare.getPiece());
                this.validMoves.add(move);
            }
        }

    }

    /**
     * Helper function for Computing all valid moves of the piece given the current board
     * Works for piece types: bishop, queen, rook
     */
    void computeValidMovesHelperWhile(GameBoard board, int[][] moveCandidates) {
        for (int[] moveCandidate : moveCandidates) {        /* try all possible moves */
            int destRow = this.rowIndex;
            int destCol = this.colIndex;
            while (true) { /* try all possible number of steps */

                destRow = destRow + moveCandidate[0];
                destCol = destCol + moveCandidate[1];

                if (!board.isValidCoordinates(destRow, destCol)) {      /* out of bound */
                    break;
                }

                Square destSquare = board.getSquare(destRow, destCol);
                if (!destSquare.hasPiece()) {               /* valid to move the piece */
                    Move move = new Move(this, destRow, destCol, null);
                    this.validMoves.add(move);
                } else if (destSquare.getPiece().getPieceColor() != this.pieceColor) {      /* valid to attack the piece in the destination */
                    Move move = new Move(this, destRow, destCol, destSquare.getPiece());
                    this.validMoves.add(move);
                    break;
                } else { /* cannot leap over self's pieces */
                    break;
                }

            }
        }
    }

    /** Updates the coordinates of the piece in the game board
     *  Has to follow up with the call to computeValidMoves(board) to update the piece
     *  @param destRow guaranteed to be valid row
     *  @param destCol guaranteed to be valid col
     */
    public void updatePieceCoordinates(int destRow, int destCol) {
        this.rowIndex = destRow;
        this.colIndex = destCol;
        this.validMoves = null; // reset valid moves
    }

    /**
     * Gets the color of the piece
     */
    public PieceColor getPieceColor() {
        return this.pieceColor;
    }

    /**
     * Gets the type of the piece
     */
    public PieceType getPieceType() {
        return this.pieceType;
    }

    /**
     * Gets the string of the piece
     * all lower case with _ between color and type
     */
    public abstract String getString();

    /**
     * Gets the row index of the piece in the board
     */
    public int getRowIndex() {
        return rowIndex;
    }

    /**
     * Gets the column index of the piece in the board
     */
    public int getColIndex() {
        return colIndex;
    }

    /**
     * Gets the valid moves of the piece in the board
     */
    public List<Move> getValidMoves() {
        return this.validMoves;
    }


    /**
     * Enumerates all possible colors of a piece
     */
    public enum PieceColor {
        WHITE,
        BLACK
    }

    /**
     * Enumerates all possible types of a piece
     */
    public enum PieceType {
        ROOK,
        KNIGHT,
        BISHOP,
        QUEEN,
        KING,
        PAWN,
        HOPPER,
        EMPRESS
    }

}

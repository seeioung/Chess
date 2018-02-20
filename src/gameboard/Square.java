package gameboard;

import pieces.Piece;

/**
 * Represents a single square in the game board
 * Created by SiyangLiu on 2018/2/1.
 */
public class Square {
    private int rowIndex;
    private int colIndex;
    private Piece piece;

    public Square(int rowIndex, int colIndex, Piece piece) {
        this.rowIndex = rowIndex;
        this.colIndex = colIndex;
        this.piece = piece;
    }

    /**
     * Checks if the square has a piece
     */
    public boolean hasPiece(){
        return this.piece != null;
    }

    /**
     * Gets the piece placed in the square
     * @return null if no piece
     */
    public Piece getPiece() {
        return this.piece;
    }

    /**
     * Gets the row index of the square
     */
    public int getRowIndex() {
        return this.rowIndex;
    }

    /**
     * Gets the column index of the square
     */
    public int getColIndex() {
        return this.colIndex;
    }

    /**
     * Removes the piece in the square
     */
    public void setEmpty() {
        this.piece = null;
    }

    /**
     * Places the given piece in the square
     */
    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}

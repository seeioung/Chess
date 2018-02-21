package gameboard;

import pieces.*;

import java.util.*;

import static pieces.Piece.PieceColor.BLACK;
import static pieces.Piece.PieceColor.WHITE;


/**
 * Represents a game board of the chess game
 * Created by SiyangLiu on 2018/2/2.
 */
public class GameBoard {
    private int numOfRows;
    private int numOfCols;

    private Square[][] squares;

    private Set<Piece> blackPieces;
    private Set<Piece> whitePieces;


    public GameBoard(int numOfRows, int numOfCols) {
        this.numOfRows = numOfRows;
        this.numOfCols = numOfCols;

        this.squares = buildEmptyBoard();
        this.whitePieces = new HashSet<>();
        this.blackPieces = new HashSet<>();
    }

//    /**
//     * copy constructor - make a deep copy of the game
//     * @param board
//     */
//    public GameBoard(GameBoard board) {
//        this.numOfRows = board.getNumOfRows();
//        this.numOfCols = board.getNumOfCols();
//
//        this.squares = board.squares;
//        this.whitePieces = new HashSet<>();
//        this.blackPieces = new HashSet<>();
//    }

    /**
     * Builds a game board with no pieces on it
     */
    private Square[][] buildEmptyBoard() {
        Square[][] emptyBoard = new Square[this.numOfRows][this.numOfCols];
        for (int row = 0; row < numOfRows; row++) {
            for (int col = 0; col < numOfCols; col++) {
                emptyBoard[row][col] = new Square(row, col, null);
            }
        }
        return emptyBoard;
    }

    /**
     * Places the initial pieces of a standard chess game onto the board
     */
    public void placeStandardPieces() {
        for (int row : new int[]{0, 1, 6, 7}) {             /*place each piece*/
            for (int col = 0; col < numOfCols; col++) {
                placeInitialPiece(row, col, true);
            }
        }
        updateValidMoves();
    }

    /**
     * Places the initial pieces of a standard chess game onto the board
     */
    public void placeCustomPieces() {
        for (int row : new int[]{0, 1, 6, 7}) {             /*place each piece*/
            for (int col = 0; col < numOfCols; col++) {
                placeInitialPiece(row, col, false);
            }
        }
        updateValidMoves();
    }

    /**
     * Places one initial piece of a standard chess game onto the board in specified location
     */
    private void placeInitialPiece(int row, int col, boolean standard) {
        // decide the color of the piece to be placed
        Piece.PieceColor color;
        Set<Piece> pieceSet;
        if (row == 6 || row == 7) {
            color = WHITE;
            pieceSet = this.whitePieces;
        } else {
            color = BLACK;
            pieceSet = this.blackPieces;
        }

        // decide the type of the piece to be placed
        Piece newPiece = null;
        if (row == 1 || row == 6) { // pawn
            if (standard) {
                newPiece = new Pawn(color, row, col);
            } else if (col == 0 || col == 7){
                newPiece = new Hopper(color, row, col);
            } else {
                newPiece = new Pawn(color, row, col);
            }
        } else if (col == 0 || col == 7) { // rook
            newPiece = new Rook(color, row, col);
        } else if (col == 1 || col == 6) { // knight
            if (standard) {
                newPiece = new Knight(color, row, col);
            } else {
                newPiece = new Empress(color, row, col);
            }
        } else if (col == 2 || col == 5) { // bishop
            newPiece = new Bishop(color, row, col);
        } else if (col == 3) { // queen
            newPiece = new Queen(color, row, col);
        } else if (col == 4) { // king
            newPiece = new King(color, row, col);
        }

        // place the piece
        pieceSet.add(newPiece);
        this.squares[row][col].setPiece(newPiece);
    }

    /**
     * Places the given piece onto the board in specified location
     * @param piece the piece to place onto the board
     *              cannot be null
     */
    public boolean placePiece(Piece piece) {
        Square square = this.squares[piece.getRowIndex()][piece.getColIndex()];
        if (square.getPiece() != null) {    /*the location is not available*/
            return false;
        }

        // place the piece
        square.setPiece(piece);
        if (piece.getPieceColor() == BLACK && !blackPieces.contains(piece)) {
            this.blackPieces.add(piece);
        } else if (piece.getPieceColor() == WHITE && !whitePieces.contains(piece)){
            this.whitePieces.add(piece);
        }
        updateValidMoves();
        return true;
    }

    private void updateValidMoves() {
        for (Piece piece : blackPieces) {
            piece.computeValidMoves(this);
        }
        for (Piece piece : whitePieces) {
            piece.computeValidMoves(this);
        }
    }

    /**
     * Executes the given move in the board
     * @param move is Guaranteed to be a valid move
     * @return the captured piece after the execution of the given move
     */
    public void executeMove(Move move) {
        int destRow = move.getDestRow();
        int destCol = move.getDestCol();
        Piece piece = move.getPiece();
        piece.updatePieceCoordinates(destRow, destCol);

        // update the start Square
        Square startSquare = this.squares[move.getStartRow()][move.getStartCol()];
        startSquare.setEmpty();

        // update the destination Square
        Piece capturedPiece = move.getToCapture();
        if (capturedPiece != null) {
            if (capturedPiece.getPieceColor() == BLACK) {
                this.blackPieces.remove(capturedPiece);
            } else if (capturedPiece.getPieceColor() == WHITE) {
                this.whitePieces.remove(capturedPiece);
            }
        }
        Square destSquare = this.squares[destRow][destCol];
        destSquare.setEmpty();
        placePiece(piece);

        updateValidMoves();

    }

    /**
     * Undoes the given move in the board
     * @param move the move to undo and was guaranteed to be a valid move
     *
     */
    public void undoMove(Move move) {
        int startRow = move.getStartRow();
        int startCol = move.getStartCol();
        int destRow = move.getDestRow();
        int destCol = move.getDestCol();
        Piece piece = move.getPiece();

        // update the start Square
        piece.updatePieceCoordinates(startRow, startCol);
        placePiece(piece);

        // update the destination Square
        Square destSquare = this.squares[destRow][destCol];
        destSquare.setEmpty();
        Piece capturedPiece =  move.getToCapture();
        if (capturedPiece != null) {
            placePiece(capturedPiece);
            if (capturedPiece.getPieceColor() == BLACK) {
                this.blackPieces.add(capturedPiece);
            } else if (capturedPiece.getPieceColor() == WHITE) {
                this.whitePieces.add(capturedPiece);
            }
        }
        updateValidMoves();
    }

    /**
     * checks if the given coordinates are valid in the board
     */
    public boolean isValidCoordinates(int rowIndex, int colIndex) {
        return rowIndex >= 0 && rowIndex < this.numOfRows && colIndex >= 0 && colIndex < this.numOfCols;
    }

    /**
     * gets the number of rows of the game board
     */
    public int getNumOfRows() {
        return this.numOfRows;
    }

    /**
     * gets the number of cols of the game board
     */
    public int getNumOfCols() {
        return this.numOfCols;
    }

    /**
     * gets the black pieces of the game board
     */
    public Set<Piece> getBlackPieces() {
        return this.blackPieces;
    }

    /**
     * gets the white pieces of the game board
     */
    public Set<Piece> getWhitePieces() {
        return this.whitePieces;
    }

    /**
     * gets the square array of the game board
     */
    public Square[][] getSquares() {
        return this.squares;
    }

    /**
     * gets the the square at the given location of the game board
     */
    public Square getSquare(int row, int col) {
        return this.squares[row][col];
    }
}

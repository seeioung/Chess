package test;

import static org.junit.Assert.*;

import gameboard.GameBoard;
import gameboard.Square;
import org.junit.Before;
import org.junit.Test;
import pieces.Move;
import pieces.Piece;

import java.util.Set;


/**
 * Created by SiyangLiu on 2018/2/3.
 */
public class GameBoardTest {
    private GameBoard gameBoard;
    private int numOfRows;
    private int numOfCols;

    @Before
    public void createEmptyBoardForTest() {
        gameBoard = new GameBoard(8, 8);
        numOfRows = 8;
        numOfCols = 8;
    }

    /**
     * Tests if an empty GameBoard can be correctly constructed
     */
    @Test
    public void ValidConstructor() throws Exception {
        // test size of the board
        assertEquals(numOfRows, gameBoard.getNumOfRows());
        assertEquals(numOfCols, gameBoard.getNumOfCols());
        assertEquals(numOfRows, gameBoard.getSquares().length); // data structure
        assertEquals(numOfCols, gameBoard.getSquares()[0].length); // data structure


        // test the board array
        Square[][] squares = gameBoard.getSquares();
        // test coordinates of each square
        for (int i = 0; i < numOfRows; i++) {
            for (int j = 0; j < numOfCols; j++) {
                assertEquals(squares[i][j].getRowIndex(), i);
                assertEquals(squares[i][j].getColIndex(), j);
            }
        }

        for (int i = 0; i < numOfRows; i++) { // test empty squares
            for (int j = 0; j < numOfCols; j++) {
                assertEquals(squares[i][j].getPiece(), null);
            }
        }
    }

    /**
     * Tests if all initial pieces of a standard chess game can be placed onto the board correctly
     */
    @Test
    public void placeStandardPieces() throws Exception {
        gameBoard.placeStandardPieces();

        // test the board array
        Square[][] squares = gameBoard.getSquares();

        for (int i = 2; i < numOfRows - 2; i++) { // test empty squares
            for (int j = 0; j < numOfCols; j++) {
                assertEquals(squares[i][j].getPiece(), null);
            }
        }

        // test color of squares and blackPieces and whitePieces sets
        Set<Piece> blackPieces = gameBoard.getBlackPieces();
        Set<Piece> whitePieces = gameBoard.getWhitePieces();
        for (int j = 0; j < numOfCols; j++) {
            assertEquals(squares[0][j].getPiece().getPieceColor(), Piece.PieceColor.BLACK);
            assertEquals(squares[1][j].getPiece().getPieceColor(), Piece.PieceColor.BLACK);
            assertEquals(blackPieces.contains(squares[0][j].getPiece()), true);
            assertEquals(blackPieces.contains(squares[1][j].getPiece()), true);


            assertEquals(squares[6][j].getPiece().getPieceColor(), Piece.PieceColor.WHITE);
            assertEquals(squares[7][j].getPiece().getPieceColor(), Piece.PieceColor.WHITE);
            assertEquals(whitePieces.contains(squares[6][j].getPiece()), true);
            assertEquals(whitePieces.contains(squares[7][j].getPiece()), true);
        }

        // test type of each piece
        assertEquals(squares[0][0].getPiece().getPieceType(), Piece.PieceType.ROOK);
        assertEquals(squares[0][7].getPiece().getPieceType(), Piece.PieceType.ROOK);
        assertEquals(squares[7][0].getPiece().getPieceType(), Piece.PieceType.ROOK);
        assertEquals(squares[7][7].getPiece().getPieceType(), Piece.PieceType.ROOK);

        assertEquals(squares[0][1].getPiece().getPieceType(), Piece.PieceType.KNIGHT);
        assertEquals(squares[0][6].getPiece().getPieceType(), Piece.PieceType.KNIGHT);
        assertEquals(squares[7][1].getPiece().getPieceType(), Piece.PieceType.KNIGHT);
        assertEquals(squares[7][6].getPiece().getPieceType(), Piece.PieceType.KNIGHT);

        assertEquals(squares[0][2].getPiece().getPieceType(), Piece.PieceType.BISHOP);
        assertEquals(squares[0][5].getPiece().getPieceType(), Piece.PieceType.BISHOP);
        assertEquals(squares[7][2].getPiece().getPieceType(), Piece.PieceType.BISHOP);
        assertEquals(squares[7][5].getPiece().getPieceType(), Piece.PieceType.BISHOP);

        assertEquals(squares[0][3].getPiece().getPieceType(), Piece.PieceType.QUEEN);
        assertEquals(squares[0][4].getPiece().getPieceType(), Piece.PieceType.KING);
        assertEquals(squares[7][3].getPiece().getPieceType(), Piece.PieceType.QUEEN);
        assertEquals(squares[7][4].getPiece().getPieceType(), Piece.PieceType.KING);
    }

    /**
     * precondition: the updatePieceCoordinates is valid
     **/
    @Test
    public void movePiece1() throws Exception {
        gameBoard.placeStandardPieces();
        int startRow = 1;
        int startCol = 0;
        int destRow = 3;
        int destCol = 0;
        Piece blackPawn = gameBoard.getSquare(startRow,startCol).getPiece();
        gameBoard.executeMove(new Move(blackPawn, destRow, destCol, gameBoard.getSquare(destRow,destCol).getPiece()));
        movePieceHelper(blackPawn, startRow, startCol, destRow, destCol);
    }

    /**
     * precondition: the updatePieceCoordinates is valid
     **/
    @Test
    public void movePiece2() throws Exception {
        gameBoard.placeStandardPieces();
        int startRow = 7;
        int startCol = 1;
        int destRow = 5;
        int destCol = 0;
        Piece whiteKnight = gameBoard.getSquare(startRow,startCol).getPiece();
        gameBoard.executeMove(new Move(whiteKnight, destRow, destCol, gameBoard.getSquare(destRow,destCol).getPiece()));
        movePieceHelper(whiteKnight, startRow, startCol, destRow, destCol);
    }

    /**
     * helper function of the test movePiece()
     **/
    private void movePieceHelper(Piece piece, int startRow, int startCol, int destRow, int destCol) throws Exception {
        assertEquals(gameBoard.getSquare(startRow,startCol).getPiece(), null);
        assertEquals(gameBoard.getSquare(destRow,destCol).getPiece(), piece);

        assertEquals(piece.getRowIndex(), destRow);
        assertEquals(piece.getColIndex(), destCol);
    }


}
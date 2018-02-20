package test;

import gameboard.GameBoard;
import org.junit.Before;
import org.junit.Test;
import pieces.Move;
import pieces.Piece;

import java.util.List;

import static org.junit.Assert.*;
import static pieces.Piece.PieceColor.BLACK;
import static pieces.Piece.PieceColor.WHITE;


/**
 * Test for the operations of a Piece
 * Created by SiyangLiu on 2018/2/3.
 */
public abstract class PieceTest {
    protected Piece blackPiece;
    protected Piece whitePiece;
    protected GameBoard board;

    @Before
    public void buildPieceForTesting() {
        board = new GameBoard(8,8);
        blackPiece = getConcrete(BLACK, 4, 5);
        whitePiece = getConcrete(WHITE, 2, 3);
    }

    /**
     * test the instance fields except pieceType after construction
     */
    @Test
    public void ValidConstructor1() throws Exception {
        assertEquals(blackPiece.getPieceColor(), BLACK);
        assertEquals(blackPiece.getRowIndex(), 4);
        assertEquals(blackPiece.getColIndex(), 5);

        assertEquals(whitePiece.getPieceColor(), WHITE);
        assertEquals(whitePiece.getRowIndex(), 2);
        assertEquals(whitePiece.getColIndex(), 3);
    }

    /**
     * test the pieceType field after construction
     */
    @Test
    public abstract void ValidConstructor2() throws Exception;

    /**
     * Test if the resulting board is correct after the execute of a valid move
     */
    @Test
    public void move() throws Exception {
        blackPiece.updatePieceCoordinates(6,7);
        assertEquals(blackPiece.getPieceColor(), BLACK);
        assertEquals(blackPiece.getRowIndex(), 6);
        assertEquals(blackPiece.getColIndex(), 7);

        whitePiece.updatePieceCoordinates(3,4);
        assertEquals(whitePiece.getPieceColor(), WHITE);
        assertEquals(whitePiece.getRowIndex(), 3);
        assertEquals(whitePiece.getColIndex(), 4);
    }

    /**
     * Test if the all valid moves are computed correctly
     */
    @Test
    public abstract void computeValidMoves() throws Exception;

    /**
     * Helper function for computeValidMoves test
     * Checks if two list of moves match
     */
    void testComputeValidMovesHelper(List<Move> validMoves, int[][] correctDest, Piece piece) {
        assertEquals(validMoves.size(), correctDest.length);
        for (int i = 0; i < validMoves.size(); i++) {
            Move move = validMoves.get(i);
            assertEquals(move.getPiece(), piece);
            assertEquals(move.getStartRow(), piece.getRowIndex());
            assertEquals(move.getStartCol(), piece.getColIndex());
            assertEquals(move.getDestRow(), correctDest[i][0]);
            assertEquals(move.getDestCol(), correctDest[i][1]);
        }
    }

    /**
     * Constructs a concrete piece with one of the piece type in chess
     */
    protected abstract Piece getConcrete(Piece.PieceColor pieceColor, int coordinateRow, int coordinateCol);


}
package test;

import gameboard.GameBoard;
import pieces.*;

import java.util.List;

import static org.junit.Assert.*;
import static pieces.Piece.PieceColor.BLACK;
import static pieces.Piece.PieceColor.WHITE;
import static pieces.Piece.PieceType.HOPPER;

/**
 * Tests the operations of a Hopper
 */
public class HopperTest extends PieceTest{

    @Override
    public void ValidConstructor2() throws Exception {
        assertEquals(this.blackPiece.getPieceType(), HOPPER);
        assertEquals(this.whitePiece.getPieceType(), HOPPER);

    }

    @Override
    public void computeValidMoves() throws Exception {
        GameBoard board = new GameBoard(8,8);

        int pieceRow = 2;
        int pieceCol = 3;
        Piece hopper = new Hopper(BLACK, pieceRow, pieceCol);
        board.placePiece(hopper);

        int friendPieceRow = 4;
        int friendPieceCol = 3;
        Piece blackPawn = new Pawn(BLACK, friendPieceRow,friendPieceCol);
        board.placePiece(blackPawn);

        int enemyPieceRow = 6;
        int enemyPieceCol = 3;
        Piece whitePawn = new Pawn(WHITE, enemyPieceRow,enemyPieceCol);
        board.placePiece(whitePawn);

        hopper.computeValidMoves(board);

        // check if valid moves are correct
        int[][] correctDest = new int[][]{
                /* {2,2}, {2,1}, {2,0}, {2,4}, {2,5}, {2,6}, {2,7}, no jump*/
                /* {1,3}, {0,3}, {3,3}, no jump*/
                /* {4,3}, hurdle*/
                {5,3},
                {6,3}/*attack*/
                /*, {7,3} blocked by enemy*/};
        List<Move> validMoves = hopper.getValidMoves();

        testComputeValidMovesHelper(validMoves, correctDest, hopper);

    }

    @Override
    protected Piece getConcrete(Piece.PieceColor pieceColor, int coordinateRow, int coordinateCol) {
        return new Hopper(pieceColor, coordinateRow, coordinateCol);
    }
}